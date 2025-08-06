package tomykulak.furniturestore.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.repository.ProductRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class DataImporter implements CommandLineRunner {
    private Integer batchCount = 0;
    private final ProductRepository productRepository;
    private final ResourceLoader resourceLoader;

    public DataImporter(ProductRepository productRepository, ResourceLoader resourceLoader) {
        this.productRepository = productRepository;
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void run(String... args) {
        importProduct();
    }

    public void importProduct() {
        Resource resource = resourceLoader.getResource("classpath:static/ikea_data.csv");
        final int BATCH_SIZE = 500;
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            processCsvInBatches(reader, BATCH_SIZE);
        } catch (IOException | CsvValidationException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            System.out.println("Finished.");
        }
    }

    private void processCsvInBatches(CSVReader reader, int batchSize) throws IOException, CsvValidationException {
        List<Product> batch = new ArrayList<>(batchSize);
        String[] line;
        boolean isFirstLine = true;
        while ((line = reader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            if (line[10].length() > 250) {
                line[10] = line[10].substring(0, 247) + "...";
            }
            // Remove pattern like 003.494.44 at the start of the string
            line[10] = line[10].replaceFirst("^\\d{3}\\.\\d{3}\\.\\d{2}\\s*", "");

            Product product = parseProductLine(line);
            if (product != null) {
                batch.add(product);
                if (batch.size() == batchSize) {
                    saveBatch(batch);
                    batch.clear();
                }
            }
        }
        if (!batch.isEmpty()) {
            saveBatch(batch);
        }
    }

    private Product parseProductLine(String[] line) {
        try {
            if (line.length < 14) {
                System.out.println("Skipping short line: " + Arrays.toString(line));
                return null;
            }
            line = convertEmptyToNull(line);
            return Product.builder()
                    .id(parseInteger(line[1]))
                    .name(line[2])
                    .category(line[3])
                    .price(parseBigDecimal(line[4]))
                    .oldPrice(parseBigDecimal(line[5]))
                    .sellableOnline(parseBoolean(line[6]))
                    .link(line[7])
                    .otherColors(parseBoolean(line[8]))
                    .shortDescription(line[9])
                    .designer(line[10])
                    .depth(parseInteger(line[11]))
                    .height(parseInteger(line[12]))
                    .width(parseInteger(line[13]))
                    .build();
        } catch (Exception e) {
            System.out.println("Error parsing line: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void saveBatch(List<Product> batch) {
        try {
            System.out.println("SHOULD Saving " + batch.size() + " products");
            productRepository.saveAll(batch);
            System.out.println("SAVED");
        } catch (Exception e) {
            System.out.println("Batch failed, saving individually...");
            for (Product p : batch) {
                try {
                    productRepository.save(p);
                } catch (Exception ex) {
                    System.out.println("Failed to save product: " + p + " Reason: " + ex.getMessage());
                }
            }
        }
    }

    private String[] convertEmptyToNull(String[] line) {
        if (line == null) return null;
        for (int i = 0; i < line.length; i++) {
            if (line[i] == null || line[i].trim().isEmpty()) {
                line[i] = null;
            }
        }
        return line;
    }

    // Helper methods
    private Integer parseInteger(String value) {
        try {
            return (value == null) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value == null || value.equalsIgnoreCase("false old price")) ? null : new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        if (value == null) return null;
        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;
        return null;
    }
}

package tomykulak.furniturestore.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Component responsible for importing product data from a CSV file.
 * Reads the file line by line, validates each value, and sets invalid or empty values to null by default.
 * Implements CommandLineRunner to trigger the import process on application startup.
 */
@Component
public class DataImporter implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final ResourceLoader resourceLoader;

    public DataImporter(ProductRepository productRepository, ResourceLoader resourceLoader) {
        this.productRepository = productRepository;
        this.resourceLoader = resourceLoader;
    }
    // Controls product data import on startup; set in application properties.
    @Value("${app.import-products-on-startup:true}")
    private boolean importOnStartup;

    // Executes on every application startup.
    // If product import is enabled and the repository is empty, triggers the product import process.
    @Override
    public void run(String... args) {
        if (importOnStartup && productRepository.count() == 0) {
            importProducts();
        }
    }
    // Imports products from the specified CSV file in batches.
    // Reads the CSV file from the classpath and processes its contents in batches for efficient database insertion.
    public void importProducts() {
        Resource resource = resourceLoader.getResource("classpath:static/ikea_data.csv");
        final int BATCH_SIZE = 500;
        // Read and process products from the CSV file in batches
        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            processCsvInBatches(reader, BATCH_SIZE);
        } catch (IOException | CsvValidationException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
    // Processes the CSV file in batches, parsing each line into a Product object and saving them in batch.
    // Skips the header line, applies necessary data transformations, and ensures batch saving for efficiency.
    private void processCsvInBatches(CSVReader reader, int batchSize) throws IOException, CsvValidationException {
        List<Product> batch = new ArrayList<>(batchSize);
        String[] line;
        boolean isFirstLine = true;
        // Read CSV line by line
        while ((line = reader.readNext()) != null) {
            // Skip the header line
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            // Truncate overly limit strings in the designer field
            if (line[10].length() > 250) {
                line[10] = line[10].substring(0, 247) + "...";
            }
            // Remove product code pattern at the start of the designer field
            line[10] = line[10].replaceFirst("^\\d{3}\\.\\d{3}\\.\\d{2}\\s*", "");

            // Parse product attributes
            Product product = parseProductLine(line);
            if (product != null) {
                batch.add(product);
            }
            // Save batch when full
            if (batch.size() == batchSize) {
                saveBatch(batch);
                batch.clear();
            }
        }
        // Save any remaining products
        if (!batch.isEmpty()) {
            saveBatch(batch);
        }
    }
    // Parses a CSV line and constructs a Product object from its fields.
    // Returns null if the line is invalid or cannot be parsed.
    private Product parseProductLine(String[] line) {
        try {
            if (line.length < 14) {
                System.out.println("Skipping short line: " + Arrays.toString(line));
                return null;
            }
            line = convertEmptyToNull(line);
            return Product.builder()
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
    // Attempts to save the entire batch of products in a single operation.
    // If the batch save fails, falls back to saving each product individually.
    private void saveBatch(List<Product> batch) {
        try {
            System.out.println("SHOULD Saving " + batch.size() + " products");
            productRepository.saveAll(batch);
            System.out.println("SAVED");
        } catch (Exception e) {
            System.out.println("Batch failed, saving individually...");
            for (Product p : batch) {
                try {
                    if (!productRepository.existsById(p.getId())) {
                        productRepository.save(p);
                    }
                } catch (Exception ex) {
                    System.out.println("Failed to save product: " + p + " Reason: " + ex.getMessage());
                }
            }
        }
    }
    // Helper method
    private String[] convertEmptyToNull(String[] line) {
        if (line == null) return null;
        for (int i = 0; i < line.length; i++) {
            if (line[i] == null || line[i].trim().isEmpty()) {
                line[i] = null;
            }
        }
        return line;
    }

    // Helper method
    private Integer parseInteger(String value) {
        try {
            return (value == null) ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    // Helper method
    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value == null || value.equalsIgnoreCase("false old price")) ? null : new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    // Helper method
    private Boolean parseBoolean(String value) {
        if (value == null) return null;
        if (value.equalsIgnoreCase("true")) return true;
        if (value.equalsIgnoreCase("false")) return false;
        return null;
    }
}

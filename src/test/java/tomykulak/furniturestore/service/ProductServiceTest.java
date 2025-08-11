package tomykulak.furniturestore.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tomykulak.furniturestore.dto.ProductDto;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(UUID.randomUUID())
                .name("TestName")
                .category("TestCategory")
                .price(BigDecimal.valueOf(99.99))
                .build();

        productDto = new ProductDto();
        productDto.setName("TestName");
        productDto.setCategory("TestCategory");
        productDto.setPrice(BigDecimal.valueOf(99.99));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProductById() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product productFound = productService.getProductById(product.getId());

        assertNotNull(productFound);
        assertEquals(product.getName(), productFound.getName());
        verify(productRepository).findById(product.getId());
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        var productsFound = productService.getAllProducts();

        assertNotNull(productsFound);
        assertEquals(product.getName(), productsFound.getFirst().getName());
        verify(productRepository).findAll();
    }

    @Test
    void createProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product productCreated = productService.createProduct(productDto);

        assertNotNull(productCreated);
        assertEquals(productDto.getName(), productCreated.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(product);

        assertNotNull(updatedProduct);
        assertEquals(product.getName(), updatedProduct.getName());
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct() {
        UUID productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product deletedProduct = productService.deleteProduct(productId);

        assertTrue(deletedProduct.getIsDeleted(), "Product should be marked as deleted");
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }
}
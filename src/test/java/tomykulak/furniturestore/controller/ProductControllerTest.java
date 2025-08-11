package tomykulak.furniturestore.controller;

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
import tomykulak.furniturestore.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProduct() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
    }
}
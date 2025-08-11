package tomykulak.furniturestore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tomykulak.furniturestore.config.ApiPaths;
import tomykulak.furniturestore.dto.ProductDto;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_API + "products")
@Tag(name="Product", description = "Product controller API")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product", description = "Adds a new product to the store")
    public Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get product by ID", description = "Returns a single product")
    public Product getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Get all products", description = "Return multiple products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update product by id", description = "Updates a single product")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Product by id", description = "Soft deletes a single product")
    public Product deleteProductById(@PathVariable UUID id){
        return productService.deleteProduct(id);
    }
}

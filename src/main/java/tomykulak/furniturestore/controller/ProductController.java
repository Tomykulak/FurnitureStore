package tomykulak.furniturestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tomykulak.furniturestore.config.ApiPaths;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.service.ProductService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.BASE_API + "products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(){
        // TODO: create product
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable UUID id, @RequestBody Product product){
        // TODO: update product
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable UUID id){
        // TODO: Delete product
    }
}

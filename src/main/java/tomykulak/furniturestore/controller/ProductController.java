package tomykulak.furniturestore.controller;

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
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @GetMapping
    @ResponseBody
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable UUID id){
        // TODO: Delete product
    }
}

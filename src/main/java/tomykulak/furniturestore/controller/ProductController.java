package tomykulak.furniturestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tomykulak.furniturestore.config.ApiPaths;
import tomykulak.furniturestore.service.ProductService;

@RestController
@RequestMapping(ApiPaths.BASE_API)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public String sayHello(){
        return productService.sayHello();
    }
}

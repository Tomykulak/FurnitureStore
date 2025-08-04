package tomykulak.furniturestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository ;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String sayHello(){
        return "Hello World from service.";
    }

    public void createProduct(){
        Product product = new Product();
    }
}

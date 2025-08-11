package tomykulak.furniturestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomykulak.furniturestore.exception.NotFoundException;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(UUID id){
        return this.productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void createProduct(){
        /*
        Product product = Product.builder()
                        .id()
                        .name()
                        .category()
                        .price()
                        .old_price()
                        .sellable_online()
                        .link()
                        .other_colors()
                        .short_description()
                        .designer()
                        .depth()
                        .height()
                        .width()
                        .build();
        productRepository.save(product);
        */
    }
}

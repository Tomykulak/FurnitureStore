package tomykulak.furniturestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomykulak.furniturestore.dto.ProductDto;
import tomykulak.furniturestore.exception.NotFoundException;
import tomykulak.furniturestore.model.Product;
import tomykulak.furniturestore.repository.ProductRepository;

import java.util.List;
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

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Product createProduct(ProductDto productDto){
        Product product = Product.builder()
                        .name(productDto.getName())
                        .category(productDto.getCategory())
                        .price(productDto.getPrice())
                        .oldPrice(productDto.getOldPrice())
                        .sellableOnline(productDto.getSellableOnline())
                        .link(productDto.getLink())
                        .otherColors(productDto.getOtherColors())
                        .shortDescription(productDto.getShortDescription())
                        .designer(productDto.getDesigner())
                        .depth(productDto.getDepth())
                        .height(productDto.getHeight())
                        .width(productDto.getWidth())
                        .build();
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        return this.productRepository.save(product);
    }
}

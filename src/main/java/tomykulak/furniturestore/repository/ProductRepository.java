package tomykulak.furniturestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tomykulak.furniturestore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}

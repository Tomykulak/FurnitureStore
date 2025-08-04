package tomykulak.furniturestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tomykulak.furniturestore.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
}

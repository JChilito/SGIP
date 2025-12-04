package dev.chilito.backend.inventory.application.output;

import java.util.List;
import java.util.Optional;

import dev.chilito.backend.inventory.domain.models.Product;

public interface IProductOutPort {
    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long id);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    boolean existsBySku(String sku);
}

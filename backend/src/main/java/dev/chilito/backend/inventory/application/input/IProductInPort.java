package dev.chilito.backend.inventory.application.input;

import java.util.List;

import dev.chilito.backend.inventory.domain.models.Product;

public interface IProductInPort {
    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();
}

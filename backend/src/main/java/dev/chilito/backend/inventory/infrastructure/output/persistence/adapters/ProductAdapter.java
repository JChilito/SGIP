package dev.chilito.backend.inventory.infrastructure.output.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.chilito.backend.inventory.application.output.IProductOutPort;
import dev.chilito.backend.inventory.domain.models.Product;
import dev.chilito.backend.inventory.infrastructure.output.persistence.entities.ProductEntity;
import dev.chilito.backend.inventory.infrastructure.output.persistence.mappers.ProductMapper;
import dev.chilito.backend.inventory.infrastructure.output.persistence.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements IProductOutPort {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return productMapper.toDomain(savedProductEntity);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity updatedProductEntity = productRepository.save(productEntity);
        return productMapper.toDomain(updatedProductEntity);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productMapper.toDomainList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

}

package dev.chilito.backend.inventory.infrastructure.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.chilito.backend.inventory.infrastructure.output.persistence.entities.ProductEntity;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);
}

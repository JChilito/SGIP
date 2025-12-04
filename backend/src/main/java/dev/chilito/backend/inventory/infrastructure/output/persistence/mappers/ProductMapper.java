package dev.chilito.backend.inventory.infrastructure.output.persistence.mappers;

import dev.chilito.backend.inventory.domain.models.Product;
import dev.chilito.backend.inventory.infrastructure.output.persistence.entities.ProductEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    /**
     * Map a object Product to a object ProductEntity
     * 
     * @param product
     * @return
     */
    ProductEntity toEntity(Product product);

    /**
     * Map a object ProductEntity to a object Product
     * 
     * @param entity
     * @return
     */
    Product toDomain(ProductEntity entity);

    /**
     * Map a list of objects ProductEntity to a list of objects Product
     * 
     * @param entities
     * @return
     */
    List<Product> toDomainList(List<ProductEntity> entities);

}

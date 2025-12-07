package dev.chilito.backend.inventory.infrastructure.input.rest.product.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import dev.chilito.backend.inventory.domain.models.Product;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.request.CreateProductRequest;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.request.UpdateProductRequest;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.response.ProductResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductRestMapper {

    /**
     * Map a object CreateProductRequest to a object Product
     * 
     * @param request
     * @return
     */
    Product toDomain(CreateProductRequest request);

    /**
     * Map a object UpdateProductRequest to a object Product
     * 
     * @param request
     * @return
     */
    Product toDomain(UpdateProductRequest request);

    /**
     * Map a object Product to a object ProductResponse
     * 
     * @param product
     * @return
     */
    ProductResponse toResponse(Product product);

    /**
     * Map a list of objects Product to a list of objects ProductResponse
     * 
     * @param products
     * @return
     */
    List<ProductResponse> toResponseList(List<Product> products);
}

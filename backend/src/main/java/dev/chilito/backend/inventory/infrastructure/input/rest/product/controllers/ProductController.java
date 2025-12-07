package dev.chilito.backend.inventory.infrastructure.input.rest.product.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.chilito.backend.inventory.application.input.IProductInPort;
import dev.chilito.backend.inventory.domain.models.Product;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.request.CreateProductRequest;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.request.UpdateProductRequest;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.response.ProductResponse;
import dev.chilito.backend.inventory.infrastructure.input.rest.product.mappers.ProductRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductInPort productService;
    private final ProductRestMapper productRestMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product productDomain = productRestMapper.toDomain(request);
        Product productDomainCreated = productService.createProduct(productDomain);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRestMapper.toResponse(productDomainCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        Product productDomain = productRestMapper.toDomain(request);
        Product updatedProduct = productService.updateProduct(productDomain, id);

        return ResponseEntity.ok(productRestMapper.toResponse(updatedProduct));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(productRestMapper.toResponseList(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productRestMapper.toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

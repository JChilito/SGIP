package dev.chilito.backend.inventory.application.usecases;

import java.util.List;

import dev.chilito.backend.inventory.application.input.IProductInPort;
import dev.chilito.backend.inventory.application.output.IProductOutPort;
import dev.chilito.backend.inventory.domain.models.Product;
import dev.chilito.backend.shared.domain.exception.BusinessException;
import dev.chilito.backend.shared.domain.exception.ErrorCode;

public class ProductService implements IProductInPort {

    private final IProductOutPort productOutPort;

    public ProductService(IProductOutPort productOutPort) {
        this.productOutPort = productOutPort;
    }

    @Override
    public Product createProduct(Product product) {
        // TODO: Validate if the category and provider exist
        if (productOutPort.existsBySku(product.getSku())) {
            throw new BusinessException(ErrorCode.ENTITY_ALREADY_EXISTS,
                    "Product with sku " + product.getSku() + " already exists");
        }
        return productOutPort.saveProduct(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product existingProduct = productOutPort.getProductById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "Product with id " + id + " does not exist"));

        if (this.productOutPort.existsBySku(product.getSku()) && !product.getSku().equals(existingProduct.getSku())) {
            throw new BusinessException(ErrorCode.ENTITY_ALREADY_EXISTS,
                    "Product with sku " + product.getSku() + " already exists");
        }
        // TODO: Validate if the category and provider exist
        existingProduct.setCategoryId(product.getCategoryId());
        existingProduct.setSupplierId(product.getSupplierId());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());

        existingProduct.updatePrices(product.getCostPrice(), product.getSellPrice());

        existingProduct.setMinStock(product.getMinStock());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setImageUrl(product.getImageUrl());
        return productOutPort.saveProduct(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (productOutPort.getProductById(id).isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "Product with id " + id + " does not exist");
        }
        productOutPort.deleteProduct(id);
    }

    @Override
    public Product getProductById(Long id) {
        return productOutPort.getProductById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND,
                        "Product with id " + id + " does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productOutPort.getAllProducts();
    }

}

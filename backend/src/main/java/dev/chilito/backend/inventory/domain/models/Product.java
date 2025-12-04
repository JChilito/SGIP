package dev.chilito.backend.inventory.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a physical item available for sale or storage.
 * Central class of the inventory module.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
    private Long id;
    private Long categoryId;
    private Long supplierId;

    /**
     * Unique internal identification code (Stock Keeping Unit).
     * Must be unique in the entire system. Free format (alphanumeric).
     */
    private String sku;

    private String name;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal sellPrice;

    /**
     * Minimum stock quantity allowed in storage before generating a restock alert.
     * If stockQuantity < minStock, the product is considered in "LOW_STOCK" state.
     */
    private Integer minStock;

    /**
     * Current physical quantity available in storage.
     * This value decreases with each confirmed Order and increases with Stock
     * Entries.
     */
    private Integer stockQuantity;

    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Domain Methods

    /**
     * Checks if the product has sufficient stock to fulfill an order.
     * 
     * @param quantity
     * @return true if the product has sufficient stock, false otherwise
     */
    public boolean hasSufficientStock(Integer quantity) {
        return stockQuantity >= quantity;
    }

    /**
     * Reduces the stock quantity of the product by the specified quantity.
     * 
     * @param quantity the quantity to reduce the stock by
     * @throws IllegalArgumentException if the quantity is negative or if there is
     *                                  not enough stock
     */
    public void reduceStock(Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity to reduce must be positive.");
        }
        if (!hasSufficientStock(quantity)) {
            throw new IllegalArgumentException("There is not enough stock for the product: " + this.sku);
        }
        this.stockQuantity -= quantity;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Increases the stock quantity of the product by the specified quantity.
     * 
     * @param quantity the quantity to increase the stock by
     * @throws IllegalArgumentException if the quantity is negative
     */
    public void addStock(Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity to add must be positive.");
        }
        this.stockQuantity += quantity;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the prices of the product.
     * 
     * @param newCostPrice the new cost price of the product
     * @param newSellPrice the new sell price of the product
     * @throws IllegalArgumentException if the new cost price or sell price is
     *                                  negative or if the sell price is less than
     *                                  the cost price
     */
    public void updatePrices(BigDecimal newCostPrice, BigDecimal newSellPrice) {
        if (newCostPrice.compareTo(BigDecimal.ZERO) < 0 || newSellPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The cost price and sell price cannot be negative.");
        }
        if (newSellPrice.compareTo(newCostPrice) < 0) {
            throw new IllegalArgumentException("The sell price cannot be less than the cost price.");
        }
        this.costPrice = newCostPrice;
        this.sellPrice = newSellPrice;
        this.updatedAt = LocalDateTime.now();
    }
}

package dev.chilito.backend.inventory.infrastructure.output.persistence.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// This allows that the events that happen in the entity are notified to another
// external class, in this case it is used for the entity audit
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;
    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;
    @Column(name = "sell_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellPrice;
    @Column(name = "min_stock", nullable = false)
    private Integer minStock;
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;
    @Column(name = "image_url", nullable = false, length = 250)
    private String imageUrl;
    @CreatedDate // This annotation captures automatically the creation date (audit field)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate // This annotation captures automatically the update date (audit field)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // TODO: Add relationships with Category and Supplier (Many to One or One to
    // Many)
}

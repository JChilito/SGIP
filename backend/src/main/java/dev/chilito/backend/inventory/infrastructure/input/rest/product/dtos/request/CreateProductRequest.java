package dev.chilito.backend.inventory.infrastructure.input.rest.product.dtos.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CreateProductRequest {
    @NotNull(message = "{product.categoryId.notnull}")
    private Long categoryId;

    @NotNull(message = "{product.supplierId.notnull}")
    private Long supplierId;

    @NotBlank(message = "{product.sku.notblank}")
    @Size(max = 50, message = "{product.sku.size}")
    private String sku;

    @NotBlank(message = "{product.name.notblank}")
    @Size(max = 100, message = "{product.name.size}")
    private String name;

    @NotBlank(message = "{product.description.notblank}")
    @Size(max = 500, message = "{product.description.size}")
    private String description;

    @Positive(message = "{product.costPrice.positive}")
    @NotNull(message = "{product.costPrice.notnull}")
    private BigDecimal costPrice;

    @Positive(message = "{product.sellPrice.positive}")
    @NotNull(message = "{product.sellPrice.notnull}")
    private BigDecimal sellPrice;

    @PositiveOrZero(message = "{product.minStock.positiveOrZero}")
    @NotNull(message = "{product.minStock.notnull}")
    private Integer minStock;

    @PositiveOrZero(message = "{product.stockQuantity.positiveOrZero}")
    @NotNull(message = "{product.stockQuantity.notnull}")
    private Integer stockQuantity;

    @NotBlank(message = "{product.imageUrl.notblank}")
    @Size(max = 250, message = "{product.imageUrl.size}")
    private String imageUrl;
}

package com.karthi.ecommerce.product;

import com.karthi.ecommerce.category.Category;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product name must be specified")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @NotNull(message = "Product stock level must be specified")
        Integer stockLevel,
        @NotNull(message = "Product unit price must be specified")
        BigDecimal price,
        @NotNull(message = "Product category is required")
        Integer categoryId
) {
}

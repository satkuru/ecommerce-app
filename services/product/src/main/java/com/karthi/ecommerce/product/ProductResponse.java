package com.karthi.ecommerce.product;

import com.karthi.ecommerce.category.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Integer stockLevel,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}

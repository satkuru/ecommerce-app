package com.karthi.ecommerce.product;

import java.math.BigDecimal;

public record PurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}

package com.karthi.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(
        @NotNull(message = "Product Id must be specified")
        Long productId,
        @NotNull(message = "Specify quantity for the purchase")
        Integer quantity
) {
}

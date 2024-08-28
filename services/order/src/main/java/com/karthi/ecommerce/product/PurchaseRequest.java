package com.karthi.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PurchaseRequest(
        @NotNull(message = "Must specify product Id")
        Integer productId,
        @Positive(message = "Quantity must be a positive value")
        Integer quantity
) {

}

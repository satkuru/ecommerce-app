package com.karthi.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.karthi.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@JsonInclude(Include.NON_EMPTY)
public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount must be a positive value")
        BigDecimal amount,
        @NotNull(message = "A payment method must be specified")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer Id cannot be null")
        @NotEmpty(message = "Customer Id cannot be empty")
        @NotBlank(message = "Customer Id cannot be blank")
        String customerId,
        @NotEmpty(message = "At least one product must be specified")
        List<PurchaseRequest> products
) {
}

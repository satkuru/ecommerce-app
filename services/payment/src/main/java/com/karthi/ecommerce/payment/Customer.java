package com.karthi.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "Firstname is require for customer")
        String firstname,
        @NotNull(message = "Lastname os required for customer")
        String lastname,
        @NotNull(message = "must provide an email address")
        @Email(message = "A valid email address is required")
        String email
) {
}

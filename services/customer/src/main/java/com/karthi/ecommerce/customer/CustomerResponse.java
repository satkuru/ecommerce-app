package com.karthi.ecommerce.customer;

public record CustomerResponse(
        String firstname,
        String lastname,
        String email,
        Address address
) {
}

package com.karthi.ecommerce.product.exception;

public class ProductNotFoundException extends RuntimeException {
    private final String message;

    public ProductNotFoundException(String message) {
        this.message = message;
    }
}

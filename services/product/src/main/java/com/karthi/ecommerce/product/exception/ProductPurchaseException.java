package com.karthi.ecommerce.product.exception;

public class ProductPurchaseException extends RuntimeException {
    private final String message;

    public ProductPurchaseException(String message) {
        this.message = message;
    }
}

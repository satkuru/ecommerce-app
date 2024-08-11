package com.karthi.ecommerce.customer.exception;

public class CustomerNotFoundException extends RuntimeException{
    private final String message;

    public CustomerNotFoundException(String message) {
        this.message = message;
    }
}

package com.foodeo.backend.exception;

public class OrderAlreadyCheckoutException extends Exception {

    public OrderAlreadyCheckoutException(String message) {
        super(message);
    }
}

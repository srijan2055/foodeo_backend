package com.foodeo.backend.exception;

public class OrderAlreadyDeliverException extends Exception {

    public OrderAlreadyDeliverException(String message) {
        super(message);
    }
}

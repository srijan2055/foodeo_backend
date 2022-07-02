package com.foodeo.backend.exception;

public class OrderNotFinishedException extends Exception {

    public OrderNotFinishedException(String message) {
        super(message);
    }
}

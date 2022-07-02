package com.foodeo.backend.exception;

public class OrderAlreadyFinishException extends Exception {

    public OrderAlreadyFinishException(String message) {
        super(message);
    }
}

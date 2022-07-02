package com.foodeo.backend.exception;

public class UserNotExistException extends Exception {

    public UserNotExistException(String message) {
        super(message);
    }
}

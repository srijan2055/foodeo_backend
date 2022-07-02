package com.foodeo.backend.exception;

public class PasswordNotMatchException extends Exception {

    public PasswordNotMatchException(String message) {
        super(message);
    }
}

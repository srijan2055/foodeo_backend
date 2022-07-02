package com.foodeo.backend.exception;

public class DishNotExistException extends Exception {

    public DishNotExistException(String message) {
        super(message);
    }
}

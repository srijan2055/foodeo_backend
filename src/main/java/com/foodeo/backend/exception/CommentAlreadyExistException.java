package com.foodeo.backend.exception;

public class CommentAlreadyExistException extends Exception {

    public CommentAlreadyExistException(String message) {
        super(message);
    }
}

package com.example.speedoTransfer.exception.custom;

public class UserDoesNotExistsException extends Throwable {
    public UserDoesNotExistsException(String userDoesNotExists) {
        super(userDoesNotExists);
    }
}

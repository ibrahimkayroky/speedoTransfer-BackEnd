package com.example.speedoTransfer.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}

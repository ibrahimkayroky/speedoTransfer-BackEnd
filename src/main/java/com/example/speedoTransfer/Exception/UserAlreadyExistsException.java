package com.example.speedoTransfer.Exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}

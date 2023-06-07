package com.example.bikego.exception;

public class RegistrationException extends Exception{
    public RegistrationException(String message) {
        super(message);
    }
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

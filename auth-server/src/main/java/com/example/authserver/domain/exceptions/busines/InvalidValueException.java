package com.example.authserver.domain.exceptions.busines;

public class InvalidValueException extends ApiException{
    public InvalidValueException(String message) {
        super(message);
    }
    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

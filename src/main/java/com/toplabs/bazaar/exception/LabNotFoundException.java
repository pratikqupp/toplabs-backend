package com.toplabs.bazaar.exception;

public class LabNotFoundException extends RuntimeException{
    public LabNotFoundException(String message) {
        super(message);
    }

    public LabNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

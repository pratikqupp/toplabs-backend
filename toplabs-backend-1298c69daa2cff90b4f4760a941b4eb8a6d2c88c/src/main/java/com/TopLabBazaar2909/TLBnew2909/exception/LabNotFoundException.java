package com.TopLabBazaar2909.TLBnew2909.exception;

public class LabNotFoundException extends RuntimeException{
    public LabNotFoundException(String message) {
        super(message);
    }

    public LabNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

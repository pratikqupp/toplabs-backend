package com.TopLabBazaar2909.TLBnew2909.exception;

public class CampNotFoundException extends RuntimeException{
    public CampNotFoundException(String message) {
        super(message);
    }

    public CampNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

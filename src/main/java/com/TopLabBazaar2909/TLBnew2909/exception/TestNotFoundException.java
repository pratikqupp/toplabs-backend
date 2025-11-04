package com.TopLabBazaar2909.TLBnew2909.exception;

public class TestNotFoundException extends RuntimeException{
    public TestNotFoundException(String message) {
        super(message);
    }

    public TestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

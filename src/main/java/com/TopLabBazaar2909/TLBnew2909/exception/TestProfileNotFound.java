package com.TopLabBazaar2909.TLBnew2909.exception;

public class TestProfileNotFound extends RuntimeException{
    public TestProfileNotFound(String message) {
        super(message);
    }

    public TestProfileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}

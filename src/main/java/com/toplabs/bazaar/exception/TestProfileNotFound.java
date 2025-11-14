package com.toplabs.bazaar.exception;

public class TestProfileNotFound extends RuntimeException{
    public TestProfileNotFound(String message) {
        super(message);
    }

    public TestProfileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}

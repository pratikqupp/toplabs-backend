package com.toplabs.bazaar.exception;

public class LabLogoPhotoProcessingException extends RuntimeException{
    public LabLogoPhotoProcessingException(String message) {
        super(message);
    }

    public LabLogoPhotoProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.toplabs.bazaar.exception;

public class PatientCampRegistrationNotFoundException extends RuntimeException{
    public PatientCampRegistrationNotFoundException(String message) {
        super(message);
    }

    public PatientCampRegistrationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

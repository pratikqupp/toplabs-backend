package com.TopLabBazaar2909.TLBnew2909.exception;

public class PatientCampRegistrationNotFoundException extends RuntimeException{
    public PatientCampRegistrationNotFoundException(String message) {
        super(message);
    }

    public PatientCampRegistrationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

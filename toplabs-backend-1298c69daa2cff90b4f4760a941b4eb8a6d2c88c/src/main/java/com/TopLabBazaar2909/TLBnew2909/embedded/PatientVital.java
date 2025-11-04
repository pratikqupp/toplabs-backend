package com.TopLabBazaar2909.TLBnew2909.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PatientVital {
    private int systolic;
    private int diastolic;
    private int spo2;

    private float height;
    private float weight;

    private float bmi;
    private int heartRate;
}
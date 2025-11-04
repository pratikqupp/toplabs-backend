package com.TopLabBazaar2909.TLBnew2909.resource;

import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import lombok.Data;

@Data
public class SampleCountReport {
    private String labName;
    private SampleType sampleType;
    private String testName;
    private int count;
    private Boolean isComplementary;
}

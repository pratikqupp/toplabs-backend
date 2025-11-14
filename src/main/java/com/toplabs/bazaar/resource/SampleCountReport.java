package com.toplabs.bazaar.resource;

import com.toplabs.bazaar.embedded.SampleType;
import lombok.Data;

@Data
public class SampleCountReport {
    private String labName;
    private SampleType sampleType;
    private String testName;
    private int count;
    private Boolean isComplementary;
}

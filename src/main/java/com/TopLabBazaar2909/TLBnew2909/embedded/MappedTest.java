package com.TopLabBazaar2909.TLBnew2909.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MappedTest {
    private String testId;
    private String name;
    private SampleType sampleType;
    private String description;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
}

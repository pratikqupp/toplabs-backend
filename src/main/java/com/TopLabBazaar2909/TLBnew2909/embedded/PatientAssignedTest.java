package com.TopLabBazaar2909.TLBnew2909.embedded;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PatientAssignedTest {
    private String campTestMappingId;

    private String testId;
    private String testName;
    private SampleType sampleType;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;

    private String labId;
    private String labName;
    private String logoS3Path;

    private Boolean isComplementary;
    private Integer campPrice;
    private Integer mmdPrice;
    private Integer mrp;
}

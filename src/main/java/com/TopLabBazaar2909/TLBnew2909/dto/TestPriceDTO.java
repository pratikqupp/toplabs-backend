package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPriceDTO {
    private double b2bPrice;
    private double mmdPrice;
    private double mrp;
    private String note;
    private String sampleTubeType;
    private String specimen;
    private String testId;
    private String testName;
}

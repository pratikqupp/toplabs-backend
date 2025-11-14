package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestMappingDTO {
    private String testId;
    private String testName;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
}

package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabPriceDTO {
    private double totalMmdPrice;
    private double totalMrp;
    private String labId;
    private String labName;
    private String logoS3Path;
    private List<TestPriceDTO> testPrices;
}

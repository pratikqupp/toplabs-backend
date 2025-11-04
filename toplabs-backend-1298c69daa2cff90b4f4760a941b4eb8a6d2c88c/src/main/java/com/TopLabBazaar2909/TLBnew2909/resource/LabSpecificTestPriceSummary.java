package com.TopLabBazaar2909.TLBnew2909.resource;


import com.TopLabBazaar2909.TLBnew2909.entity.TestPrice;
import lombok.Data;

import java.util.List;

@Data
public class LabSpecificTestPriceSummary  {
    private String labId;
    private String labName;
    private String logoS3Path;
    private List<TestPrice> testPrices;
}

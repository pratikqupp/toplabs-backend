package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.entity.TestPrice;
import lombok.Data;

import java.util.List;

@Data
public class LabSpecificTestPriceSummary  {
    private String labId;
    private String labName;
    private String logoS3Path;
    private List<TestPrice> testPrices;
}

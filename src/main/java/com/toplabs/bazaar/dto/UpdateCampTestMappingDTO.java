package com.toplabs.bazaar.dto;


import com.toplabs.bazaar.embedded.CampTestPrice;
import com.toplabs.bazaar.embedded.SampleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCampTestMappingDTO {
    private String testName;  // For denormalized display
    private SampleType sampleType;
    private List<CampTestPrice> testMappedLabs;
}

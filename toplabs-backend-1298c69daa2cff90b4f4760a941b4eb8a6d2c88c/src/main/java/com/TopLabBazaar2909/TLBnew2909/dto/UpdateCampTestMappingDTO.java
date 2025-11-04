package com.TopLabBazaar2909.TLBnew2909.dto;


import com.TopLabBazaar2909.TLBnew2909.embedded.CampTestPrice;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
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

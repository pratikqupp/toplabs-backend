package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.CampTestPrice;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampTestMappingDTO {
    private String campId;    // FK to Camp
    private String testId;    // FK to Test
    private String testName;  // For denormalized display
    private SampleType sampleType;
    private SampleTubeType sampleTubeType;
    private String specimen;
    private String note;
    private String Camp;
    private String Test;
    private List<CampTestPrice> testMappedLabs;
}

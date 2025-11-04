package com.TopLabBazaar2909.TLBnew2909.resource;

import com.TopLabBazaar2909.TLBnew2909.embedded.CampTestPrice;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampTestMappingResource extends RepresentationModel<CampTestMappingResource> {

    private String campTestMappingId;
    private String campId;    // FK to Camp
    private String testId;    // FK to Test
    private String testName;  // For denormalized display
    private SampleType sampleType;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;

    private List<CampTestPrice> testMappedLabs;
}

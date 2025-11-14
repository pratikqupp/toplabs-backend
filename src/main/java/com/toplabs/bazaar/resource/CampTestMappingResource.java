package com.toplabs.bazaar.resource;

import com.toplabs.bazaar.embedded.CampTestPrice;
import com.toplabs.bazaar.embedded.SampleTubeType;
import com.toplabs.bazaar.embedded.SampleType;
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

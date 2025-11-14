package com.toplabs.bazaar.resource;

import com.toplabs.bazaar.embedded.SampleTubeType;
import com.toplabs.bazaar.embedded.SampleType;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class BasicInfoTestResource extends RepresentationModel<BasicInfoTestResource> {

    private String testId;
    private String name;
    private SampleType sampleType;
    private String description;
    private Boolean favorite;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
}


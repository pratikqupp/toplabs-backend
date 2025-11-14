package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.embedded.SampleTubeType;
import com.toplabs.bazaar.embedded.SampleType;
import com.toplabs.bazaar.entity.TestMappedLab;
import lombok.Data;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class TestResource extends RepresentationModel<TestResource> {

    private String testId;
    private String name;
    private SampleType sampleType;
    private String description;
    private Boolean favorite;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
    private List<TestMappedLab> testMappedLabs;

}

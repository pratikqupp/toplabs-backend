package com.TopLabBazaar2909.TLBnew2909.resource;

import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
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


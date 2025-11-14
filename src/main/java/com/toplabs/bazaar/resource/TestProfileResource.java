package com.toplabs.bazaar.resource;

import com.toplabs.bazaar.embedded.MappedTest;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class TestProfileResource extends RepresentationModel<TestProfileResource> {
    private String testProfileId;
    private String name;
    private String labId;
    private String labName;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
    private String logoS3Path;
    private List<MappedTest> mappedTests;
}

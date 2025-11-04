package com.TopLabBazaar2909.TLBnew2909.resource;


import com.TopLabBazaar2909.TLBnew2909.embedded.MappedTest;
import com.TopLabBazaar2909.TLBnew2909.entity.TestProfile;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class PackageResource extends RepresentationModel<PackageResource> {
    private String packageId;
    private String name;
    private String categoryId;
    private String labId;
    private List<MappedTest> mappedTests;
    private List<TestProfile> testProfiles;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
}

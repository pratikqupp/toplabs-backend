package com.TopLabBazaar2909.TLBnew2909.resourceAssembler;

import com.TopLabBazaar2909.TLBnew2909.controller.PackageController;
import com.TopLabBazaar2909.TLBnew2909.embedded.MappedTest;
import com.TopLabBazaar2909.TLBnew2909.entity.LabPackage;
import com.TopLabBazaar2909.TLBnew2909.resource.PackageResource;
import com.TopLabBazaar2909.TLBnew2909.entity.TestProfile;
import com.TopLabBazaar2909.TLBnew2909.repository.TestProfileRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.TestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PackageResourceAssembler extends RepresentationModelAssemblerSupport<LabPackage, PackageResource> {

    private final ModelMapper modelMapper;
    private final TestRepository testRepository;
    private final TestProfileRepository testProfileRepository;

    @Autowired
    public PackageResourceAssembler(ModelMapper modelMapper, TestRepository testRepository,
                                    TestProfileRepository testProfileRepository) {
        super(PackageController.class, PackageResource.class);
        this.modelMapper = modelMapper;
        this.testRepository = testRepository;
        this.testProfileRepository = testProfileRepository;
    }

    @Override
    public PackageResource toModel(LabPackage entity) {
        PackageResource resource = new PackageResource();
        resource.setPackageId(entity.getId());
        resource.setName(entity.getName());
        resource.setCategoryId(entity.getCategoryId());
        resource.setLabId(entity.getLabId());
        resource.setB2bPrice(entity.getB2bPrice());
        resource.setMrp(entity.getMrp());
        resource.setMmdPrice(entity.getMmdPrice());

        // Map testIds to MappedTest
        List<MappedTest> mappedTests = Optional.ofNullable(entity.getTestIds())
                .orElse(Collections.emptyList())
                .stream()
                .map(testId -> testRepository.findById(testId)) // <-- lambda
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(test -> modelMapper.map(test, MappedTest.class))
                .collect(Collectors.toList());

        resource.setMappedTests(mappedTests);

        // Map profileIds to TestProfile
        List<TestProfile> testProfiles = Optional.ofNullable(entity.getProfileIds())
                .orElse(Collections.emptyList())
                .stream()
                .map(profileId -> testProfileRepository.findById(profileId)) // <-- lambda
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        resource.setTestProfiles(testProfiles);

        return resource;
    }

}

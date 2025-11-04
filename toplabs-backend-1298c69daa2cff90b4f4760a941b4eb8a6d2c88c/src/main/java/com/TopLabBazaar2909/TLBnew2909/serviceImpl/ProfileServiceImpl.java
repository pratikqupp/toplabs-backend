package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.ProfileService;
import com.TopLabBazaar2909.TLBnew2909.dto.RegisterProfileDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.SearchRecordDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.UpdateProfileDTO;
import com.TopLabBazaar2909.TLBnew2909.embedded.MappedTest;
import com.TopLabBazaar2909.TLBnew2909.entity.TestProfile;
import com.TopLabBazaar2909.TLBnew2909.exception.TestProfileNotFound;
import com.TopLabBazaar2909.TLBnew2909.repository.TestProfileRepository;
import com.TopLabBazaar2909.TLBnew2909.resource.TestProfileResource;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.TestProfileResourceAssembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private TestProfileResourceAssembler testProfileResourceAssembler;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TestProfileRepository testProfileRepository;

    @Override
    public TestProfileResource createProfile(RegisterProfileDTO registerProfileDTO) {
        TestProfile testProfile = modelMapper.map(registerProfileDTO, TestProfile.class);
        TestProfile savedProfile = testProfileRepository.save(testProfile);
        return testProfileResourceAssembler.toModel(savedProfile);
    }

    @Override
    public TestProfileResource getProfileById(String id) {
        TestProfile profile = testProfileRepository.findById(id)
                .orElseThrow(() -> new TestProfileNotFound("Test profile not found"));
        return testProfileResourceAssembler.toModel(profile);
    }

    @Override
    public List<TestProfileResource> getProfilesByLabId(String labId) {
        List<TestProfile> profiles = testProfileRepository.findByLabIdOrderByNameAsc(labId);
        List<TestProfileResource> resources = new ArrayList<>();
        for (TestProfile profile : profiles) {
            resources.add(testProfileResourceAssembler.toModel(profile));
        }
        return resources;
    }

    @Override
    public Page<TestProfile> getPaginatedTestProfile(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "name");
        return testProfileRepository.findAll(pageable);
    }

    @Override
    public List<TestProfileResource> searchTestProfile(SearchRecordDTO searchRecordDTO) {
        List<TestProfile> profiles = testProfileRepository
                .findByNameContainingIgnoreCase(searchRecordDTO.getPhares());
        List<TestProfileResource> resources = new ArrayList<>();
        for (TestProfile profile : profiles) {
            resources.add(testProfileResourceAssembler.toModel(profile));
        }
        return resources;
    }

    @Override
    public TestProfileResource mapTestToProfile(String profileId, MappedTest mappedTest) {
        TestProfile profile = testProfileRepository.findById(profileId)
                .orElseThrow(() -> new TestProfileNotFound("TestProfile not found"));

        if (profile.getMappedTests() == null) {
            profile.setMappedTests(new ArrayList<>());
        }

        boolean alreadyMapped = profile.getMappedTests().stream()
                .anyMatch(existing -> existing.getTestId().equals(mappedTest.getTestId()));

        if (!alreadyMapped) {
            profile.getMappedTests().add(mappedTest);
            profile = testProfileRepository.save(profile);
        }

        return testProfileResourceAssembler.toModel(profile);
    }

    @Override
    public TestProfileResource unmapTestFromProfile(String profileId, String testId) {
        TestProfile profile = testProfileRepository.findById(profileId)
                .orElseThrow(() -> new TestProfileNotFound("TestProfile not found with ID: " + profileId));

        List<MappedTest> mappedTests = profile.getMappedTests();
        if (mappedTests != null && !mappedTests.isEmpty()) {
            mappedTests.removeIf(test -> test.getTestId().equals(testId));
            profile.setMappedTests(mappedTests);
            profile = testProfileRepository.save(profile);
        }

        return testProfileResourceAssembler.toModel(profile);
    }

    @Override
    public TestProfileResource updateProfile(String profileId, UpdateProfileDTO updateDTO) {
        TestProfile profile = testProfileRepository.findById(profileId)
                .orElseThrow(() -> new TestProfileNotFound("TestProfile not found with ID: " + profileId));

        if (updateDTO.getName() != null) {
            profile.setName(updateDTO.getName());
        }
        if (updateDTO.getB2bPrice() != null) {
            profile.setB2bPrice(updateDTO.getB2bPrice());
        }
        if (updateDTO.getMrp() != null) {
            profile.setMrp(updateDTO.getMrp());
        }
        if (updateDTO.getMmdPrice() != null) {
            profile.setMmdPrice(updateDTO.getMmdPrice());
        }

        TestProfile updated = testProfileRepository.save(profile);
        return testProfileResourceAssembler.toModel(updated);
    }
}

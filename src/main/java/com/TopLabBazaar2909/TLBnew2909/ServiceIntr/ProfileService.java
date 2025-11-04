package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;


import com.TopLabBazaar2909.TLBnew2909.dto.RegisterProfileDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.SearchRecordDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.UpdateProfileDTO;
import com.TopLabBazaar2909.TLBnew2909.embedded.MappedTest;
import com.TopLabBazaar2909.TLBnew2909.entity.TestProfile;
import com.TopLabBazaar2909.TLBnew2909.resource.TestProfileResource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfileService {

    TestProfileResource createProfile(RegisterProfileDTO registerProfileDTO);

    TestProfileResource getProfileById(String id);

    List<TestProfileResource> getProfilesByLabId(String labId);

    Page<TestProfile> getPaginatedTestProfile(int page, int size);

    List<TestProfileResource> searchTestProfile(SearchRecordDTO searchRecordDTO);

    TestProfileResource mapTestToProfile(String profileId, MappedTest mappedTest);

    TestProfileResource unmapTestFromProfile(String testProfileId, String testId);

    TestProfileResource updateProfile(String profileId, UpdateProfileDTO updateDTO);
}

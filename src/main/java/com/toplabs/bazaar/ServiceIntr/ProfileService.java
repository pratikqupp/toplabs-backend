package com.toplabs.bazaar.ServiceIntr;


import com.toplabs.bazaar.dto.RegisterProfileDTO;
import com.toplabs.bazaar.dto.SearchRecordDTO;
import com.toplabs.bazaar.dto.UpdateProfileDTO;
import com.toplabs.bazaar.embedded.MappedTest;
import com.toplabs.bazaar.entity.TestProfile;
import com.toplabs.bazaar.resource.TestProfileResource;
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

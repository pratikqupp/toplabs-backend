package com.toplabs.bazaar.ServiceIntr;


import com.toplabs.bazaar.dto.LabPackageDTO;
import com.toplabs.bazaar.dto.SearchRecordDTO;
import com.toplabs.bazaar.dto.TestProfileMappingRequest;
import com.toplabs.bazaar.entity.LabPackage;
import com.toplabs.bazaar.resource.PackageResource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PackageService {
    PackageResource createPackage(LabPackageDTO packageDTO);

    List<PackageResource> getLabWisePackage(String labId);


    List<PackageResource> searchPackageByName(SearchRecordDTO searchRecordDTO);

    PackageResource getPackageById(String packageId);


    PackageResource unmapTestAndProfiles(String packageId, TestProfileMappingRequest request);

    PackageResource mapTestAndProfiles(String packageId, TestProfileMappingRequest request);


    Page<LabPackage> getPaginatedPackages(int page, int size);

    List<PackageResource> getPackageByCategoryId(String categoryId);

    List<PackageResource> getAllPackages();

    List<PackageResource> getPackageByLabAndCategory(String labId, String categoryId);
}

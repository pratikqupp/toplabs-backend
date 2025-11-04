package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;


import com.TopLabBazaar2909.TLBnew2909.dto.LabPackageDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.SearchRecordDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.TestProfileMappingRequest;
import com.TopLabBazaar2909.TLBnew2909.entity.LabPackage;
import com.TopLabBazaar2909.TLBnew2909.resource.PackageResource;
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

package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.Enum.AppRole;
import com.TopLabBazaar2909.TLBnew2909.Enum.LocalCampStatus;
import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.entity.Camp;
import com.TopLabBazaar2909.TLBnew2909.entity.CampTestMapping;
import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;
import com.TopLabBazaar2909.TLBnew2909.resource.CampResource;
import com.TopLabBazaar2909.TLBnew2909.resource.CampTestMappingResource;
import com.TopLabBazaar2909.TLBnew2909.resource.CampUserResource;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CampService {

    CampResource registerCamp(CampDTO campDTO);

    Page<Camp> getPaginatedCamps(int page, int size, String sortBy);

    Page<Camp> getCampsByStatus(LocalCampStatus campStatus, int page, int size);

    List<CampTestMappingResource> mapCampToTest(List<CampTestMappingDTO> campTestmappingDTO);

    CampTestMappingResource toModel(CampTestMapping entity);

    List<CampTestMappingResource> getCampMappedTests(String campId);

    List<CampResource> searchCamp(SearchCampDTO searchCampDTO);

    CampResource getCampDetailsById(String campId);

    CampResource updateCampDetails(String campId , UpdateCampDTO updateCampDTO);

    CampTestMappingResource updateCampMappedTestDetails(String campTestMappingId, UpdateCampTestMappingDTO updateCampTestMappingDTO);

    List<CampTestMappingResource> getPreviousCampTestMappings();

    void addUserToCamp(MappedUserIntoTheCampDTO mappedUserIntoTheCampDTO);

    void unmapUserFromCamp(String campId, String userId);


    Page<Camp> getCampsMappedToUser(String userId, LocalCampStatus campStatus, int page, int size);

    List<CampUserResource> getCampUserResource(String campId, AppRole designation);

    List<CampResource> searchCampWithRespectToUser(SearchCampWRTCampUserDTO searchCampWRTCampUserDTO);

    void unmapCampTestMapping(UnmapLabSpecificTestFromCampDTO unmapLabSpecificTestFromCampDTO);

    void unmapLabSpecificTestFromCamp(String labId, String campId);

    // ✅ NEW METHOD for controller's new endpoint
    Optional<PatientCampRegistration> getPatientCampRegistrationById(Long id);
}

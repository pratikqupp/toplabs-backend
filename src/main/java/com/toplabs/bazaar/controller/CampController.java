package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LocalCampStatus;
import com.toplabs.bazaar.ServiceIntr.CampService;
import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.entity.Camp;
import com.toplabs.bazaar.resource.CampResource;
import com.toplabs.bazaar.resource.CampTestMappingResource;
import com.toplabs.bazaar.resource.CampUserResource;
import com.toplabs.bazaar.resourceAssembler.DetailedCampResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/camp")
public class CampController {

    private final CampService campService;
    private final DetailedCampResourceAssembler campResourceAssembler;

    @Autowired
    public CampController(CampService campService, DetailedCampResourceAssembler campResourceAssembler) {
        this.campService = campService;
        this.campResourceAssembler = campResourceAssembler;
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new camp", description = "Accessible to all authenticated users")
    public CampResource registerCamp(@RequestBody CampDTO campDTO) {
        return campService.registerCamp(campDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get paginated list of camps", description = "Accessible to all authenticated users")
    public PagedModel<CampResource> getCamps(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "institutionName") String sortBy,
            PagedResourcesAssembler<Camp> pagedResourcesAssembler
    ) {
        Page<Camp> campPage = campService.getPaginatedCamps(page, size, sortBy);
        return pagedResourcesAssembler.toModel(campPage, campResourceAssembler);
    }


   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/getCamp/{campStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get paginated list of camps based on the camp status", description = "Accessible to all authenticated users")
    public PagedModel<CampResource> getCampsBasedOnTheStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable LocalCampStatus campStatus,
            PagedResourcesAssembler<Camp> pagedResourcesAssembler
    ) {
        Page<Camp> campPage = campService.getCampsByStatus(campStatus, page, size);
        return pagedResourcesAssembler.toModel(campPage, campResourceAssembler);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/search/camp", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search camp based on the passed searched phrase", description = "Accessible to all authenticated users")
    public List<CampResource> searchCampBasedOnCampStatusAndPassedLiteral(@RequestBody SearchCampDTO searchCampDTO) {
        return campService.searchCamp(searchCampDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/test/mapping", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Map test to the camp", description = "Accessible to all authenticated users")
    public List<CampTestMappingResource> campTestMapping(@RequestBody List<CampTestMappingDTO> campTestMappingDTOList) {
        return campService.mapCampToTest(campTestMappingDTOList);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/tests/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get camp mapped tests", description = "Accessible to all authenticated users")
    public List<CampTestMappingResource> getCampTests(@PathVariable String campId) {
        return campService.getCampMappedTests(campId);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get camp details", description = "Accessible to all authenticated users")
    public CampResource getCamp(@PathVariable String campId) {
        return campService.getCampDetailsById(campId);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/update/camp/details/{campId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update camp details", description = "Accessible to all authenticated users")
    public CampResource updateCampDetails(@PathVariable String campId, @RequestBody UpdateCampDTO updateCampDTO) {
        return campService.updateCampDetails(campId, updateCampDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/mapped/test/update/{campTestMappingId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update camp mapped test details", description = "Accessible to all authenticated users")
    public CampTestMappingResource updateCampMappedTest(@PathVariable String campTestMappingId, @RequestBody UpdateCampTestMappingDTO updateCampTestMappingDTO) {
        return campService.updateCampMappedTestDetails(campTestMappingId, updateCampTestMappingDTO);
    }

//   //@PreAuthorize("isAuthenticated()")
//    @GetMapping(path = "/patientCampRegistration/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get patient camp registration by id",
//            description = "Accessible to authenticated users")
//    public DetailedPatientCampRegistrationResource getPatientCampRegistrationById(@PathVariable Long id) {
//        PatientCampRegistration registration = campService.getPatientCampRegistrationById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "PatientCampRegistration not found with id: " + id));
//        return patientRegistrationAssembler.toModel(registration);
//    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/previous/mappings", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get previous camp test mappings", description = "Accessible to all authenticated users")
    public List<CampTestMappingResource> getPreviousCampTestMappings() {
        return campService.getPreviousCampTestMappings();
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/camps/map-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Map user into the camp", description = "Accessible to all authenticated users")
    public void mapUserToCamp(@RequestBody MappedUserIntoTheCampDTO mappedUserIntoTheCampDTO) {
        campService.addUserToCamp(mappedUserIntoTheCampDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/unmap/{campId}/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Unmap user from the camp", description = "Accessible to all authenticated users")
    public void unmapUser(@PathVariable String campId, @PathVariable String userId) {
        campService.unmapUserFromCamp(campId, userId);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/camps/mapped-to-user/{userId}/{campStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get camps mapped to a user by status", description = "Accessible to any authenticated user")
    public PagedModel<CampResource> getMappedCampsWRTCampStatus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String userId,
            @PathVariable LocalCampStatus campStatus,
            PagedResourcesAssembler<Camp> pagedResourcesAssembler
    ) {
        Page<Camp> campPage = campService.getCampsMappedToUser(userId, campStatus, page, size);
        return pagedResourcesAssembler.toModel(campPage, campResourceAssembler);
    }


   //@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/get/user/{campId}/{designation}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get users of a camp by designation", description = "Accessible to any authenticated user")
    public List<CampUserResource> getCampResource(@PathVariable String campId, @PathVariable AppRole designation) {
        return campService.getCampUserResource(campId, designation);
    }

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/search/user/mapped-to-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search user mapped to a camp", description = "Accessible to any authenticated user")
    public List<CampResource> searchCampWRTCampUser(@RequestBody SearchCampWRTCampUserDTO searchCampWRTCampUserDTO) {
        return campService.searchCampWithRespectToUser(searchCampWRTCampUserDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/test/unmapping", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Unmap test from a camp", description = "Accessible to all authenticated users")
    public void unmapCampTestMapping(@RequestBody UnmapLabSpecificTestFromCampDTO unmapLabSpecificTestFromCampDTO) {
        campService.unmapCampTestMapping(unmapLabSpecificTestFromCampDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/unmap/lab/specific/test/from/{labId}/{campId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Unmap lab-specific test from a camp", description = "Accessible to all authenticated users")
    public void unmapLabSpecificTestFromCamp(@PathVariable String labId, @PathVariable String campId) {
        campService.unmapLabSpecificTestFromCamp(labId, campId);
    }
}

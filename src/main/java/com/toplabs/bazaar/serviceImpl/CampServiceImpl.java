package com.toplabs.bazaar.serviceImpl;


import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LocalCampStatus;
import com.toplabs.bazaar.ServiceIntr.CampService;
import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.embedded.CampTestPrice;
import com.toplabs.bazaar.embedded.MappedUserIntoTheCamp;
import com.toplabs.bazaar.entity.Camp;
import com.toplabs.bazaar.entity.CampTestMapping;
import com.toplabs.bazaar.entity.AppUser;
import com.toplabs.bazaar.entity.PatientCampRegistration;
import com.toplabs.bazaar.exception.CampNotFoundException;
import com.toplabs.bazaar.exception.CampTestMappingNotFoundException;
import com.toplabs.bazaar.repository.AppUserRepository;
import com.toplabs.bazaar.repository.CampRepository;
import com.toplabs.bazaar.repository.CampTestMappingRepository;
import com.toplabs.bazaar.repository.CustomCampRepository;
import com.toplabs.bazaar.resource.CampResource;
import com.toplabs.bazaar.resource.CampTestMappingResource;
import com.toplabs.bazaar.resource.CampUserResource;
import com.toplabs.bazaar.resourceAssembler.CampTestMappingResourceAssembler;
import com.toplabs.bazaar.resourceAssembler.DetailedCampResourceAssembler;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampServiceImpl implements CampService {

    @Autowired
    private CampRepository campRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DetailedCampResourceAssembler campResourceAssembler;

    @Autowired
    private CampTestMappingResourceAssembler campTestMappingResourceAssembler;

    @Autowired
    private CampTestMappingRepository campTestMappingRepository;

    @Autowired
    private CustomCampRepository customCampRepository;

    @Autowired
    private AppUserRepository appUserRepository;



    @Override
    public CampResource registerCamp(CampDTO campDTO) {
        Camp camp = modelMapper.map(campDTO, Camp.class);
        camp.setUniqueCampNumber(campUniqueCampNumberGenerator());
        return campResourceAssembler.toModel(campRepository.save(camp));
    }

    private Long campUniqueCampNumberGenerator() {
        Optional<Camp> optionalPreviousCamp =
                Optional.ofNullable(campRepository.findTopByOrderByCreatedAtDesc());

        return optionalPreviousCamp
                .map(Camp::getUniqueCampNumber)
                .map(num -> num + 1)
                .orElse(1L);
    }


    @Override
    public Page<Camp> getPaginatedCamps(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, sortBy);
        return campRepository.findAll(pageable);
    }



    public Page<Camp> getCampsByStatus(LocalCampStatus campStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "campStartDate");
        return customCampRepository.findCampsByStatus(campStatus, pageable);
    }

    @Override
    public CampTestMappingResource toModel(CampTestMapping entity) {
        CampTestMappingResource resource = new CampTestMappingResource();

       // resource.setId(entity.getId());
        resource.setCampId(entity.getCampId());
        resource.setTestId(entity.getTestId());
        resource.setTestName(entity.getTestName());
        resource.setSampleType(entity.getSampleType());
        resource.setSpecimen(entity.getSpecimen());
        resource.setNote(entity.getNote());
        resource.setSampleTubeType(entity.getSampleTubeType());
        resource.setTestMappedLabs(entity.getTestMappedLabs());

        // optionally add HATEOAS self-link
        // resource.add(linkTo(methodOn(CampTestMappingController.class).getById(entity.getId())).withSelfRel());

        return resource;
    }



    private CampTestMapping checkMapping(CampTestMappingDTO dto) {
        List<CampTestPrice> filteredTestMappedLabs = Optional.ofNullable(dto.getTestMappedLabs())
                .orElse(Collections.emptyList())
                .stream()
                .filter(lab -> {
                    Integer campPrice = lab.getCampPrice();
                    Boolean isComplementary = lab.getIsComplementary();
                    return !(campPrice != null && campPrice == 0 && Boolean.FALSE.equals(isComplementary));
                })
                .collect(Collectors.toList());

        return campTestMappingRepository
                .findByCampIdAndTestId(dto.getCampId(), dto.getTestId())
                .map(existing -> {
                    existing.setTestMappedLabs(filteredTestMappedLabs);
                    return existing;
                })
                .orElseGet(() -> {
                    CampTestMapping newMapping = new CampTestMapping();
                    newMapping.setCampId(dto.getCampId());
                    newMapping.setTestId(dto.getTestId());
                    newMapping.setTestName(dto.getTestName());
                    newMapping.setSampleType(dto.getSampleType());
                    newMapping.setSampleTubeType(dto.getSampleTubeType());
                    newMapping.setNote(dto.getNote());
                    newMapping.setSpecimen(dto.getSpecimen());
                    newMapping.setTestMappedLabs(filteredTestMappedLabs);
                    return newMapping;
                });
    }





    @Override
    public List<CampTestMappingResource> getCampMappedTests(String campId) {
        List<CampTestMapping> mappings = campTestMappingRepository.findAllByCampId(campId);
        return mappings.stream()
                .map(campTestMappingResourceAssembler::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public List<CampResource> searchCamp(SearchCampDTO searchCampDTO) {
        return campResourceAssembler.toCollectionModel(
                customCampRepository.searchCamps(searchCampDTO)
        ).getContent().stream().toList();
    }


    @Override
    public CampResource getCampDetailsById(String campId) {
        return campResourceAssembler.toModel(
                campRepository.findById((campId))
                        .orElseThrow(() -> new CampNotFoundException("Camp does not exist."))
        );
    }


    @Override
    public CampResource updateCampDetails(String campId, UpdateCampDTO updateCampDTO) {
        Camp camp = campRepository.findById((campId))
                .orElseThrow(() -> new CampNotFoundException("Camp does not exist."));

        // Map the DTO to the existing camp entity
        modelMapper.map(updateCampDTO, camp);

        // Save the updated entity
        Camp updatedCamp = campRepository.save(camp);

        // Convert to resource using toModel() instead of toResource()
        return campResourceAssembler.toModel(updatedCamp);
    }


    @Override
    public CampTestMappingResource updateCampMappedTestDetails(String campTestMappingId, UpdateCampTestMappingDTO updateDTO) {
        Long id = Long.valueOf(campTestMappingId); // convert String to Long

        CampTestMapping campTestMapping = campTestMappingRepository.findById(id)
                .orElseThrow(() -> new CampTestMappingNotFoundException("Test not found"));

        // Update fields only if new values are provided
        if (updateDTO.getTestName() != null) {
            campTestMapping.setTestName(updateDTO.getTestName());
        }

        if (updateDTO.getSampleType() != null) {
            campTestMapping.setSampleType(updateDTO.getSampleType());
        }

        if (updateDTO.getTestMappedLabs() != null) {
            campTestMapping.setTestMappedLabs(updateDTO.getTestMappedLabs());
        }

        // Use toModel() instead of toResource() with newer Spring HATEOAS
        return campTestMappingResourceAssembler.toModel(campTestMappingRepository.save(campTestMapping));
    }

    @Override
    public List<CampTestMappingResource> getPreviousCampTestMappings() {

        CampTestMapping latestMapping = campTestMappingRepository.findTopByOrderByCreatedAtDesc();

        if (latestMapping == null) {
            return Collections.emptyList();
        }

        Long campId = Long.valueOf(latestMapping.getCampId()); // convert if stored as String

        Camp camp = campRepository.findById(campId)
                .orElseThrow(() -> new CampNotFoundException("Camp not found."));

        // Convert each CampTestMapping to a resource
        List<CampTestMapping> mappings = campTestMappingRepository.findAllByCampId(camp.getId().toString());
        return mappings.stream()
                .map(campTestMappingResourceAssembler::toModel) // use toModel()
                .collect(Collectors.toList());
    }



    @Override
    public void addUserToCamp(MappedUserIntoTheCampDTO mappedUserIntoTheCampDTO) {
        Optional<Camp> campOptional = campRepository.findById(mappedUserIntoTheCampDTO.getCampId());

        if (!campOptional.isPresent()) {
            throw new IllegalArgumentException("Camp not found for id: " + mappedUserIntoTheCampDTO.getCampId());
        }

        Camp camp = campOptional.get();

        List<MappedUserIntoTheCamp> mappedList = camp.getMappedUserIntoTheCampList();
        if (mappedList == null) {
            mappedList = new ArrayList<>();
            camp.setMappedUserIntoTheCampList(mappedList);
        }

        boolean alreadyMapped = mappedList.stream()
                .anyMatch(existingUser -> existingUser.getUserId().equals(mappedUserIntoTheCampDTO.getUserId()));

        if (!alreadyMapped) {
            MappedUserIntoTheCamp mappedUserIntoTheCamp = new MappedUserIntoTheCamp();
            mappedUserIntoTheCamp.setUserId(mappedUserIntoTheCampDTO.getUserId());
            mappedUserIntoTheCamp.setDesignation(mappedUserIntoTheCampDTO.getDesignation());
            mappedUserIntoTheCamp.setDepartment(mappedUserIntoTheCampDTO.getDepartment());
            mappedList.add(mappedUserIntoTheCamp);
            campRepository.save(camp);
        }
    }

    @Override
    public void unmapUserFromCamp(String campId, String userId) {
        Optional<Camp> campOptional = campRepository.findById(campId);

        if (!campOptional.isPresent()) {
            throw new IllegalArgumentException("Camp not found for id: " + campId);
        }

        Camp camp = campOptional.get();
        List<MappedUserIntoTheCamp> mappedList = camp.getMappedUserIntoTheCampList();

        if (mappedList == null || mappedList.isEmpty()) {
            throw new IllegalStateException("No users are currently mapped to this camp.");
        }

        boolean removed = mappedList.removeIf(user -> userId.equals(user.getUserId()));

        if (!removed) {
            throw new IllegalArgumentException("User with id " + userId + " is not mapped to this camp.");
        }

        campRepository.save(camp);
    }


    public Page<Camp> getCampsMappedToUser(String userId, LocalCampStatus campStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "campStartDate");
        return customCampRepository.findCampsByStatus(campStatus, pageable);
    }


    @Override
    public List<CampUserResource> getCampUserResource(String campId, AppRole designation) {
        Camp camp = campRepository.findById(campId)
                .orElseThrow(() -> new CampNotFoundException("This camp does not exist."));

        List<MappedUserIntoTheCamp> mappedUsers = camp.getMappedUserIntoTheCampList();

        if (mappedUsers == null) {
            return Collections.emptyList();
        }

        return mappedUsers.stream()
                .filter(mappedUser -> designation.equals(mappedUser.getDesignation()))
                .map(mappedUser -> {
                    Optional<AppUser> optionalAppUser = appUserRepository.findById(mappedUser.getUserId());
                    if (!optionalAppUser.isPresent()) {
                        return null; // Skip this entry
                    }
                    AppUser appUser = optionalAppUser.get();
                    CampUserResource campUserResource = new CampUserResource();
                    campUserResource.setUserId(String.valueOf(appUser.getId()));
                    campUserResource.setDepartment(appUser.getLabDepartment());
                    campUserResource.setMobile(appUser.getMobileNumber());
                    campUserResource.setDesignation(mappedUser.getDesignation());
                    campUserResource.setName(String.format("%s %s", appUser.getFirstName(), appUser.getLastName()));
                    return campUserResource;
                })
                .filter(Objects::nonNull) // Remove null entries
                .collect(Collectors.toList());
    }

    @Override
    public List<CampResource> searchCampWithRespectToUser(SearchCampWRTCampUserDTO searchCampWRTCampUserDTO) {
        // Fetch camps by user and filters
        List<Camp> campList = customCampRepository
                .findCampsByUserIdAndStatusAndSearch(
                        searchCampWRTCampUserDTO.getUserId(),
                        searchCampWRTCampUserDTO.getCampStatus(),
                        searchCampWRTCampUserDTO.getSearchedPhrase()
                );

        // Handle null case safely
        if (campList == null || campList.isEmpty()) {
            return Collections.emptyList();
        }

        // Convert Camp entities → CampResource list using assembler
        return campResourceAssembler.toCollectionModel(campList)
                .getContent() // Extract the list from CollectionModel
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<CampTestMappingResource> mapCampToTest(List<CampTestMappingDTO> campTestMappingDTOList) {
        List<CampTestMapping> campTestMappings = campTestMappingDTOList.stream()
                .map(this::checkMapping)
                .collect(Collectors.toList());

        List<CampTestMapping> savedMappings = campTestMappingRepository.saveAll(campTestMappings);

        return campTestMappingResourceAssembler.toModelList(savedMappings);
    }



    @Override
    public void unmapCampTestMapping(UnmapLabSpecificTestFromCampDTO dto) {

        // Fetch mapping, or throw exception if not found
        CampTestMapping campTestMapping = campTestMappingRepository
                .findById(dto.getCampTestMappingId())
                .orElseThrow(() -> new CampTestMappingNotFoundException("Mapping does not exist."));

        // If no test-lab mappings, nothing to unmap
        List<CampTestPrice> testMappedLabs = campTestMapping.getTestMappedLabs();
        if (testMappedLabs == null || testMappedLabs.isEmpty()) {
            return; // Nothing to remove
        }

        // Remove labs matching the given labId & testId
        boolean removed = testMappedLabs.removeIf(testPrice ->
                Objects.equals(dto.getLabId(), getLabId(testPrice)) &&
                        Objects.equals(dto.getTestId(), campTestMapping.getTestId())
        );

        // If no matching mapping found, just return
        if (!removed) {
            return;
        }

        // Delete mapping if no labs left, otherwise update
        if (testMappedLabs.isEmpty()) {
            campTestMappingRepository.delete(campTestMapping);
        } else {
            campTestMappingRepository.save(campTestMapping);
        }
    }

    /**
     * Extracts labId from CampTestPrice safely.
     * Update this depending on your CampTestPrice fields.
     */
    private String getLabId(CampTestPrice testPrice) {


        return null; // fallback if your embeddable doesn't have a labId field
    }





    @Override
    @Transactional
    public void unmapLabSpecificTestFromCamp(String labId, String campId) {
        // Step 1: Fetch all mappings for the given camp
        List<CampTestMapping> mappings = campTestMappingRepository.findAllByCampId(campId);

        for (CampTestMapping mapping : mappings) {
            List<CampTestPrice> testMappedLabs = mapping.getTestMappedLabs();

            if (testMappedLabs == null || testMappedLabs.isEmpty()) {
                continue;
            }

            // Step 2: Remove lab-specific entries from the list
            testMappedLabs.removeIf(price -> Objects.equals(price.getLabId(), labId));

            // Step 3: If no labs remain for that test, delete the mapping
            if (testMappedLabs.isEmpty()) {
                campTestMappingRepository.delete(mapping);
            } else {
                campTestMappingRepository.save(mapping); // persist updated test-lab list
            }
        }
    }

    @Override
    public Optional<PatientCampRegistration> getPatientCampRegistrationById(Long id) {
        return Optional.empty();
    }


}

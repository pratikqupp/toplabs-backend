package com.TopLabBazaar2909.TLBnew2909.serviceImpl;


import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.PatientCampRegistrationService;
import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.embedded.AssignedCampUser;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientAssignedTest;
//import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientVital;
import com.TopLabBazaar2909.TLBnew2909.exception.PatientCampRegistrationNotFoundException;
import com.TopLabBazaar2909.TLBnew2909.payment.PaymentCountProjection;
import com.TopLabBazaar2909.TLBnew2909.repository.PatientCampRegistrationRepository;
import com.TopLabBazaar2909.TLBnew2909.resource.CampPatientAdditionSummary;
import com.TopLabBazaar2909.TLBnew2909.resource.DetailedPatientCampRegistrationResource;
import com.TopLabBazaar2909.TLBnew2909.resource.PatientRegistrationResourceInCamp;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.DetailedPatientCampRegistrationResourceAssembler;
import com.TopLabBazaar2909.TLBnew2909.resourceAssembler.PatientRegistrationInCampResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientCampRegistrationServiceImpl implements PatientCampRegistrationService {

    @Autowired
    private PatientCampRegistrationRepository patientCampRegistrationRepository;

    @Autowired
    private PatientRegistrationInCampResourceAssembler patientRegistrationInCampResourceAssembler;

    @Autowired
    private DetailedPatientCampRegistrationResourceAssembler detailedPatientCampRegistrationResourceAssembler;

//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Override
    public PatientRegistrationResourceInCamp registerPatient(PatientCampRegistrationDTO dto) {

        // 1. Check for existing registration
        checkForPatientCampRegistration(dto.getFamilyMemberId(), dto.getCampId());

        // 2. Create new PatientCampRegistration from DTO
        PatientCampRegistration registration = new PatientCampRegistration();
        registration.setCampId(dto.getCampId());
        registration.setFamilyMemberId(dto.getFamilyMemberId());
        registration.setFirstName(dto.getFirstName());
        registration.setLastName(dto.getLastName());
        registration.setMobileNumber(dto.getMobileNumber());
        registration.setGender(dto.getGender());
        registration.setPatientAddress(dto.getPatientAddress());
        registration.setDob(dto.getDob());
        registration.setAge(dto.getAge());

        //3. Manual ID generation (e.g., PCR001, PCR002, PCR003, ...)
        String lastId = patientCampRegistrationRepository.findTopByOrderByIdDesc()
                .map(PatientCampRegistration::getId)
                .orElse("PCR000");

        int nextNumber = Integer.parseInt(lastId.substring(3)) + 1;
        String newId = String.format("PCR%03d", nextNumber);
        registration.setId(newId);

        System.out.println("Generated Manual ID for Registration: " + newId);

        // 4. Save registration
        registration = patientCampRegistrationRepository.save(registration);

        // 5. Assign user if provided
        assignUserIfNotExists(registration, (AssignedCampUserDTO) dto.getAssignedCampUsers());

        // 6. Save again only if modified
        registration = patientCampRegistrationRepository.save(registration);

        // 7. Convert to response resource
        return patientRegistrationInCampResourceAssembler.toModel(registration);
    }


    private void checkForPatientCampRegistration(String familyMemberId, String campId) {
        patientCampRegistrationRepository.findByFamilyMemberIdAndCampId(familyMemberId,campId)
                .ifPresent(registration -> {
                    throw new PatientCampRegistrationNotFoundException("Registration already exists for this family member.");
                });
    }

    @Override
    public List<DetailedPatientCampRegistrationResource> getAllRegisteredPatientOfCamp(String campId) {
        List<PatientCampRegistration> patientCampRegistrationList =
                patientCampRegistrationRepository.findByCampIdOrderByCreatedAtDesc(campId);

        // Convert each entity to resource using toModel()
        return patientCampRegistrationList.stream()
                .map(detailedPatientCampRegistrationResourceAssembler::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public DetailedPatientCampRegistrationResource assignTestToPatient(AssignTestPayloadDTO assignTestPayloadDTO) {
        // 1. Find the patient registration by ID or throw exception if not found
        PatientCampRegistration patientCampRegistration = patientCampRegistrationRepository.findById(
                        assignTestPayloadDTO.getPatientCampRegistrationId())
                .orElseThrow(() -> new PatientCampRegistrationNotFoundException("Patient camp registration not found."));

        // 2. Assign user if not already assigned
        assignUserIfNotExists(patientCampRegistration, assignTestPayloadDTO.getAssignedCampUser());

        // 3. Assign unique tests to the patient
        assignUniqueTests(patientCampRegistration, assignTestPayloadDTO.getPatientAssignedTestLists());

        // 4. Save the updated registration entity
        PatientCampRegistration updated = patientCampRegistrationRepository.save(patientCampRegistration);

        // 5. Convert entity to resource using new HATEOAS method
        return detailedPatientCampRegistrationResourceAssembler.toModel(updated);
    }


    @Override
    public DetailedPatientCampRegistrationResource updatePaymentStatus(UpdatePaymentDetailsDTO updatePaymentDetailsDTO) {

        // 1. Find the patient registration or throw an exception if not found
        PatientCampRegistration patientCampRegistration = patientCampRegistrationRepository.findById(
                        updatePaymentDetailsDTO.getPatientCampRegistrationId())
                .orElseThrow(() ->
                        new PatientCampRegistrationNotFoundException("Patient camp registration not found.")
                );

        // 2. Update payment-related fields
        patientCampRegistration.setPaid(updatePaymentDetailsDTO.isPaid());
        patientCampRegistration.setAmountPaid(updatePaymentDetailsDTO.getAmountPaid());
        patientCampRegistration.setPaymentMode(updatePaymentDetailsDTO.getPaymentMode());

        // 3. Assign user if not already assigned
        assignUserIfNotExists(patientCampRegistration, updatePaymentDetailsDTO.getAssignedCampUser());

        // 4. Save the updated record
        PatientCampRegistration updated = patientCampRegistrationRepository.save(patientCampRegistration);

        // 5. Convert the updated entity to a response resource using toModel()
        return detailedPatientCampRegistrationResourceAssembler.toModel(updated);
    }

    private void assignUserIfNotExists(PatientCampRegistration registration, AssignedCampUserDTO newUser) {
        if (newUser == null) return;

        List<AssignedCampUser> existingUsers = Optional.ofNullable(registration.getAssignedCampUsers())
                .orElse(new ArrayList<>());

        Optional<AssignedCampUser> existingUserOpt = existingUsers.stream()
                .filter(u -> u.getUserId().equals(newUser.getUserId()))
                .findFirst();

        if (existingUserOpt.isPresent()) {
            // User exists: check if action already recorded
            AssignedCampUser existingUser = existingUserOpt.get();
            List<String> actions = Optional.ofNullable(existingUser.getPerformedActions())
                    .orElse(new ArrayList<>());

            if (!actions.contains(newUser.getAction())) {
                actions.add(newUser.getAction());
                existingUser.setPerformedActions(actions);
            }

        } else {
            // New user: add everything
            AssignedCampUser user = new AssignedCampUser();
            user.setUserId(newUser.getUserId());
            user.setUserName(newUser.getUserName());
            user.setDepartment(newUser.getDepartment());
            user.setDesignation(newUser.getDesignation());
            user.setPerformedActions(new ArrayList<>(Collections.singletonList(newUser.getAction())));

            existingUsers.add(user);
        }

        registration.setAssignedCampUsers(existingUsers);
    }



    private void assignUniqueTests(PatientCampRegistration registration, List<PatientAssignedTest> newTests) {
        if (newTests == null || newTests.isEmpty()) return;

        List<PatientAssignedTest> existingTests = Optional.ofNullable(registration.getPatientAssignedTestLists())
                .orElse(new ArrayList<>());

        Set<String> existingTestIds = existingTests.stream()
                .map(PatientAssignedTest::getTestId)
                .collect(Collectors.toSet());

        List<PatientAssignedTest> uniqueNewTests = newTests.stream()
                .filter(test -> !existingTestIds.contains(test.getTestId()))
                .collect(Collectors.toList());

        existingTests.addAll(uniqueNewTests);
        registration.setPatientAssignedTestLists(existingTests);
    }

    @Override
    public Long getSampleTestCountByCampId(String campId) {
        // Fetch aggregated test counts from PostgreSQL via JPA repository
        return patientCampRegistrationRepository.getSampleTestCountByCampId(campId);
    }



    @Override
    public DetailedPatientCampRegistrationResource getPatientRegisterCampDetails(String patientCampRegistrationId) {
        PatientCampRegistration patientCampRegistration = patientCampRegistrationRepository.findById(
                patientCampRegistrationId).orElseThrow(
                () -> new PatientCampRegistrationNotFoundException("Patient camp registration not found.")
        );

        return detailedPatientCampRegistrationResourceAssembler.toModel(patientCampRegistration);
    }


    @Override
    public DetailedPatientCampRegistrationResource addVital(AddVitalDTO addVitalDTO) {
        PatientCampRegistration patientCampRegistration = patientCampRegistrationRepository.findById(
                addVitalDTO.getPatientCampRegistrationId()).orElseThrow(
                () -> new PatientCampRegistrationNotFoundException("Patient camp registration not found.")
        );

        // Set the vital info
        patientCampRegistration.setPatientVital(addVitalDTO.getPatientVital());

        assignUserIfNotExists(patientCampRegistration, addVitalDTO.getAssignedCampUser());

        // Save the updated registration
        patientCampRegistrationRepository.save(patientCampRegistration);

        // Return the detailed resource
        return detailedPatientCampRegistrationResourceAssembler.toModel(patientCampRegistration);
    }



    @Override
    public DetailedPatientCampRegistrationResource removeAssignedTestToPatient(RemoveAssignedTestFromPatientDTO removeAssignedTestFromPatientDTO) {
        // 1. Fetch patient record or throw exception
        PatientCampRegistration patientCampRegistration = patientCampRegistrationRepository.findById(
                removeAssignedTestFromPatientDTO.getPatientCampRegistrationId()).orElseThrow(
                () -> new PatientCampRegistrationNotFoundException("Patient camp registration not found.")
        );

        // 2. Remove tests where campTestMappingId is in the list
        if (patientCampRegistration.getPatientAssignedTestLists() != null) {
            List<PatientAssignedTest> updatedTests = patientCampRegistration.getPatientAssignedTestLists()
                    .stream()
                    .filter(test -> !removeAssignedTestFromPatientDTO.getCampTestMappingIds().contains(test.getCampTestMappingId()))
                    .collect(Collectors.toList());

            patientCampRegistration.setPatientAssignedTestLists(updatedTests);
        }

        // 3. Assign user if not already present
        assignUserIfNotExists(patientCampRegistration, removeAssignedTestFromPatientDTO.getAssignedCampUser());

        // 4. Save updated entity
        PatientCampRegistration updated = patientCampRegistrationRepository.save(patientCampRegistration);

        // 5. Return updated resource
        return detailedPatientCampRegistrationResourceAssembler.toModel(updated);
    }

    @Override
    public CampPatientAdditionSummary getCampWisePaymentSummary(String campId) {
        List<PaymentCountProjection> results = patientCampRegistrationRepository.getPaymentSummaryByCamp(campId);

        CampPatientAdditionSummary summary = new CampPatientAdditionSummary();
        for (PaymentCountProjection proj : results) {
            if (Boolean.TRUE.equals(proj.getIsPaid())) {
                summary.setPaid(proj.getCount());
            } else {
                summary.setUnpaid(proj.getCount());
            }
        }

        return summary;
    }







}

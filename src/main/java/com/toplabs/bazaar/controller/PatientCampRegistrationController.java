package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.PatientCampRegistrationService;
import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.repository.PatientCampRegistrationCustomRepository;
import com.toplabs.bazaar.resource.CampPatientAdditionSummary;
import com.toplabs.bazaar.resource.DetailedPatientCampRegistrationResource;
import com.toplabs.bazaar.resource.PatientRegistrationResourceInCamp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/patient-camp-registrations")
@Tag(name = "Patient Camp Registration", description = "Operations related to patient camp registration")
public class PatientCampRegistrationController {

    @Autowired
    private PatientCampRegistrationService patientCampRegistrationService;

    @Autowired
    private PatientCampRegistrationCustomRepository patientCampRegistrationCustomRepository;

   //@PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Register patient into a camp",
            description = "Accessible to all authenticated users"
    )
    public PatientRegistrationResourceInCamp registerPatient(@RequestBody PatientCampRegistrationDTO patientCampRegistrationDTO) {
        System.out.println("Incoming gender: " + patientCampRegistrationDTO.getGender());
        return patientCampRegistrationService.registerPatient(patientCampRegistrationDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/{patientCampRegistrationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get patient registration details",
            description = "Accessible to all authenticated users"
    )
    public DetailedPatientCampRegistrationResource getPatientRegisterCampDetails(
            @PathVariable("patientCampRegistrationId") String patientCampRegistrationId) {
        return patientCampRegistrationService.getPatientRegisterCampDetails(patientCampRegistrationId);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/all/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all registered patients of a camp",
            description = "Accessible to all authenticated users"
    )
    public List<DetailedPatientCampRegistrationResource> detailedPatientCampRegistrationResource(@PathVariable("campId") String campId) {
        return patientCampRegistrationService.getAllRegisteredPatientOfCamp(campId);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/assign-tests", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Assign tests to a patient",
            description = "Accessible to all authenticated users"
    )
    public DetailedPatientCampRegistrationResource assignTestToPatient(@RequestBody AssignTestPayloadDTO assignTestPayloadDTO) {
        return patientCampRegistrationService.assignTestToPatient(assignTestPayloadDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/update-payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update payment status of a patient",
            description = "Accessible to all authenticated users"
    )
    public DetailedPatientCampRegistrationResource updatePayment(@RequestBody UpdatePaymentDetailsDTO updatePaymentDetailsDTO) {
        return patientCampRegistrationService.updatePaymentStatus(updatePaymentDetailsDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/lab/sample/test/count/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get sample count with respect to test",
            description = "Accessible to all authenticated users"
    )
    public Long getLabSpecificTestCount(@PathVariable("campId") String campId) {
        return patientCampRegistrationService.getSampleTestCountByCampId(campId);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/add/vital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Add vital details to patient registration",
            description = "Accessible to all authenticated users"
    )
    public DetailedPatientCampRegistrationResource addVital(@RequestBody AddVitalDTO addVitalDTO) {
        return patientCampRegistrationService.addVital(addVitalDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/get/payment/summary/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get payment summary based on payment method type",
            description = "Accessible to all authenticated users"
    )
    public List<PaymentSummaryDTO> getPaymentSummary(@PathVariable("campId") String campId) {
        return patientCampRegistrationCustomRepository.getPaymentSummaryByCampId(campId);
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/remove-tests", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Remove assigned tests from a patient",
            description = "Accessible to all authenticated users"
    )
    public DetailedPatientCampRegistrationResource removeAssignedTestToPatient(@RequestBody RemoveAssignedTestFromPatientDTO removeAssignedTestFromPatientDTO) {
        return patientCampRegistrationService.removeAssignedTestToPatient(removeAssignedTestFromPatientDTO);
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/count/{campId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get registered patient count based on payment status",
            description = "Accessible to all authenticated users"
    )
    public CampPatientAdditionSummary getPatientCountBasedOnItsPaymentStatus(@PathVariable("campId") String campId) {
        return patientCampRegistrationService.getCampWisePaymentSummary(campId);
    }
}

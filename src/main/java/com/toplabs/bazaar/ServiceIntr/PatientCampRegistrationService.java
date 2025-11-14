package com.toplabs.bazaar.ServiceIntr;



import com.toplabs.bazaar.dto.*;
import com.toplabs.bazaar.resource.CampPatientAdditionSummary;
import com.toplabs.bazaar.resource.DetailedPatientCampRegistrationResource;
import com.toplabs.bazaar.resource.PatientRegistrationResourceInCamp;

import java.util.List;

public interface PatientCampRegistrationService {


    PatientRegistrationResourceInCamp registerPatient(PatientCampRegistrationDTO dto);

    List<DetailedPatientCampRegistrationResource> getAllRegisteredPatientOfCamp(String campId);

    DetailedPatientCampRegistrationResource assignTestToPatient(AssignTestPayloadDTO assignTestPayloadDTO);

    DetailedPatientCampRegistrationResource updatePaymentStatus(UpdatePaymentDetailsDTO updatePaymentDetailsDTO);

    Long getSampleTestCountByCampId(String campId);

    DetailedPatientCampRegistrationResource getPatientRegisterCampDetails(String patientCampRegistrationId);

    DetailedPatientCampRegistrationResource addVital(AddVitalDTO addVitalDTO);

    DetailedPatientCampRegistrationResource removeAssignedTestToPatient(RemoveAssignedTestFromPatientDTO removeAssignedTestFromPatientDTO);

    CampPatientAdditionSummary getCampWisePaymentSummary(String campId);
}

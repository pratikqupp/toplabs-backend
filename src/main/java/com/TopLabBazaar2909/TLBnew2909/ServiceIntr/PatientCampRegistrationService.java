package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;



import com.TopLabBazaar2909.TLBnew2909.dto.*;
import com.TopLabBazaar2909.TLBnew2909.resource.CampPatientAdditionSummary;
import com.TopLabBazaar2909.TLBnew2909.resource.DetailedPatientCampRegistrationResource;
import com.TopLabBazaar2909.TLBnew2909.resource.PatientRegistrationResourceInCamp;

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

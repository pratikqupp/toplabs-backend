package com.toplabs.bazaar.dto;


import com.toplabs.bazaar.embedded.PatientVital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVitalDTO {
    private String patientCampRegistrationId;
  //  private List<PatientVital> patientVital;

    private PatientVital patientVital;
    private AssignedCampUserDTO assignedCampUser;
}

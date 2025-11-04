package com.TopLabBazaar2909.TLBnew2909.dto;


import com.TopLabBazaar2909.TLBnew2909.embedded.PatientVital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVitalDTO {
    private String patientCampRegistrationId;
  //  private List<PatientVital> patientVital;

    private PatientVital patientVital;
    private AssignedCampUserDTO assignedCampUser;
}

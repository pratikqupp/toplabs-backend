package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.PatientAssignedTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTestPayloadDTO {
    private String patientCampRegistrationId;

    private List<PatientAssignedTest> patientAssignedTestLists;

    private AssignedCampUserDTO assignedCampUser;
}

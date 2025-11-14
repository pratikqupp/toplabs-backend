package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.embedded.PatientAssignedTest;
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

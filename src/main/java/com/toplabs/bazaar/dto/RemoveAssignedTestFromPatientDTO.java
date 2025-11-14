package com.toplabs.bazaar.dto;

//import com.qup.microservices.provider.laboratory.embadded.AssignedCampUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveAssignedTestFromPatientDTO {
    private String patientCampRegistrationId;
    private List<String> campTestMappingIds;
    private AssignedCampUserDTO assignedCampUser;
}

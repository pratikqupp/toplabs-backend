package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.Enum.Gender;
import com.toplabs.bazaar.embedded.AssignedCampUser;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class PatientRegistrationResourceInCamp extends RepresentationModel<PatientRegistrationResourceInCamp> {
    private String patientCampRegistrationId;

    private String campId;

    private String familyMemberId;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private Gender gender;
    private String patientAddress;
    private Integer age;

    private List<AssignedCampUser> assignedCampUsers;

}

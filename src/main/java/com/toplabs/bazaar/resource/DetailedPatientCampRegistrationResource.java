package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.Enum.Gender;
import com.toplabs.bazaar.Enum.PaymentMode;
import com.toplabs.bazaar.embedded.AssignedCampUser;
import com.toplabs.bazaar.embedded.PatientAssignedTest;
import com.toplabs.bazaar.embedded.PatientVital;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class DetailedPatientCampRegistrationResource extends RepresentationModel<DetailedPatientCampRegistrationResource> {
    private String patientCampRegistrationId;

    private String campId;

    private String familyMemberId;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private Gender gender;
    private Integer age;

    private boolean isPaid;
    private PaymentMode paymentMode;
    private Double amountPaid;

    private List<PatientAssignedTest> patientAssignedTestLists;

    private List<AssignedCampUser> assignedCampUsers;

    private PatientVital patientVital;

    private String patientAddress;

}

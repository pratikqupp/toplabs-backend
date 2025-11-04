package com.TopLabBazaar2909.TLBnew2909.resource;


import com.TopLabBazaar2909.TLBnew2909.Enum.Gender;
import com.TopLabBazaar2909.TLBnew2909.Enum.PaymentMode;
import com.TopLabBazaar2909.TLBnew2909.embedded.AssignedCampUser;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientAssignedTest;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientVital;
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

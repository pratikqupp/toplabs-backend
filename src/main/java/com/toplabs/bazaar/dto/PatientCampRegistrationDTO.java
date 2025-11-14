package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.Gender;
import com.toplabs.bazaar.Enum.PaymentMode;
import com.toplabs.bazaar.embedded.AssignedCampUser;
import com.toplabs.bazaar.embedded.PatientAssignedTest;
import com.toplabs.bazaar.embedded.PatientVital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientCampRegistrationDTO {

    private String id;

    private String campId;

    private String familyMemberId;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private Gender gender;
    private String patientAddress;
    private Integer dob;
    private Integer age;

    private boolean isPaid;
    private PaymentMode paymentMode;
    private Double amountPaid;

    private List<PatientAssignedTest> patientAssignedTestLists;

    private List<AssignedCampUser> assignedCampUsers;

    private List<PatientVital> patientVital;

    private LocalDateTime createdAt;
}

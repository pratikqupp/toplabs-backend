package com.toplabs.bazaar.entity;

import com.toplabs.bazaar.Enum.Gender;
import com.toplabs.bazaar.Enum.PaymentMode;
import com.toplabs.bazaar.embedded.AssignedCampUser;
import com.toplabs.bazaar.embedded.PatientAssignedTest;
import com.toplabs.bazaar.embedded.PatientVital;
import com.toplabs.bazaar.model.BaseAuditableDocument;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PatientCampRegistration extends BaseAuditableDocument {

    @Id
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
    @Enumerated(EnumType.ORDINAL)
    private PaymentMode paymentMode;


    private Double amountPaid;

    @ElementCollection
    private List<PatientAssignedTest> patientAssignedTestLists;

//    @ElementCollection
//    private List<AssignedCampUser> assignedUsers;
    @ElementCollection
   private List<AssignedCampUser> assignedCampUsers;

    @Embedded
    private PatientVital patientVital;
    private LocalDateTime createdAt;

}

package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.Enum.Gender;
import com.TopLabBazaar2909.TLBnew2909.Enum.PaymentMode;
import com.TopLabBazaar2909.TLBnew2909.embedded.AssignedCampUser;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientAssignedTest;
import com.TopLabBazaar2909.TLBnew2909.embedded.PatientVital;
import com.TopLabBazaar2909.TLBnew2909.model.BaseAuditableDocument;
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

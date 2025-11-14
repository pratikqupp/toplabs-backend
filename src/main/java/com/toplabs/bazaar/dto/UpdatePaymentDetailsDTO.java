package com.toplabs.bazaar.dto;

//import com.qup.microservices.booking.patient.model.enums.PaymentMode;
import com.toplabs.bazaar.Enum.PaymentMode;
//import com.qup.microservices.provider.laboratory.embadded.AssignedCampUser;
//import com.qup.microservices.provider.laboratory.embadded.PatientAssignedTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentDetailsDTO {
    private String patientCampRegistrationId;
    private boolean isPaid;
    private PaymentMode paymentMode;
    private Double amountPaid;
    private AssignedCampUserDTO assignedCampUser;
}


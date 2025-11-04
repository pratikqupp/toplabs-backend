package com.TopLabBazaar2909.TLBnew2909.dto;



import com.TopLabBazaar2909.TLBnew2909.Enum.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSummaryDTO {
    private PaymentMode paymentMode;
    private Double totalAmount;
}

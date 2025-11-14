package com.toplabs.bazaar.dto;



import com.toplabs.bazaar.Enum.PaymentMode;
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

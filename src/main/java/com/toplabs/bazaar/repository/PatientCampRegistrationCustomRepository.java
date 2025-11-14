package com.toplabs.bazaar.repository;


import com.toplabs.bazaar.dto.PaymentSummaryDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PatientCampRegistrationCustomRepository   {
    List<PaymentSummaryDTO> getPaymentSummaryByCampId(String campId);
}

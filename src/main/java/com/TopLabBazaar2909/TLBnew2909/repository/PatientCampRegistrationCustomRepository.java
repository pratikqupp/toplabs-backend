package com.TopLabBazaar2909.TLBnew2909.repository;


import com.TopLabBazaar2909.TLBnew2909.dto.PaymentSummaryDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PatientCampRegistrationCustomRepository   {
    List<PaymentSummaryDTO> getPaymentSummaryByCampId(String campId);
}

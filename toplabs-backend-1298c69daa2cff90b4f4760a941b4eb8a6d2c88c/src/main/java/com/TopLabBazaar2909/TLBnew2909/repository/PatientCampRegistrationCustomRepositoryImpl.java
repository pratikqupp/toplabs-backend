package com.TopLabBazaar2909.TLBnew2909.repository;


import com.TopLabBazaar2909.TLBnew2909.dto.PaymentSummaryDTO;
import com.TopLabBazaar2909.TLBnew2909.repository.PatientCampRegistrationCustomRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PatientCampRegistrationCustomRepositoryImpl implements PatientCampRegistrationCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentSummaryDTO> getPaymentSummaryByCampId(String campId) {
        String query = """
            SELECT new com.qup.microservices.provider.laboratory.dto.PaymentSummaryDTO(
                p.paymentMode,
                SUM(p.amountPaid)
            )
            FROM PatientCampRegistration p
            WHERE p.campId = :campId
              AND p.isPaid = true
            GROUP BY p.paymentMode
        """;

        return entityManager.createQuery(query, PaymentSummaryDTO.class)
                .setParameter("campId", Long.parseLong(campId))
                .getResultList();
    }
}

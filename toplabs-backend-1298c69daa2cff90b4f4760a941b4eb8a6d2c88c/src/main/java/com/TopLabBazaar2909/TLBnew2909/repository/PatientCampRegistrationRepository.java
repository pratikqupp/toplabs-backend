package com.TopLabBazaar2909.TLBnew2909.repository;

import com.TopLabBazaar2909.TLBnew2909.entity.PatientCampRegistration;
import com.TopLabBazaar2909.TLBnew2909.payment.PaymentCountProjection;
import com.TopLabBazaar2909.TLBnew2909.resource.SampleCountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientCampRegistrationRepository extends JpaRepository<PatientCampRegistration, String> {

    @Query("SELECT p.isPaid AS isPaid, COUNT(p) AS count " +
            "FROM PatientCampRegistration p " +
            "WHERE p.campId = :campId " +
            "GROUP BY p.isPaid")
    List<PaymentCountProjection> getPaymentSummaryByCamp(@Param("campId") String campId);

    // Find all by campId and sort by firstName ascending
    List<PatientCampRegistration> findByCampIdOrderByFirstNameAsc(String campId);

    // Find all by campId and sort by createdAt descending
    List<PatientCampRegistration> findByCampIdOrderByCreatedAtDesc(String campId);

    // Find by primary key (optional)
    Optional<PatientCampRegistration> findById(String id);

    // Find by familyMemberId
    Optional<PatientCampRegistration> findByFamilyMemberId(String familyMemberId);

    // Find by familyMemberId and campId
    Optional<PatientCampRegistration> findByFamilyMemberIdAndCampId(String familyMemberId, String campId);

    @Query("SELECT COUNT(p) FROM PatientCampRegistration p WHERE p.campId = :campId")
    Long getSampleTestCountByCampId(@Param("campId") String campId);
    Optional<PatientCampRegistration> findTopByOrderByIdDesc();

}

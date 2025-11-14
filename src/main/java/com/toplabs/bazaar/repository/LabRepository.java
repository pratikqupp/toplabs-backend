package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

    Optional<Lab> findById(String labId);

    // ✅ JPA equivalent of Mongo query:
    // Assuming Lab has a one-to-many relation: List<TestPrice> testPrices;
    @Query("SELECT DISTINCT l FROM Lab l JOIN l.testPrices tp WHERE tp.testId IN :testIds")
    List<Lab> findLabsWithAnyTestIds(List<String> testIds);
}

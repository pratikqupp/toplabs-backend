package com.TopLabBazaar2909.TLBnew2909.repository;


import com.TopLabBazaar2909.TLBnew2909.entity.TestProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestProfileRepository extends JpaRepository<TestProfile, Long> {

    // Find by ID
    Optional<TestProfile> findById(String id);

    // Find all by labId sorted by name ascending
    List<TestProfile> findByLabIdOrderByNameAsc(String labId);

    // Case-insensitive search by name (JPA syntax)
    @Query("SELECT t FROM TestProfile t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<TestProfile> findByNameContainingIgnoreCase(String phrase);
}

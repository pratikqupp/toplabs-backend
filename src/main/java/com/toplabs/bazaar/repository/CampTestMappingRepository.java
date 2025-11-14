package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.CampTestMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampTestMappingRepository extends JpaRepository<CampTestMapping, Long> {

    // Get all CampTestMapping by Camp ID
    @Query("SELECT ctm FROM CampTestMapping ctm WHERE ctm.campId = :campId")
    List<CampTestMapping> findAllByCampId(@Param("campId") String campId);

    // Find by primary key (already available via JpaRepository#findById)
    Optional<CampTestMapping> findById(String id);

    // Get the latest CampTestMapping by creation time
    CampTestMapping findTopByOrderByCreatedAtDesc();

    // Find CampTestMapping by Camp ID and Test ID
    Optional<CampTestMapping> findByCampIdAndTestId(String campId, String testId);

    // Find all mappings for a specific Test ID
    List<CampTestMapping> findByTestId(String testId);
}

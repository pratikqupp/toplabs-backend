package com.TopLabBazaar2909.TLBnew2909.repository;

import com.TopLabBazaar2909.TLBnew2909.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampRepository extends JpaRepository<Camp, Long> {

    // Get the most recently created Camp
    Camp findTopByOrderByCreatedAtDesc();

    // Find by primary key (non-static)
    Optional<Camp> findById(String campId);

    // Find all Camps where mapped user's ID matches (assuming a relationship)
    @Query("SELECT c FROM Camp c JOIN c.mappedUserIntoTheCampList u WHERE u.userId = :userId")
    List<Camp> findByMappedUserUserId(@Param("userId") String userId);

    // Search by institution name (case-insensitive) and mapped user ID
    @Query("SELECT c FROM Camp c JOIN c.mappedUserIntoTheCampList u " +
            "WHERE LOWER(c.institutionName) LIKE LOWER(CONCAT('%', :searchedPhrase, '%')) " +
            "AND u.userId = :userId")
    List<Camp> searchByInstitutionNameAndMappedUser(@Param("searchedPhrase") String searchedPhrase,
                                                    @Param("userId") String userId);
}

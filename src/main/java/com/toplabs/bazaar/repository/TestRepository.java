package com.toplabs.bazaar.repository;


import com.toplabs.bazaar.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    // Find by ID
    Optional<Test> findById(String testId);

    // Find where name starts with (case-insensitive)
    @Query("SELECT t FROM Test t WHERE LOWER(t.name) LIKE LOWER(CONCAT(:namePrefix, '%'))")
    List<Test> findByNameStartingWithIgnoreCase(String namePrefix);

    // Find where name contains (case-insensitive)
    @Query("SELECT t FROM Test t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Test> findByNameContainingIgnoreCase(String phrase);

    // Find all tests marked as favorite
    List<Test> findByFavoriteTrue();

    // Find all tests with IDs in the provided list
    List<Test> findByIdIn(List<String> ids);}

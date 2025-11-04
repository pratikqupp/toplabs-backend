package com.TopLabBazaar2909.TLBnew2909.repository;


import com.TopLabBazaar2909.TLBnew2909.entity.LabPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<LabPackage, String> {

 // Find by labId and order by name
 List<LabPackage> findByLabIdOrderByNameAsc(String labId);

 // Case-insensitive name search (JPA version of Mongo regex)

 List<LabPackage> findByNameContainingIgnoreCase(String phrase);

 // Find by ID (already included in JpaRepository, but explicit is fine)
 Optional<LabPackage> findById(String packageId);

 // Find by categoryId
 List<LabPackage> findByCategoryId(String categoryId);

 // Find by labId and categoryId
 List<LabPackage> findByLabIdAndCategoryId(String labId, String categoryId);

 Optional<LabPackage> findTopByOrderByIdDesc();

}

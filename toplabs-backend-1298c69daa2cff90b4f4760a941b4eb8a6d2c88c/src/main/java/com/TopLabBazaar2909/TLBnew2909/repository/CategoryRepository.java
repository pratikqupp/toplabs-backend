package com.TopLabBazaar2909.TLBnew2909.repository;

//import com.qup.microservices.provider.laboratory.model.Category;
import com.TopLabBazaar2909.TLBnew2909.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find by ID (inherited from JpaRepository, so not strictly needed)
    Optional<Category> findById(String id);

   void deleteById(String id);

    Optional<Category> findTopByOrderByIdDesc();
}

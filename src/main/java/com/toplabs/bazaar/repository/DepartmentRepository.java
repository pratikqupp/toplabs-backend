package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    // Find by primary key (departmentId)
    Optional<Department> findById(String id);
    // Optional: find by department name
    Optional<Department> findByDepartmentName(String departmentName);
}

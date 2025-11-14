package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.entity.Role22;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role22, Long> {
    // Find role by roleId (unique)
    Optional<Role22> findById(String id);


    // Find all roles by departmentId (one department can have many roles)
    List<Role22> findByDepartmentId(String id);

}

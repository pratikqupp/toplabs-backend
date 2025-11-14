package com.toplabs.bazaar.repository;

import com.toplabs.bazaar.Enum.PositionStatus;
import com.toplabs.bazaar.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    // ✅ Get all positions by Department (String departmentId in Department)

    Optional<Position> findById(Long id);


    // ✅ Get all positions by Role (String roleId in Role22)
    List<Position> findByRole_Id(String roleId);


    // ✅ Get all positions by Department + Role
    List<Position> findByDepartmentIdAndRoleId(String departmentId, String roleId);


    // ✅ Find by unique positionId field (String)

    // ✅ Get positions assigned to a specific User
    List<Position> findByCurrentUser_Id(String id);

    // ✅ Get positions by status (String)
    List<Position> findByStatus(String status);

    List<Position> findByDepartment_Id(String departmentId);

    Position findFirstByRoleIdAndStatus(String roleId, PositionStatus status);
}

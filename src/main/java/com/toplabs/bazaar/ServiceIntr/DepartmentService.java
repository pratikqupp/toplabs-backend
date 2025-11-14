package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO updateDepartment(String departmentId, DepartmentDTO departmentDTO);

    DepartmentDTO deactivateDepartment(String departmentId);

    DepartmentDTO getDepartmentHierarchy(String departmentId);

    long countDepartments();
}

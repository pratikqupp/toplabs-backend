package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO updateDepartment(String departmentId, DepartmentDTO departmentDTO);

    DepartmentDTO deactivateDepartment(String departmentId);

    DepartmentDTO getDepartmentHierarchy(String departmentId);

    long countDepartments();
}

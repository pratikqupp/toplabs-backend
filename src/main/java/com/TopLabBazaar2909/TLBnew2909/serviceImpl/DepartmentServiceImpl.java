package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.DepartmentService;
import com.TopLabBazaar2909.TLBnew2909.dto.DepartmentDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Department;
import com.TopLabBazaar2909.TLBnew2909.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department dept = new Department();

        dept.setId(departmentDTO.getDepartmentId());

        dept.setDepartmentName(departmentDTO.getName());
        dept.setStatus(departmentDTO.getStatus() != null ? departmentDTO.getStatus() : "ACTIVE");

        LocalDateTime now = LocalDateTime.now();
        dept.setCreatedAt(now);
        dept.setUpdatedAt(now);

        Department saved = departmentRepository.save(dept);

        return convertToDTO(saved);
    }



    @Override
    public DepartmentDTO updateDepartment(String departmentId, DepartmentDTO departmentDTO) {
        Optional<Department> existing = departmentRepository.findById((departmentId));
        if (existing.isEmpty()) return null;

        Department dept = existing.get();
        dept.setDepartmentName(departmentDTO.getName());
        dept.setStatus(departmentDTO.getStatus());

        Department updated = departmentRepository.save(dept);
        return convertToDTO(updated);
    }

    @Override
    public DepartmentDTO deactivateDepartment(String departmentId) {
        Optional<Department> existing = departmentRepository.findById((departmentId));
        if (existing.isEmpty()) return null;

        Department dept = existing.get();
        dept.setStatus("inactive");
        Department updated = departmentRepository.save(dept);

        return convertToDTO(updated);
    }

    @Override
    public DepartmentDTO getDepartmentHierarchy(String departmentId) {
        Optional<Department> deptOpt = departmentRepository.findById((departmentId));
        return deptOpt.map(this::convertToDTO).orElse(null);
    }

    @Override
    public long countDepartments() {
        return 0;
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(String.valueOf(department.getId()));  // use getter
        dto.setName(department.getDepartmentName());
        dto.setStatus(department.getStatus());
        // 🚨 TODO: map roles → positions → users if you want full hierarchy
        return dto;
    }

}

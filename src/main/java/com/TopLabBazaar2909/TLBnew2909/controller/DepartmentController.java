package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.DepartmentService;
import com.TopLabBazaar2909.TLBnew2909.dto.DepartmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth-team-services/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/user/department/test")
    public String testDepartments() {
        long count = departmentService.countDepartments();
        return "Total Departments: " + count;
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/department/getall")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }


    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/user/department/create")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO created = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(created);
    }

    //@PreAuthorize("isAuthenticated()")
    @PutMapping("/user/department/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable("id") String departmentId,
            @RequestBody DepartmentDTO departmentDTO) {

        DepartmentDTO updated = departmentService.updateDepartment(departmentId, departmentDTO);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }


    //@PreAuthorize("isAuthenticated()")
    @PatchMapping("/user/department/{id}/deactivate")
    public ResponseEntity<DepartmentDTO> deactivateDepartment(@PathVariable("id") String departmentId) {
        DepartmentDTO deactivated = departmentService.deactivateDepartment(departmentId);
        if (deactivated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivated);
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/department/{id}/hierarchy")
    public ResponseEntity<DepartmentDTO> getDepartmentHierarchy(@PathVariable("id") String departmentId) {
        DepartmentDTO dept = departmentService.getDepartmentHierarchy(departmentId);
        if (dept == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dept);
    }
}

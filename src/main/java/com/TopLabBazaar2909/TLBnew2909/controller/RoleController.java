package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.RoleService;
import com.TopLabBazaar2909.TLBnew2909.dto.RoleDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.Department;
import com.TopLabBazaar2909.TLBnew2909.entity.Role22;
import com.TopLabBazaar2909.TLBnew2909.entity.Status;
import com.TopLabBazaar2909.TLBnew2909.repository.DepartmentRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth-team-services/role")
public class RoleController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StatusRepository statusRepository;

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/role/getall")
    public ResponseEntity<List<Role22>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }


    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/user/role/create")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }


    //@PreAuthorize("isAuthenticated()")
    @PutMapping("/user/role/:id")
    public ResponseEntity<Role22> updateRole(
            @PathVariable String roleId,
            @RequestBody Role22 roleDetails,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String statusName) {

        Department dept = null;
        if (departmentName != null) {
            dept = departmentRepository.findByDepartmentName(departmentName)
                    .orElseThrow(() -> new RuntimeException("Department not found: " + departmentName));
        }

        Status status = null;
        if (statusName != null) {
            status = statusRepository.findByName(statusName)
                    .orElseThrow(() -> new RuntimeException("Status not found: " + statusName));
        }

        Role22 updated = roleService.updateRole(roleId, roleDetails, dept, status);
        return ResponseEntity.ok(updated);
    }


    //@PreAuthorize("isAuthenticated()")
    @PutMapping("/user/role/{roleId}/with-department-status")
    public ResponseEntity<Role22> updateRoleWithDeptAndStatus(
            @PathVariable String roleId,
            @RequestBody Role22 roleDetails,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String statusValue) {

        Department dept = null;
        if (departmentName != null) {
            dept = departmentRepository.findByDepartmentName(departmentName)
                    .orElseThrow(() -> new RuntimeException("Department not found: " + departmentName));
        }

        Status status = null;
        if (statusValue != null) {
            status = statusRepository.findByName(statusValue.toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Status not found: " + statusValue));
        }

        Role22 updated = roleService.updateRole(roleId, roleDetails, dept, status);
        return ResponseEntity.ok(updated);
    }


    //@PreAuthorize("isAuthenticated()")
    @PatchMapping("/user/role/:id/deactivate")
    public ResponseEntity<String> deactivateRole(@PathVariable("id") String roleId) {
        Role22 deactivated = roleService.deactivateRole(roleId);
        if (deactivated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Role deactivated successfully");
    }
}

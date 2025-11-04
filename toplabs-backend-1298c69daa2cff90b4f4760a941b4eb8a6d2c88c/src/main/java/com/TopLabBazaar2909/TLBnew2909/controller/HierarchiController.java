package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.HierarchyService;
import com.TopLabBazaar2909.TLBnew2909.dto.PositionDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.RoleDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.UserTeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hierarchy")
public class HierarchiController {
    private final HierarchyService hierarchyService;

    public HierarchiController(HierarchyService hierarchyService) {
        this.hierarchyService = hierarchyService;
    }

    //  Get Roles by Department
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/departments/{departmentId}/roles")
    public ResponseEntity<List<RoleDTO>> getRolesByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(hierarchyService.getRolesByDepartment(departmentId));
    }

    //  Get Positions by Department
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/departments/{departmentId}/positions")
    public ResponseEntity<List<PositionDTO>> getPositionsByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(hierarchyService.getPositionsByDepartment(departmentId));
    }

    //  Get Positions by Role
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/roles/{roleId}/positions")
    public ResponseEntity<List<PositionDTO>> getPositionsByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(hierarchyService.getPositionsByRole(roleId));
    }

    //  Get Users by Department
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/departments/{departmentId}/users")
    public ResponseEntity<List<UserTeamDTO>> getUsersByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(hierarchyService.getUsersByDepartment(departmentId));
    }

    //  Get Users by Role
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/roles/{roleId}/users")
    public ResponseEntity<List<UserTeamDTO>> getUsersByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(hierarchyService.getUsersByRole(roleId));
    }

    //  Get Users by Position
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/positions/{positionId}/users")
    public ResponseEntity<List<UserTeamDTO>> getUsersByPosition(@PathVariable String positionId) {
        return ResponseEntity.ok(hierarchyService.getUsersByPosition(positionId));
    }

    //  Get Positions by Department + Role
   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/departments/{departmentId}/roles/{roleId}/positions")
    public ResponseEntity<List<PositionDTO>> getPositionsByDeptRole(
            @PathVariable String departmentId,
            @PathVariable String roleId) {
        return ResponseEntity.ok(hierarchyService.getPositionsByDeptRole(departmentId, roleId));
    }
}

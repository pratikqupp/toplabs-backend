package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.HierarchyService;
import com.toplabs.bazaar.ServiceIntr.PositionService;
import com.toplabs.bazaar.dto.PositionDTO;
import com.toplabs.bazaar.dto.RoleDTO;
import com.toplabs.bazaar.dto.UserTeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth-team-services/filter")
public class FilterController {

    private final PositionService positionService;

    private final HierarchyService hierarchyService;

    public FilterController(PositionService positionService, HierarchyService hierarchyService) {
        this.positionService = positionService;
        this.hierarchyService = hierarchyService;
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/position/getall")
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }


    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/user/position/create")
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(positionService.createPosition(positionDTO));
    }


    //@PreAuthorize("isAuthenticated()")
    @PutMapping("/user/position/{positionId}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable String positionId,
                                                      @RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(positionService.updatePosition(positionId, positionDTO));
    }


    //@PreAuthorize("isAuthenticated()")
    @PatchMapping("/user/position/{id}/deactivate")
    public ResponseEntity<PositionDTO> deactivatePosition(@PathVariable String id) {
        return ResponseEntity.ok(positionService.deactivatePosition(id));
    }


    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/user/position/map")
    public ResponseEntity<PositionDTO> mapUserToPosition(@RequestParam String positionId,
                                                         @RequestParam String userId,
                                                         @RequestParam(required = false) String assignedBy,
                                                         @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(
                positionService.mapUserToPosition(Long.valueOf(positionId), userId, assignedBy, reason)
        );
    }


    //@PreAuthorize("isAuthenticated()")
    @PostMapping("/user/position/unmap")
    public ResponseEntity<PositionDTO> unmapUserFromPosition(@RequestParam String positionId,
                                                             @RequestParam(required = false) String unassignedBy,
                                                             @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(positionService.unmapUserFromPosition(positionId, unassignedBy, reason));
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/position/department/{departmentId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(positionService.getPositionsByDepartment(departmentId));
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/position/role/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByRole(roleId));
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/position/deptrole/{departmentId}/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDeptAndRole(@PathVariable String departmentId,
                                                                    @PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByDeptRole(departmentId, roleId));
    }
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/user/department/{departmentId}")
    public ResponseEntity<List<UserTeamDTO>> getUsersByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(hierarchyService.getUsersByDepartment(departmentId));
    }
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/user/role/{roleId}")
    public ResponseEntity<List<UserTeamDTO>> getUsersByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(hierarchyService.getUsersByRole(roleId));
    }
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/user/position/{positionId}")
    public ResponseEntity<List<UserTeamDTO>> getUsersByPosition(@PathVariable String positionId) {
        return ResponseEntity.ok(hierarchyService.getUsersByPosition(positionId));
    }
    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/filter/role/department/{departmentId}")
    public ResponseEntity<List<RoleDTO>> getRolesByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(hierarchyService.getRolesByDepartment(departmentId));
    }
}

package com.toplabs.bazaar.controller;

import com.toplabs.bazaar.ServiceIntr.PositionService;
import com.toplabs.bazaar.dto.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth-team-services/position")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
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
    @GetMapping("/user/position/department/{departmentId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(positionService.getPositionsByDepartment(departmentId));
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/position/role/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByRole(roleId));
    }


    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/position/department/{departmentId}/role/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDeptRole(@PathVariable String departmentId,
                                                                    @PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByDeptRole(departmentId, roleId));
    }
}

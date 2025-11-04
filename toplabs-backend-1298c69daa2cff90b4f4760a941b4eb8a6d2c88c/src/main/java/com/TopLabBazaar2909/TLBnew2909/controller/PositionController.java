package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.PositionService;
import com.TopLabBazaar2909.TLBnew2909.dto.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    
    // Get all positions
   //@PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    //  Create new position
   //@PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(positionService.createPosition(positionDTO));
    }

    //  Update position
   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{positionId}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable String positionId,
                                                      @RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(positionService.updatePosition(positionId, positionDTO));
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{positionId}/deactivate")
    public ResponseEntity<PositionDTO> deactivatePosition(@PathVariable String positionId) {
        return ResponseEntity.ok(positionService.deactivatePosition(positionId));
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{positionId}/map-user/{userId}")
    public ResponseEntity<PositionDTO> mapUserToPosition(@PathVariable String positionId,
                                                         @PathVariable String userId,
                                                         @RequestParam(required = false) String assignedBy,
                                                         @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(positionService.mapUserToPosition(Long.valueOf(positionId), userId, assignedBy, reason));
    }

   //@PreAuthorize("isAuthenticated()")
    @PutMapping("/{positionId}/unmap-user")
    public ResponseEntity<PositionDTO> unmapUserFromPosition(@PathVariable String positionId,
                                                             @RequestParam(required = false) String unassignedBy,
                                                             @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(positionService.unmapUserFromPosition(positionId, unassignedBy, reason));
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDepartment(@PathVariable String departmentId) {
        return ResponseEntity.ok(positionService.getPositionsByDepartment(departmentId));
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByRole(@PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByRole((roleId)));
    }

   //@PreAuthorize("isAuthenticated()")
    @GetMapping("/department/{departmentId}/role/{roleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByDeptRole(@PathVariable String departmentId,
                                                                    @PathVariable String roleId) {
        return ResponseEntity.ok(positionService.getPositionsByDeptRole(departmentId, roleId));
    }
}

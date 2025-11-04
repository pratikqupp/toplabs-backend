package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.PositionDTO;

import java.util.List;

public interface PositionService {
    List<PositionDTO> getAllPositions();

    PositionDTO createPosition(PositionDTO positionDTO);

    PositionDTO updatePosition(String positionId, PositionDTO positionDTO);

    PositionDTO deactivatePosition(String positionId);

    //PositionDTO mapUserToPosition(String positionId, String userId, String assignedBy, String reason);

    PositionDTO mapUserToPosition(Long positionId, String userId, String assignedBy, String reason);

    PositionDTO unmapUserFromPosition(String positionId, String unassignedBy, String reason);

    List<PositionDTO> getPositionsByDepartment(String departmentId);




    //List<PositionDTO> getPositionsByRole(Long roleId);

  //  List<PositionDTO> getPositionsByDeptRole(Long departmentId, Long roleId);

    List<PositionDTO> getPositionsByRole(String roleId);

    List<PositionDTO> getPositionsByDeptRole(String departmentId, String roleId);
}

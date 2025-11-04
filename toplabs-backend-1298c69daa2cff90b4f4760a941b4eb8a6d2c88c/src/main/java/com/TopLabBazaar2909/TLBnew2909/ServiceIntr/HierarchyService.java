package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.PositionDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.RoleDTO;
import com.TopLabBazaar2909.TLBnew2909.dto.UserTeamDTO;

import java.util.List;

public interface HierarchyService {
    List<RoleDTO> getRolesByDepartment(String departmentId);
    List<PositionDTO> getPositionsByDepartment(String departmentId);
    List<PositionDTO> getPositionsByRole(String roleId);
    List<UserTeamDTO> getUsersByDepartment(String departmentId);
    List<UserTeamDTO> getUsersByRole(String roleId);
    List<UserTeamDTO> getUsersByPosition(String positionId);
   // List<PositionDTO> getPositionsByDeptRole(Long departmentId, Long roleId);

    List<PositionDTO> getPositionsByDeptRole(String departmentId, String roleId);
}

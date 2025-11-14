package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.PositionDTO;
import com.toplabs.bazaar.dto.RoleDTO;
import com.toplabs.bazaar.dto.UserTeamDTO;

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

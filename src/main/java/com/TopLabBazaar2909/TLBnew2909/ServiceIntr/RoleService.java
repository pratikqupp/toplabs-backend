package com.TopLabBazaar2909.TLBnew2909.ServiceIntr;

import com.TopLabBazaar2909.TLBnew2909.dto.RoleDTO;
import com.TopLabBazaar2909.TLBnew2909.entity.*;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role22> getAllRoles();

    RoleDTO createRole(RoleDTO role);


    Role22 updateRole(String roleId, Role22 roleDetails, Department department, Status status);

    //Role22 updateRole(String roleId, Role22 roleDetails, Department department, com.TopLabBazaar2909.TLBnew2909.Enum.Status status);

   // Role22 updateRole(String roleId, Role22 roleDetails, Department department, com.TopLabBazaar2909.TLBnew2909.Enum.Status status);

    Role22 deactivateRole(String roleId);

    Optional<Role22> getRoleHierarchy(String roleId);

    List<Position> getPositionsByRole(String roleId);

    List<AppUser> getUsersByRole(String roleId);

}

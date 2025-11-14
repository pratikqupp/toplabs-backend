package com.toplabs.bazaar.ServiceIntr;

import com.toplabs.bazaar.dto.RoleDTO;
import com.toplabs.bazaar.entity.*;

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

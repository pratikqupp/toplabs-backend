package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.Enum.Status;
import com.toplabs.bazaar.ServiceIntr.RoleService;
import com.toplabs.bazaar.dto.RoleDTO;
import com.toplabs.bazaar.entity.*;
import com.toplabs.bazaar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Status status;

    @Override
    public List<Role22> getAllRoles() {
        return roleRepository.findAll();
    }


    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        if (roleDTO == null) return null;


        com.toplabs.bazaar.entity.Status statusEntity;
        if (roleDTO.getStatus() == null) {
            statusEntity = statusRepository.findByName("ACTIVE")
                    .orElseThrow(() -> new RuntimeException("Default status 'ACTIVE' not found"));
        } else {
            statusEntity = statusRepository.findByName(roleDTO.getStatus())
                    .orElseThrow(() -> new RuntimeException("Status '" + roleDTO.getStatus() + "' not found"));
        }

        //  Fetch Department
        Department department = departmentRepository.findById(roleDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + roleDTO.getDepartmentId()));

        //  Create new Role entity
        Role22 roleEntity = new Role22();

        //  Use provided ID if available, else auto-generate
        if (roleDTO.getId() != null && !roleDTO.getId().isEmpty()) {
            roleEntity.setId(roleDTO.getId());
        } else {
            roleEntity.setId("ROLE" + System.currentTimeMillis()); // fallback if you want auto IDs
        }

        roleEntity.setRoleName(roleDTO.getRoleName());
        roleEntity.setDescription(roleDTO.getDescription());
        roleEntity.setStatus(statusEntity);
        roleEntity.setDepartment(department);

        //  Save entity
        Role22 savedRole = roleRepository.save(roleEntity);

        //  Convert to DTO
        RoleDTO savedDTO = new RoleDTO();
        savedDTO.setId(savedRole.getId());
        savedDTO.setRoleName(savedRole.getRoleName());
        savedDTO.setDescription(savedRole.getDescription());
        savedDTO.setDepartmentId(savedRole.getDepartment().getId());
        savedDTO.setStatus(savedRole.getStatus().getName());

        return savedDTO;
    }

//    @Override
//    public Role22 updateRole(String roleId, Role22 roleDetails, Department department, com.TopLabBazaar2909.TLBnew2909.entity.Status status) {
//        return null;
//    }

    @Override
    public Role22 updateRole(String roleId, Role22 roleDetails, Department department, com.toplabs.bazaar.entity.Status status) {
        Role22 role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        if (roleDetails.getRoleName() != null) {
            role.setRoleName(roleDetails.getRoleName());
        }

        if (roleDetails.getDescription() != null) {
            role.setDescription(roleDetails.getDescription());
        }

        if (department != null) {
            role.setDepartment(department);
        }

        if (status != null) {
            role.setStatus(status); // ✅ use the entity directly
        }

        return roleRepository.save(role);
    }




//    @Override
//    public Role22 updateRole(String roleId, Role22 roleDetails, Department department, Status status) {
//        Role22 role = roleRepository.findByRoleId(roleId);
//        if (role != null) {
//            role.setRoleName(roleDetails.getRoleName());
//            department.setDepartmantname(department.getDepartmantname());
//            status.getClass(status.getStatus());
//            return roleRepository.save(role);
//        }
//        return null;
//    }

    @Override
    public Role22 deactivateRole(String roleId) {
        Optional<Role22> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isPresent()) {
            Role22 role = optionalRole.get();

            com.toplabs.bazaar.entity.Status inactiveStatus = statusRepository.findByName("INACTIVE")
                    .orElseThrow(() -> new RuntimeException("Status 'INACTIVE' not found"));

            role.setStatus(inactiveStatus);
            return roleRepository.save(role);
        }

        return null;
    }




    @Override
    public Optional<Role22> getRoleHierarchy(String roleId) {
        return roleRepository.findById(Long.valueOf(roleId));
    }

    @Override
    public List<Position> getPositionsByRole(String roleId) {
        return positionRepository.findByRole_Id((roleId));
    }

    @Override
    public List<AppUser> getUsersByRole(String roleId) {
        return userRepository.findByRoleId(roleId);
    }
}

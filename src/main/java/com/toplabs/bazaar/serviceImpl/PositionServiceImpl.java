package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.Enum.PositionStatus;
import com.toplabs.bazaar.ServiceIntr.PositionService;
import com.toplabs.bazaar.dto.PositionDTO;
import com.toplabs.bazaar.entity.*;
import com.toplabs.bazaar.repository.AppUserRepository;
import com.toplabs.bazaar.repository.DepartmentRepository;
import com.toplabs.bazaar.repository.PositionRepository;
import com.toplabs.bazaar.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PositionDTO createPosition(PositionDTO positionDTO) {
        Position position = new Position();

        // Set name
        position.setName(positionDTO.getName());

        // Set Department if provided
        if (positionDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(String.valueOf(positionDTO.getDepartmentId())) // assuming Long ID
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            position.setDepartment(dept);
        }

        // Set Role if provided
        if (positionDTO.getRoleId() != null) {
            Role22 role = roleRepository.findById(positionDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            position.setRole(role);
        }

        // Set ReportsTo if provided
        if (positionDTO.getReportsToId() != null) {
            Position manager = positionRepository.findById(positionDTO.getReportsToId())
                    .orElseThrow(() -> new RuntimeException("ReportsTo position not found"));
            position.setReportsTo(manager);
        }

        // Set status
        position.setStatus(positionDTO.getStatus() != null ? positionDTO.getStatus() : PositionStatus.ACTIVE);

        // Set contact info
        position.setMobile(positionDTO.getMobile());
        position.setEmail(positionDTO.getEmail());
        position.setPassword(positionDTO.getPassword());

        // Save position
        Position savedPosition = positionRepository.save(position);

        return convertToDTO(savedPosition);
    }



    @Override
    public PositionDTO updatePosition(String positionId, PositionDTO positionDTO) {
        // Get or create position dynamically
        Position position = getOrCreatePositionById(positionId, positionDTO);

        if (positionDTO.getRoleName() != null) {
            position.setName(positionDTO.getRoleName());
        }

        if (positionDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(((positionDTO.getDepartmentId())))
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            position.setDepartment(dept);
        }

        if (positionDTO.getRoleId() != null) {
            Role22 role = roleRepository.findById(((positionDTO.getRoleId())))
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            position.setRole(role);
        }

        if (positionDTO.getStatus() != null) {
            position.setStatus(positionDTO.getStatus());
        }

        Position updatedPosition = positionRepository.save(position);
        return convertToDTO(updatedPosition);
    }

    // Helper method: Get existing position or create new one
    private Position getOrCreatePositionById(String positionId, PositionDTO positionDTO) {
        if (positionId == null) return new Position(); // No ID provided, create new

        return positionRepository.findById(Long.parseLong(positionId))
                .orElseGet(() -> {
                    Position newPosition = new Position();
                    newPosition.setName(positionDTO.getRoleName() != null ? positionDTO.getRoleName() : "Default Position");

                    if (positionDTO.getDepartmentId() != null) {
                        Department dept = departmentRepository.findById(String.valueOf(positionDTO.getDepartmentId()))
                                .orElse(null);
                        newPosition.setDepartment(dept);
                    }

                    if (positionDTO.getRoleId() != null) {
                        Role22 role = roleRepository.findById(Long.parseLong(String.valueOf(positionDTO.getRoleId())))
                                .orElse(null);
                        newPosition.setRole(role);
                    }

                    newPosition.setStatus(positionDTO.getStatus() != null ? positionDTO.getStatus() : PositionStatus.ACTIVE);

                    return positionRepository.save(newPosition);
                });
    }

    @Override
    public PositionDTO deactivatePosition(String positionId) {
        Position position = positionRepository.findById(Long.parseLong(positionId))
                .orElseThrow(() -> new RuntimeException("Position not found"));

        position.setStatus(PositionStatus.INACTIVE);
        Position updatedPosition = positionRepository.save(position);
        return convertToDTO(updatedPosition);
    }

//    @Override
//    public PositionDTO mapUserToPosition(String positionId, String userId, String assignedBy, String reason) {
//        return null;
//    }

    @Override
    public PositionDTO mapUserToPosition(Long positionId, String userId, String assignedBy, String reason) {
        // 1️⃣ Fetch the Position
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with ID: " + positionId));

        // 2️⃣ Fetch the User
        AppUser user = appUserRepository.findById((userId))
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 3️⃣ Create new assignment
        UserAssignment assignment = new UserAssignment();
        assignment.setUserId(user.getId());
        assignment.setUserName(user.getUserName());
        assignment.setAssignedBy(assignedBy);
        assignment.setReason(reason);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setPosition(position);

        // 4️⃣ Add assignment to history
        if (position.getAssignmentHistory() == null) {
            position.setAssignmentHistory(new ArrayList<>());
        }
        position.getAssignmentHistory().add(assignment);

        // 5️⃣ Update current assignment
        position.setCurrentAssignment(assignment);
        position.setCurrentUser(user);

        // 6️⃣ Persist everything
        position = positionRepository.save(position);

        // 7️⃣ Convert and return DTO
        return convertToDTO(position);
    }


    @Override
    public PositionDTO unmapUserFromPosition(String positionId, String unassignedBy, String reason) {
        // Fetch the Position
        Position position = positionRepository.findById(Long.parseLong(positionId))
                .orElseThrow(() -> new RuntimeException("Position not found with ID: " + positionId));

        // Check if a user is currently assigned
        if (position.getCurrentAssignment() == null) {
            throw new RuntimeException("No user currently assigned to this position");
        }

        //  Get the current assignment
        UserAssignment currentAssignment = position.getCurrentAssignment();

        //  Mark this assignment as unassigned
        currentAssignment.setUnassignedBy(unassignedBy);
        currentAssignment.setUnassignedReason(reason);
        currentAssignment.setUnassignedAt(LocalDateTime.now());

        //  Clear the current user and current assignment from the position
        position.setCurrentUser(null);
        position.setCurrentAssignment(null);

        //  Save the updated position (cascade will update UserAssignment as well)
        positionRepository.save(position);

        //  Return the updated DTO
        return convertToDTO(position);
    }


    @Override
    public List<PositionDTO> getPositionsByDepartment(String departmentId) {
        return positionRepository.findByDepartment_Id((departmentId)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PositionDTO> getPositionsByRole(String roleId) {
        return positionRepository.findByRole_Id(roleId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PositionDTO> getPositionsByDeptRole(String departmentId, String roleId) {
        return positionRepository.findByDepartmentIdAndRoleId(departmentId, roleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PositionDTO convertToDTO(Position pos) {
        PositionDTO dto = new PositionDTO();

        dto.setPositionId(pos.getId());
        dto.setName(pos.getName());
        dto.setStatus(pos.getStatus());
        dto.setMobile(pos.getMobile());
        dto.setEmail(pos.getEmail());
        dto.setPassword(pos.getPassword());

        // Department
        if (pos.getDepartment() != null) {
            dto.setDepartmentId(pos.getDepartment().getId());
            dto.setDepartmentName(pos.getDepartment().getDepartmentName());
        }

        // Role
        if (pos.getRole() != null) {
            dto.setRoleId(pos.getRole().getId());
            dto.setRoleName(pos.getRole().getRoleName());
        }

        // Reports To (Manager)
        if (pos.getReportsTo() != null) {
            dto.setReportsToId(pos.getReportsTo().getId());
            dto.setReportsToName(pos.getReportsTo().getName());
        }

        // Current Assignment / User
        if (pos.getCurrentAssignment() != null) {
            dto.setCurrentAssignmentId(pos.getCurrentAssignment().getId());
            dto.setCurrentUserId(pos.getCurrentAssignment().getUserId() != null ?
                 (pos.getCurrentAssignment().getUserId()) : null);
            dto.setCurrentUserName(pos.getCurrentAssignment().getUserName());
        }

        // Assignment history
        if (pos.getAssignmentHistory() != null) {
            dto.setAssignmentHistoryIds(
                    pos.getAssignmentHistory()
                            .stream()
                            .map(UserAssignment::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

}
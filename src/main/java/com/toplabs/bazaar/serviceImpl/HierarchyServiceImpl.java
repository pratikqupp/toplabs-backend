package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.ServiceIntr.HierarchyService;
import com.toplabs.bazaar.dto.PositionDTO;
import com.toplabs.bazaar.dto.RoleDTO;
import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.*;
import com.toplabs.bazaar.repository.PositionRepository;
import com.toplabs.bazaar.repository.RoleRepository;
import com.toplabs.bazaar.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HierarchyServiceImpl implements HierarchyService {
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public HierarchyServiceImpl(RoleRepository roleRepository, PositionRepository positionRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RoleDTO> getRolesByDepartment(String departmentId) {
        return roleRepository.findByDepartmentId(((departmentId)))
                .stream()
                .map(r -> new RoleDTO(r.getId(), r.getRoleName(), r.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PositionDTO> getPositionsByDepartment(String departmentId) {
        return positionRepository.findByDepartment_Id((departmentId))
                .stream()
                .map(this::mapToPositionDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PositionDTO> getPositionsByRole(String roleId) {
        return positionRepository.findByRole_Id((roleId))
                .stream()
                .map(this::mapToPositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserTeamDTO> getUsersByDepartment(String departmentId) {
        return userRepository.findByPositionId(Long.valueOf((departmentId)))
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }


    private UserTeamDTO mapToUserDTO(AppUser user) {
        UserTeamDTO dto = new UserTeamDTO();


        dto.setId(user.getId() != null ? user.getId().toString() : null);


        dto.setFirstName(user.getFirstName());
        dto.setEmail(user.getEmail());


        if (user.getDepartment() != null) {
            dto.setDepartmentName(String.valueOf(user.getDepartment().getId()));
        } else {
            dto.setDepartmentName(null);
        }

        // 🔧 You can extend this later:
        // dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
        // dto.setStatus(user.getStatus() != null ? user.getStatus().name() : null);
        // dto.setCreatedDate(user.getCreatedDate());

        return dto;


}



    @Override
    public List<UserTeamDTO> getUsersByRole(String roleId) {
        return userRepository.findByRoleId(roleId)
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserTeamDTO> getUsersByPosition(String positionId) {
        return userRepository.findByPositionId(Long.valueOf(positionId))
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<PositionDTO> getPositionsByDeptRole(String departmentId, String roleId) {
        List<Position> positions = positionRepository.findByDepartmentIdAndRoleId(departmentId, roleId);
        return positions.stream()
                .map(this::mapToPositionDTO)
                .collect(Collectors.toList());
    }


    // 🔹 Helpers
     private PositionDTO mapToPositionDTO(Position position) {
        return new PositionDTO(
                String.valueOf(position.getId()),
                position.getRole() != null ? position.getRole().getRoleName() : null,   // from Role entity
                position.getDepartment() != null ? position.getDepartment().getDepartmentName() : null, // from Department entity
                position.getStatus()
        );
    }
    private UserTeamDTO mapToUserDTO(UserTeam user) {
        return new UserTeamDTO(
                Long.valueOf(user.getId() != null ? user.getId().toString() : null),
                user.getFirstName(),
                user.getLastName(),
                user.getMobile(),
                user.getDepartmentId(),
                user.getRoleId(),
                user.getPositionId(),
                user.getEmail(),
                user.getStatus(),
                user.getConfig() != null && user.getConfig().isReport(),
                user.getConfig() != null && user.getConfig().isBooking(),
                user.getConfig() != null && user.getConfig().isNotification()
        );
    }

}









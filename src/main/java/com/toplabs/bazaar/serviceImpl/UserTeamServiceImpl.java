package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.ServiceIntr.UserTeamService;
import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.UserTeam;
import com.toplabs.bazaar.repository.UserTeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserTeamServiceImpl implements UserTeamService {
    private final UserTeamRepository userTeamRepository;

    public UserTeamServiceImpl(UserTeamRepository userTeamRepository) {
        this.userTeamRepository = userTeamRepository;
    }

    @Override
    @Transactional
    public UserTeam createUser(UserTeamDTO dto) {
        UserTeam userTeam;

        if (dto.getId() != null) {
            // Update existing entity if ID is provided
            userTeam = userTeamRepository.findById(Long.valueOf(dto.getId()))
                    .orElseThrow(() -> new RuntimeException("UserTeam not found with id " + dto.getId()));
        } else {
            // Create new entity
            userTeam = new UserTeam();
            userTeam.setCreatedAt(LocalDateTime.now()); // set created timestamp only for new entity
        }

        // Set/update all fields
        userTeam.setFirstName(dto.getFirstName());
        userTeam.setLastName(dto.getLastName());
        userTeam.setMobile(dto.getMobile());
        userTeam.setEmail(dto.getEmail());
        userTeam.setPassword(dto.getPassword());
        userTeam.setStatus(dto.getStatus());

        userTeam.setAddress(dto.getAddress());
        userTeam.setPostalCode(dto.getPostalCode());
        userTeam.setGender(dto.getGender());
        userTeam.setDateOfBirth(dto.getDateOfBirth());

        userTeam.setDepartmentId(dto.getDepartmentId());
        userTeam.setDepartmentName(dto.getDepartmentName());
        userTeam.setRoleId(dto.getRoleId());
        userTeam.setRoleName(dto.getRoleName());
        userTeam.setPositionId(dto.getPositionId());
        userTeam.setPositionName(dto.getPositionName());

        userTeam.setLabDepartment(dto.getLabDepartment());
        userTeam.setDepartments(dto.getDepartments());

        userTeam.setBooking(dto.getBooking());
        userTeam.setBookingIds(dto.getBookingIds());

        userTeam.setConfig(dto.getConfig());

        // Always update timestamp for both new and existing
        userTeam.setUpdatedAt(LocalDateTime.now());

        return userTeamRepository.save(userTeam);
    }



    @Override
    public UserTeam updateUser(Long id, UserTeam userTeam) {
        return userTeamRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(userTeam.getFirstName());
            existingUser.setLastName(userTeam.getLastName());
            existingUser.setMobile(userTeam.getMobile());
            existingUser.setEmail(userTeam.getEmail());
            existingUser.setPassword(userTeam.getPassword());
            existingUser.setStatus(userTeam.getStatus());
            existingUser.setDepartmentName(userTeam.getDepartmentName());
            existingUser.setRoleName(userTeam.getRoleName());
            existingUser.setPositionName(userTeam.getPositionName());
            existingUser.setConfig(userTeam.getConfig());
            return userTeamRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userTeamRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userTeamRepository.deleteById(id);
    }

    @Override
    public Optional<UserTeam> getUserById(Long id) {
        return userTeamRepository.findById(id);
    }

    @Override
    public Optional<UserTeam> getUserByEmail(String email) {
        return userTeamRepository.findByEmail(email);
    }

    @Override
    public Optional<UserTeam> getUserByMobile(String mobile) {
        return userTeamRepository.findByMobile(mobile);
    }

    @Override
    public List<UserTeam> getAllUsers() {
        return userTeamRepository.findAll();
    }
}

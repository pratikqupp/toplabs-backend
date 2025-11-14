package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.Enum.LabDepartment;
import com.toplabs.bazaar.Enum.UserTeamStatus;
import com.toplabs.bazaar.ServiceIntr.UserService;
import com.toplabs.bazaar.dto.UserTeamDTO;
import com.toplabs.bazaar.entity.UserAssignment;
import com.toplabs.bazaar.entity.*;
import com.toplabs.bazaar.repository.DepartmentRepository;
import com.toplabs.bazaar.repository.PositionRepository;
import com.toplabs.bazaar.repository.RoleRepository;
import com.toplabs.bazaar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser createUser(UserTeamDTO userDTO) {
        AppUser user = new AppUser();

        if (userDTO.getId() != null && !userDTO.getId().isEmpty()) {
            user.setId(userDTO.getId());
        }


        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());


        if (userDTO.getMobile() != null && !userDTO.getMobile().isEmpty()) {
            try {
                user.setMobileNumber((userDTO.getMobile()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid mobile number: " + userDTO.getMobile());
            }
        }


        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : UserTeamStatus.ACTIVE);


        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //  Department (entity relationship)
        if (userDTO.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(userDTO.getDepartmentId()).orElse(null);
            user.setDepartment(dept);
        }

        //  Role
        if (userDTO.getRoleId() != null) {
            Role22 role = roleRepository.findById(userDTO.getRoleId()).orElse(null);
            user.setRole(role);
        }

        //  Position (optional)
        if (userDTO.getPositionId() != null) {
            try {
                Position pos = positionRepository.findById(Long.valueOf(userDTO.getPositionId())).orElse(null);
                user.setPosition(pos);
            } catch (NumberFormatException e) {
                user.setPosition(null);
            }
        }

        //  New profile fields
        user.setAddress(userDTO.getAddress());

        if (userDTO.getPostalCode() != null && !userDTO.getPostalCode().isEmpty()) {
            user.setPostalCode((userDTO.getPostalCode()));
        }

        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        if (userDTO.getLabDepartment() != null && !userDTO.getLabDepartment().isEmpty()) {
            user.setLabDepartment(LabDepartment.valueOf(userDTO.getLabDepartment()));
        }

        // Multiple departments (comma-separated)
        if (userDTO.getDepartments() != null && !userDTO.getDepartments().isEmpty()) {
            String joinedDepartments = String.join(",", userDTO.getDepartments());
            user.setDepartments(joinedDepartments);
        } else {
            user.setDepartments(null);
        }

        //  Multiple bookings (comma-separated)
        if (userDTO.getBookingIds() != null && !userDTO.getBookingIds().isEmpty()) {
            String joinedBookings = String.join(",", userDTO.getBookingIds());
            user.setBookingIds(Collections.singletonList(joinedBookings));
        } else {
            user.setBookingIds(null);
        }

        if (userDTO.getBooking() != null) {
            user.setBooking(userDTO.getBooking());
        } else {
            user.setBooking(false);
        }


        //  Save user
        return userRepository.save(user);
    }







    @Override
    public AppUser updateUser(String userId, UserTeam userDetails) {
        Optional<AppUser> existingOpt = userRepository.findById(userId);
        if (existingOpt.isEmpty()) return null;

        AppUser user = existingOpt.get();

        // Update basic info
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setMobileNumber((userDetails.getMobile()));
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        // Update Department if provided
        if (userDetails.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(String.valueOf((userDetails.getDepartmentId()))).orElse(null);
            user.setDepartment(dept);
        }

        // Update Role if provided
        if (userDetails.getRoleId() != null) {
            Role22 role = roleRepository.findById((userDetails.getRoleId())).orElse(null);
            user.setRole(role);
        }

        // Update Position if provided
        if (userDetails.getPositionId() != null) {
            Position pos = positionRepository.findById(Long.valueOf((userDetails.getPositionId()))).orElse(null);
            user.setPosition(pos);

            if (pos != null) {
                // ✅ Use the separate Assignment class
                UserAssignment assign = new UserAssignment(
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        null, // assignedBy if needed
                        null  // reason if needed
                );

                // Set current assignment in position
                pos.setCurrentAssignment(assign);

                // Add to assignment history
                if (pos.getAssignmentHistory() == null) {
                    pos.setAssignmentHistory(new ArrayList<>());
                }
                pos.getAssignmentHistory().add(assign);

                positionRepository.save(pos);
            }
        }

        // Save and return updated user
        return userRepository.save(user);
    }



    @Override
    public AppUser getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public AppUser getUserByMobile(String mobile) {
        return userRepository.findByMobileNumber((mobile)).orElse(null);
    }

    @Override
    public boolean validateUserByMobile(String mobile) {
        Optional<AppUser> userOpt = userRepository.findByMobileNumber((mobile));
        return userOpt.map(u -> "ACTIVE".equalsIgnoreCase(String.valueOf(u.getStatus()))).orElse(false);
    }

    @Override
    public UserTeam validateUser(String email, String password, String roleId, String departmentId, String positionId) {
        Optional<UserTeam> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return null;

        UserTeam user = userOpt.get();
        if (!"ACTIVE".equalsIgnoreCase(String.valueOf(user.getStatus()))) return null;

        if (!user.getPassword().equals(password)) return null;
        if (!user.getRoleId().equals(roleId)) return null;
        if (!user.getDepartmentId().equals(departmentId)) return null;
        if (!user.getPositionId().equals(positionId)) return null;

        return user;
    }

    @Override
    public AppUser deactivateUser(String userId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return null;

        AppUser user = userOpt.get();
        user.setStatus(UserTeamStatus.valueOf("INACTIVE"));
        return userRepository.save(user);
    }
}

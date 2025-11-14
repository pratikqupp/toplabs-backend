package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.UserTeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String id;              // Changed to String for manual ID
    private String firstName;
    private String lastName;
    private String mobile;

    // References
    private String departmentId;
    private String departmentName;

    private String roleId;
    private String roleName;

    private String positionId;
    private String positionName;

    private String email;
    private String password;

    private UserTeamStatus status;

    private ConfigDTO config;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String address;
    private String postalCode;
    private String gender;
    private LocalDate dateOfBirth;

    // Department & Lab info
    private String labDepartment;           // For lab-specific department
    private List<String> departments;       // For multiple department associations

    // Booking information
    private List<String> bookingIds;        // If a user can have multiple bookings
    private Boolean booking;

}

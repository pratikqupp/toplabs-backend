package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.Enum.UserStatus;
import com.TopLabBazaar2909.TLBnew2909.Enum.UserTeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamDTO {
    private String id;
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


    public UserTeamDTO(Long id, String firstName, String lastName, String mobile, String departmentId, String roleId, String positionId, String email, UserTeamStatus status, boolean b, boolean b1, boolean b2) {
    }


    public UserTeamDTO(Long id, String firstName, String lastName, String mobile, Long aLong, String roleId, String positionId, String email, UserTeamStatus status, boolean b, boolean b1, boolean b2) {
    }
    private String address;
    private String postalCode;
    private String gender;
    private LocalDate dateOfBirth;

    // ✅ Department & Lab info
    private String labDepartment;           // For lab-specific department
    private List<String> departments;       // For multiple department associations

    // ✅ Booking information
    // If a user can have multiple bookings, store as a list of booking IDs
    private List<String> bookingIds;

    private Boolean booking;

}

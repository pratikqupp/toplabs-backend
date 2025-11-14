package com.toplabs.bazaar.entity;

import com.toplabs.bazaar.Enum.UserTeamStatus;
import com.toplabs.bazaar.dto.ConfigDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_team")
public class UserTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserTeamStatus status;

    private String address;
    private String postalCode;
    private String gender;
    private LocalDate dateOfBirth;

    private String departmentId;
    private String departmentName;
    private String roleId;
    private String roleName;
    private String positionId;
    private String positionName;

    private String labDepartment;

    @Version
    private Integer version;

    @ElementCollection
    @CollectionTable(name = "user_team_departments", joinColumns = @JoinColumn(name = "user_team_id"))
    @Column(name = "department")
    private List<String> departments;

    private Boolean booking;  // ✅ simple Boolean column

    @ElementCollection
    @CollectionTable(name = "user_team_bookings", joinColumns = @JoinColumn(name = "user_team_id"))
    @Column(name = "booking_id") // ✅ must be different from 'booking'
    private List<String> bookingIds;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "report", column = @Column(name = "config_report")),
            @AttributeOverride(name = "booking", column = @Column(name = "config_booking")),
            @AttributeOverride(name = "notification", column = @Column(name = "config_notification"))
    })
    private ConfigDTO config;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.Enum.LabDepartment;
import com.TopLabBazaar2909.TLBnew2909.Enum.UserTeamStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users17")
public class AppUser {

    @Id
    private String id;

    @Column(nullable = false)
    private String firstName;


    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    private String address;

    private String gender;

    private LocalDate dateOfBirth;

    private String postalCode;

    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Embedded
    private Config config;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role22 role;

    @Enumerated(EnumType.STRING)
    @Column(name = "lab_department")
    private LabDepartment labDepartment;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTeamStatus status = UserTeamStatus.ACTIVE;

    @ElementCollection
    @CollectionTable(
            name = "user_bookings",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "booking_id")
    private List<String> bookingIds = new ArrayList<>();

    private String departments;


    @Column(name = "has_booking")
    private Boolean booking;

    public String getUserName() {
        return firstName + " " + (lastName != null ? lastName : "");
    }
}

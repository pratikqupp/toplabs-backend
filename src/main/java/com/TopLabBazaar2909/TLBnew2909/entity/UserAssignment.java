package com.TopLabBazaar2909.TLBnew2909.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_assignments")
public class UserAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userName;
    private String assignedBy;
    private String unassignedBy;
    private String unassignedReason;
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
    private LocalDateTime assignedAt;
    private LocalDateTime unassignedAt;



    public UserAssignment(Long id, String s, String assignedBy, String reason) {
    }

    public UserAssignment(String s, String s1, Object assignedBy, Object reason) {
    }
}

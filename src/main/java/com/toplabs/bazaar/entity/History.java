package com.toplabs.bazaar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // What action happened (ASSIGNED, UPDATED, CREATED)
    @Column(nullable = false)
    private String action;

    // Who performed the action (CareManager, LabHead, Phlebo etc.)
    private String role;

    // Assigned person ID
    private String assignedId;

    // Assigned person's name
    private String assignedName;

    // Which user/system updated
    private String updatedBy;

    // Reason for change (optional)
    private String reason;

    // Timestamp
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // ------------------- Relationships -------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Leads lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
}

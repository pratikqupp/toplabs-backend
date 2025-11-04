package com.TopLabBazaar2909.TLBnew2909.entity;

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
    private Long id; // Primary key

    @Column(nullable = false)
    private String action; // e.g., LEAD_CREATED, ASSIGNED, UPDATED, etc.

    private String createdByRole;   // Who performed the action (e.g., SYSTEM, CareManager)
    private String userId;          // ID of user who triggered the change
    private String oldValue;        // Old status/value (optional)
    private String newValue;        // New status/value (optional)
    private String reason;          // Optional reason or note

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Auto-timestamp

    // ------------------- Relationships -------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)  // matches 'lead' in Leads entity
    private Leads lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;
}

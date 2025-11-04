package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "leads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leads {

    @Id
    private String id;

    @Column(name = "user_reference_id")
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ElementCollection
    @CollectionTable(name = "lead_labs", joinColumns = @JoinColumn(name = "lead_id"))
    @Column(name = "lab")
    private List<String> labs = new ArrayList<>();

    private String patientNote;
    private String bookingAddress;
    private Double paymentAmount;

    private String careHeadId;
    private String careHeadName;
    private String careHeadStatus;

    private String careManagerId;
    private String careManagerName;
    private String careManagerStatus;

    private String labHeadId;
    private String labHeadName;
    private String labHeadStatus;

    private String phleboId;
    private String phleboName;
    private String phleboStatus;

    private String runnerId;
    private String runnerName;
    private String runnerStatus;

    private String bookingStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "phlebo_lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "phlebo_lng")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "phlebo_last_updated"))
    })
    private Location phleboLocation;

    @Column(name = "booking_creation_date")
    private LocalDateTime bookingCreationDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "runner_lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "runner_lng")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "runner_last_updated"))
    })
    private Location runnerLocation;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeadHistory> history = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // 🟩 Add these main coordinates for the lead
    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    // Helper method for history
    public void addHistory(String role, String action, String assignedId, String assignedName,
                           String updatedBy, String reason, LocalDateTime timestamp) {
        LeadHistory h = new LeadHistory();
        h.setRole(role);
        h.setAction(action);
        h.setAssignedId(assignedId);
        h.setAssignedName(assignedName);
        h.setUpdatedBy(updatedBy);
        h.setReason(reason);
        h.setTimestamp(timestamp);
        h.setLead(this);
        this.history.add(h);
    }
}

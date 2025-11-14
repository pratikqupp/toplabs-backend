package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.Enum.*;
import com.TopLabBazaar2909.TLBnew2909.embedded.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "leads",
        indexes = {
                @Index(columnList = "careHeadStatus"),
                @Index(columnList = "careManagerStatus"),
                @Index(columnList = "labHeadStatus"),
                @Index(columnList = "phleboStatus"),
                @Index(columnList = "runnerStatus"),
                @Index(columnList = "bookingStatus"),
                @Index(columnList = "paymentStatus"),
                @Index(columnList = "createdAt")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Leads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ------------------------------
    // User
    // ------------------------------
    private String userId;

    // ------------------------------
    // Labs List
    // ------------------------------
    @ElementCollection
    @CollectionTable(name = "lead_labs", joinColumns = @JoinColumn(name = "lead_id"))
    private List<LabPrice> labs = new ArrayList<>();

    // ------------------------------
    // Tests List
    // ------------------------------
    @ElementCollection
    @CollectionTable(name = "lead_tests", joinColumns = @JoinColumn(name = "lead_id"))
    private List<TestPrice> tests = new ArrayList<>();

    private LocalDateTime bookingCreationDate = LocalDateTime.now();

    private String patientNote = "";
    private String bookingAddress = "";
    private String postalCode = "";
    private String reportUrl = "";
    private LocalDateTime patientExpectedTime;

    // ------------------------------
    // Payment
    // ------------------------------
    private boolean paymentCollectedByPhlebo = false;
    private Double paymentReceivedByPhlebo = 0.0;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    private String paymentTransactionId = "";
    private Double paymentAmount = 0.0;
    private LocalDateTime paymentDate;

    // ------------------------------
    // Care Head
    // ------------------------------
    private String careHeadId = "";
    private String careHeadName = "";

    @Enumerated(EnumType.STRING)
    private CareHeadStatus careHeadStatus = CareHeadStatus.INTERESTED;

    // ------------------------------
    // Care Manager
    // ------------------------------
    private String careManagerId = "";
    private String careManagerName = "";

    @Enumerated(EnumType.STRING)
    private CareManagerStatus careManagerStatus = CareManagerStatus.ASSIGNED;

    // ------------------------------
    // Lab Head
    // ------------------------------
    private String labHeadId = "";
    private String labHeadName = "";

    @Enumerated(EnumType.STRING)
    private LabHeadStatus labHeadStatus = LabHeadStatus.BOOKING_CONFIRM;

    // ------------------------------
    // Phlebo
    // ------------------------------
    private String phleboId = "";
    private String phleboName = "";

    @Enumerated(EnumType.STRING)
    private PhleboStatus phleboStatus = PhleboStatus.ASSIGNED;

    private LocalDateTime phleboArrivalTime;
    private LocalDateTime phleboCompletionTime;
    private String phleboNote = "";

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "phlebo_lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "phlebo_lng")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "phlebo_last_updated"))
    })
    private Location phleboLocation;

    // ------------------------------
    // Runner
    // ------------------------------
    private String runnerId = "";
    private String runnerName = "";

    @Enumerated(EnumType.STRING)
    private RunnerStatus runnerStatus = RunnerStatus.ASSIGNED;

    private LocalDateTime runnerPickupTime;
    private LocalDateTime runnerDeliveryTime;
    private String runnerNote = "";

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "runner_lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "runner_lng")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "runner_last_updated"))
    })
    private Location runnerLocation;

    // ------------------------------
    // Booking Status
    // ------------------------------
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.INTERESTED;

    // ------------------------------
    // History
    // ------------------------------
    @ElementCollection
    @CollectionTable(name = "lead_history", joinColumns = @JoinColumn(name = "lead_id"))
    private List<History> history = new ArrayList<>();

    // ------------------------------
    // Timestamps
    // ------------------------------
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    // ======================================================
    // Helper Methods
    // ======================================================

    public void addHistory(
            String action,
            String role,
            String assignedId,
            String assignedName,
            String updatedBy,
            String reason,
            String string) {
        History h = new History();
        h.setAction(action);
        h.setRole(role);
        h.setAssignedId(assignedId);
        h.setAssignedName(assignedName);
        h.setUpdatedBy(updatedBy);
        h.setReason(reason);
        h.setTimestamp(LocalDateTime.now());
        this.history.add(h);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    private Double Lat;
    private Double Lng;


}

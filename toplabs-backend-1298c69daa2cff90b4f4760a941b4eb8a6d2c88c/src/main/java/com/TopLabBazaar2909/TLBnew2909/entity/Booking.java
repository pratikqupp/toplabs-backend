package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.dto.LocationDTO;
import com.TopLabBazaar2909.TLBnew2909.embedded.Location;
import com.TopLabBazaar2909.TLBnew2909.Enum.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- USER INFO ---
    private String userId;
    private String mobile;

    // --- STATUS & NOTE ---
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private String status;
    private String patientNote;
    private String bookingAddress;

    // --- LABS ---
    @ElementCollection
    @CollectionTable(name = "booking_labs", joinColumns = @JoinColumn(name = "booking_id"))
    private List<LabPrice> labs;

    // --- CARE HEAD ---
    private String careHeadId;
    private String careHeadName;

    @Enumerated(EnumType.STRING)
    private CareHeadStatus careHeadStatus;

    // --- CARE MANAGER ---
    private String careManagerId;
    private String careManagerName;

    @Enumerated(EnumType.STRING)
    private CareManagerStatus careManagerStatus;

    // --- LAB HEAD ---
    private String labHeadId;
    private String labHeadName;

    @Enumerated(EnumType.STRING)
    private LabHeadStatus labHeadStatus;

    // --- PHLEBO ---
    private String phleboId;
    private String phleboName;

    @Enumerated(EnumType.STRING)
    private PhleboStatus phleboStatus;

    private LocalDateTime phleboArrivalTime;
    private LocalDateTime phleboCompletionTime;
    private String phleboNote;

    // Embedded phlebo location (using DTO-style)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "phlebo_latitude")),
            @AttributeOverride(name = "lng", column = @Column(name = "phlebo_longitude")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "phlebo_location_last_updated"))
    })
    private Location phleboLocation;

    // --- RUNNER ---
    private String runnerId;
    private String runnerName;

    @Enumerated(EnumType.STRING)
    private RunnerStatus runnerStatus;

    private LocalDateTime runnerPickupTime;
    private LocalDateTime runnerDeliveryTime;
    private String runnerNote;

    // Embedded runner location (avoids duplicate last_updated)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "runner_latitude")),
            @AttributeOverride(name = "lng", column = @Column(name = "runner_longitude")),
            @AttributeOverride(name = "lastUpdated", column = @Column(name = "runner_location_last_updated"))
    })
    private Location runnerLocation;

    // --- PAYMENT ---
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String paymentTransactionId;
    private double paymentAmount;
    private LocalDateTime paymentDate;

    // --- TIMESTAMPS ---
    private LocalDateTime bookingCreationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- HISTORY ---
    @ElementCollection
    @CollectionTable(name = "booking_history", joinColumns = @JoinColumn(name = "booking_id"))
    private List<History> history;
}

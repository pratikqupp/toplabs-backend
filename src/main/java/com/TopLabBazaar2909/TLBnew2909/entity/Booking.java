package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.*;
import com.TopLabBazaar2909.TLBnew2909.Enum.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // USER
    private String userId;
    private String mobile;

    // LABS
    @ElementCollection
    @CollectionTable(name = "booking_labs", joinColumns = @JoinColumn(name = "booking_id"))
    private List<LabPrice> labs = new ArrayList<>();

    // TESTS
    @ElementCollection
    @CollectionTable(name = "booking_tests", joinColumns = @JoinColumn(name = "booking_id"))
    private List<TestPrice> tests = new ArrayList<>();

    private LocalDateTime bookingCreationDate = LocalDateTime.now();
    private String patientNote = "";
    private String bookingAddress = "";
    private String postalCode = "";
    private String reportUrl = "";

    private LocalDateTime patientExpectedTime;

    private boolean paymentCollectedByPhlebo = false;
    private double paymentReceivedByPhlebo = 0;

    // CARE HEAD
    private String careHeadId = "";
    private String careHeadName = "";

    @Enumerated(EnumType.STRING)
    private CareHeadStatus careHeadStatus = CareHeadStatus.INTERESTED;

    // CARE MANAGER
    private String careManagerId = "";
    private String careManagerName = "";

    @Enumerated(EnumType.STRING)
    private CareManagerStatus careManagerStatus = CareManagerStatus.ASSIGNED;

    // LAB HEAD
    private String labHeadId = "";
    private String labHeadName = "";

    @Enumerated(EnumType.STRING)
    private LabHeadStatus labHeadStatus = LabHeadStatus.BOOKING_CONFIRM;

    // PHLEBO
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
    private Location phleboLocation = new Location();

    // RUNNER
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
    private Location runnerLocation = new Location();

    // PAYMENT
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    private String paymentTransactionId = "";
    private double paymentAmount = 0;
    private LocalDateTime paymentDate;

    // BOOKING STATUS
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.INTERESTED;

    // HISTORY
    @ElementCollection
    @CollectionTable(name = "booking_history", joinColumns = @JoinColumn(name = "booking_id"))
    private List<History> history = new ArrayList<>();

    // TIMESTAMPS
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void setLeadId(String leadId) {
    }
}

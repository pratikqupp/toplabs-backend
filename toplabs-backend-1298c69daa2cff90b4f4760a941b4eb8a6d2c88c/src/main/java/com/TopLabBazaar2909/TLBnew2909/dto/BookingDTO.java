package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String userId; // Reference to User
    private String id;
    private List<LabPriceDTO> labs;

    private LocalDateTime bookingCreationDate;
    private String patientNote;
    private String bookingAddress;

    private String mobile;

    // Care Head
    private String careHeadId;
    private String careHeadName;
    private String careHeadStatus;

    // Care Manager
    private String careManagerId;
    private String careManagerName;
    private String careManagerStatus;

    // Lab Head
    private String labHeadId;
    private String labHeadName;
    private String labHeadStatus;

    // Phlebo
    private String phleboId;
    private String phleboName;
    private String phleboStatus;
    private LocalDateTime phleboArrivalTime;
    private LocalDateTime phleboCompletionTime;
    private String phleboNote;
    private LocationDTO phleboLocation;

    // Runner
    private String runnerId;
    private String runnerName;
    private String runnerStatus;
    private LocalDateTime runnerPickupTime;
    private LocalDateTime runnerDeliveryTime;
    private String runnerNote;
    private LocationDTO runnerLocation;

    // Payment
    private String paymentStatus;
    private String paymentMethod;
    private String paymentTransactionId;
    private double paymentAmount;
    private LocalDateTime paymentDate;

    // Overall Booking
    private String bookingStatus;

    // History Logs
    private List<HistoryDTO> history;

    private double runnerLatitude;   // <-- this must exist
    private double runnerLongitude;
}

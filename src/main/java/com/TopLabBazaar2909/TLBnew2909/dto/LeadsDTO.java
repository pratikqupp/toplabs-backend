package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.entity.LabPrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadsDTO {
    private String id;
    private String userId;
    //private List<String> labs;
    private String patientNote;
    private String bookingAddress;
    private Double paymentAmount;

    private List<LabPrice> labs;


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

    private List<HistoryDTO> history;
    private Double lat;
    private Double lng;

}

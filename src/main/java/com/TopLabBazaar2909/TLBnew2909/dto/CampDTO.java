package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.InstitutionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampDTO {
    private String institutionName;
    private InstitutionType institutionType;
    private String institutionAddress;
    private String campDescription;

    private LocalDateTime campStartDate;
    private LocalDateTime campEndDate;
    private Integer campStartTimeSecFromMidNight;
    private Integer campEndTimeSecFromMidNight;

    private Double fees;

    private String contactPersonName;
    private Long contactPersonMobileNumber;
}

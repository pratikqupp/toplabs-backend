package com.TopLabBazaar2909.TLBnew2909.resource;

import com.TopLabBazaar2909.TLBnew2909.embedded.InstitutionType;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


import java.time.LocalDateTime;

@Data
public class CampResource extends RepresentationModel<CampResource> {
    private String campId;
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
    private Long uniqueCampNumber;



}

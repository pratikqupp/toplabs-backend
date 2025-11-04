package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.InstitutionType;
import com.TopLabBazaar2909.TLBnew2909.embedded.MappedUserIntoTheCamp;
import com.TopLabBazaar2909.TLBnew2909.model.BaseAuditableDocument;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "camp")
public class Camp extends BaseAuditableDocument {

    @Id
    private String id;

    private String institutionName;
    private InstitutionType institutionType;
    private String institutionAddress;
    private String campDescription;

    private LocalDateTime campStartDate;
    private LocalDateTime campEndDate;
    private Integer campStartTimeSecFromMidNight;
    private Integer campEndTimeSecFromMidNight;

    @ElementCollection
    @CollectionTable(
            name = "camp_mapped_users",
            joinColumns = @JoinColumn(name = "camp_id")
    )
    private List<MappedUserIntoTheCamp> mappedUserIntoTheCampList;

    private Double fees;
    private String contactPersonName;
    private Long contactPersonMobileNumber;
    private Long uniqueCampNumber;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

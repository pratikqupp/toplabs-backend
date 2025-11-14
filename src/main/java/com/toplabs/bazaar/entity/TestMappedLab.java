package com.toplabs.bazaar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestMappedLab {
    @Id
    private String labId;
    private String labName;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
    private String logoS3Path;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}

package com.toplabs.bazaar.entity;

import com.toplabs.bazaar.embedded.SampleTubeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_price")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPrice {
    @Id
    @Column(name = "test_id")
    private String testId;
    private String testName;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
    @ManyToOne
    @JoinColumn(name = "lab_price_id")
    private LabPrice labPrice;
}

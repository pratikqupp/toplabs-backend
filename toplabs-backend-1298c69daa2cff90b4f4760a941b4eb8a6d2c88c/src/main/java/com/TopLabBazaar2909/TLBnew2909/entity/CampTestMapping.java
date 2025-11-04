package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.CampTestPrice;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "camp_test_mapping")
public class CampTestMapping {

    @Id
    private String id;

    private String campId;    // Reference to Camp entity ID
    private String testId;    // Reference to Test entity ID
    private String testName;  // For denormalized display

    @Enumerated(EnumType.STRING)
    private SampleType sampleType;

    private String specimen;
    private String note;

    @Enumerated(EnumType.STRING)
    private SampleTubeType sampleTubeType;

    @ElementCollection
    @CollectionTable(name = "camp_test_prices", joinColumns = @JoinColumn(name = "camp_test_mapping_id"))
    private List<CampTestPrice> testMappedLabs;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

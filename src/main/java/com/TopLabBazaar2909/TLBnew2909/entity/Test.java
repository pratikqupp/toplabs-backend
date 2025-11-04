package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Test {

    @Id
    private String id;
    private String name;
    private SampleType sampleType;
    private String description;

    private Boolean favorite;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;

    @ElementCollection
    @CollectionTable(name = "test_mapped_labs", joinColumns = @JoinColumn(name = "test_id"))
    private List<TestMappedLab> testMappedLabs;
}

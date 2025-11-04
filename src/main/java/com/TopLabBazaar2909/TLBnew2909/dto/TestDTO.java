package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private String labId;
    private String name;
    private SampleType sampleType;
    private String description;
    private Boolean favorite;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
}

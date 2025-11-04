package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import lombok.Data;

@Data
public class UpdateTestDTO {
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
}

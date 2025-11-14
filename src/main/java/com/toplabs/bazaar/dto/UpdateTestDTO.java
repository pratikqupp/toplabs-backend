package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.embedded.SampleTubeType;
import lombok.Data;

@Data
public class UpdateTestDTO {
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;
}

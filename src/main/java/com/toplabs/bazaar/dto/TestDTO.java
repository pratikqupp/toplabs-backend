package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.embedded.SampleTubeType;
import com.toplabs.bazaar.embedded.SampleType;
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

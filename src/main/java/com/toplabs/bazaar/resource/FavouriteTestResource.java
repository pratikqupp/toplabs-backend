package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.embedded.SampleTubeType;
import com.toplabs.bazaar.embedded.SampleType;
import lombok.Data;

@Data
public class FavouriteTestResource {
    private String labId;
    private String logoS3Path;
    private String testId;
    private String testName;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
    private Integer campPrice;
    private SampleType sampleType;
    private String specimen;
    private String note;
    private SampleTubeType sampleTubeType;

}

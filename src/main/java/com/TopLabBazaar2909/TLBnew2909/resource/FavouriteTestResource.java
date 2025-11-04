package com.TopLabBazaar2909.TLBnew2909.resource;


import com.TopLabBazaar2909.TLBnew2909.embedded.SampleTubeType;
import com.TopLabBazaar2909.TLBnew2909.embedded.SampleType;
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

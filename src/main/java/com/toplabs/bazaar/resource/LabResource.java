package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.embedded.LabType;
import com.toplabs.bazaar.entity.TestPrice;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@Data
public class LabResource extends RepresentationModel<LabResource> {
    private String labId;
    private String labName;
    private Boolean active;
    private List<LabType> labTypes;
    private String address;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private String locality;
    private String landmark;
    private Set<String> labCertification;
    private List<TestPrice> testPrices;
    private String logoS3Path;

     /* private Integer labStartTimeSecsFromMidnight;
    private Integer labEndTimeSecsFromMidnight;
    private List<DayOfWeek> applicableDays;*/




}

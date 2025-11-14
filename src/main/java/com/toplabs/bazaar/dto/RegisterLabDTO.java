package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.embedded.LabType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterLabDTO {

    private String labName;
    private List<LabType> labTypes;

    private Boolean active;

    private Set<String> labCertification;

    private String postalCode;
    private String address;
    private String city;
    private String state;
    private String country;
    private String locality;
    private String landmark;
}

package com.toplabs.bazaar.embedded;


import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LabDepartment;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MappedUserIntoTheCamp {
    private String userId;
    private LabDepartment department;
    private AppRole designation;
}

package com.TopLabBazaar2909.TLBnew2909.embedded;


import com.TopLabBazaar2909.TLBnew2909.Enum.AppRole;
import com.TopLabBazaar2909.TLBnew2909.Enum.LabDepartment;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MappedUserIntoTheCamp {
    private String userId;
    private LabDepartment department;
    private AppRole designation;
}

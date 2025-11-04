package com.TopLabBazaar2909.TLBnew2909.embedded;

import com.TopLabBazaar2909.TLBnew2909.Enum.AppRole;
import com.TopLabBazaar2909.TLBnew2909.Enum.LabDepartment;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class AssignedCampUser {
    private String userId;
    private String userName;
    private LabDepartment department;
    private AppRole designation;
    private List<String> performedActions;
}

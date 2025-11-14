package com.toplabs.bazaar.embedded;

import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LabDepartment;

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

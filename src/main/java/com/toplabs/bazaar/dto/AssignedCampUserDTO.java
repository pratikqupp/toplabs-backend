package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LabDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedCampUserDTO {
    private String userId;
    private String userName;
    private LabDepartment department;
    private AppRole designation;
    private String action;
}

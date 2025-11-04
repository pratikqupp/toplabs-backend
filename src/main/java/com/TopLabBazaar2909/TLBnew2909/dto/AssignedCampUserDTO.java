package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.Enum.AppRole;
import com.TopLabBazaar2909.TLBnew2909.Enum.LabDepartment;
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

package com.TopLabBazaar2909.TLBnew2909.resource;


import com.TopLabBazaar2909.TLBnew2909.Enum.AppRole;
import com.TopLabBazaar2909.TLBnew2909.Enum.LabDepartment;
import lombok.Data;

@Data
public class CampUserResource {
    private String userId;
    private String name;
    private LabDepartment department;
    private String mobile;
    private AppRole designation;
}

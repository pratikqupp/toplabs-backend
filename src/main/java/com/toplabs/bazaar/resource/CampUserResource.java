package com.toplabs.bazaar.resource;


import com.toplabs.bazaar.Enum.AppRole;
import com.toplabs.bazaar.Enum.LabDepartment;
import lombok.Data;

@Data
public class CampUserResource {
    private String userId;
    private String name;
    private LabDepartment department;
    private String mobile;
    private AppRole designation;
}

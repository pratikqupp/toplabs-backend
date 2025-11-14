package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private String id;
    private String roleName;        // Role name
    private String description;     // Optional description
    private String departmentId;    // ID of the department
    private String status;        // Optional ID of the status

    public RoleDTO(String id, String roleName, String description) {
    }
}

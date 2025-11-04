package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private String departmentId;  // human-readable ID
    private String name;
    private String status;        // active or inactive

    private List<RoleDTO> roles;  // equivalent of virtual field

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

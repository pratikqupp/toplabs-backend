package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.PositionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Long positionId;
    private String name;

    // Use IDs for related entities
    private String departmentId;
    private String departmentName;

    private String roleId;
    private String roleName;

    private Long reportsToId;
    private String reportsToName;

    private PositionStatus status;

    private String currentUserId;
    private String currentUserName;

    private Long currentAssignmentId;

    private List<Long> assignmentHistoryIds; // List of assignment IDs

    private String mobile;
    private String email;
    private String password;

    public PositionDTO(String s, String s1, String s2, PositionStatus status) {
    }
}

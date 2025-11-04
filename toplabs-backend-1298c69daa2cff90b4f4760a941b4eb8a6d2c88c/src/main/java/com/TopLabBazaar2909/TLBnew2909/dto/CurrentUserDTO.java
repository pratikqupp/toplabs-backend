package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDTO {
    private String userId; // reference to UserTeam
    private String userName;
    private LocalDateTime assignedAt;
    private String assignedBy;
}

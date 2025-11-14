package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private String action;
    private String role;
    private String assignedId;
    private String assignedName;
    private String updatedBy;
    private String reason;
    private LocalDateTime timestamp;
}

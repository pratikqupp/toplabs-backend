package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnmapLabSpecificTestFromCampDTO {
    private String campTestMappingId;
    private String labId;
    private String testId;
}

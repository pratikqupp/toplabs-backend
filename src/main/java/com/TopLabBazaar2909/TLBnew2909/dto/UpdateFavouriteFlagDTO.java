package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFavouriteFlagDTO {
    private String testId; 
    private Boolean favourite;
}

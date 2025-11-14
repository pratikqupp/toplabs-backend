package com.toplabs.bazaar.dto;

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

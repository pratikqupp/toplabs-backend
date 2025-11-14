package com.toplabs.bazaar.dto;

import com.toplabs.bazaar.Enum.LocalCampStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCampWRTCampUserDTO {
    private String searchedPhrase;
    private LocalCampStatus campStatus;
    private String userId;
}

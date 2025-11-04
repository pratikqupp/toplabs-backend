package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.Enum.LocalCampStatus;
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

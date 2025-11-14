package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileDTO {
    private String name;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
}

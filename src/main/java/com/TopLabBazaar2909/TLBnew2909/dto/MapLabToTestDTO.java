package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapLabToTestDTO {
    private String labId;
    private String logoS3Path;
    private String labName;
    private Integer b2bPrice;
    private Integer mrp;
    private Integer mmdPrice;
}

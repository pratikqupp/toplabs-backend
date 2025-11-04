package com.TopLabBazaar2909.TLBnew2909.dto;

import com.TopLabBazaar2909.TLBnew2909.embedded.MappedTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterProfileDTO {
    private String name;

    private String labId;
    private String labName;
    private String logoS3Path;

    private Integer mrp;
    private Integer b2bPrice;
    private Integer mmdPrice;

    private List<MappedTest> mappedTests;
}

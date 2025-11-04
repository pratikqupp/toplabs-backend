package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestProfileMappingRequest {
    private List<String> testIds;
    private List<String> profileIds;
}

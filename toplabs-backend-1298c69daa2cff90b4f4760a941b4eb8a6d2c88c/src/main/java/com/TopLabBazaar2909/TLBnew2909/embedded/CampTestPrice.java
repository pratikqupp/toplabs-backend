package com.TopLabBazaar2909.TLBnew2909.embedded;

import com.TopLabBazaar2909.TLBnew2909.entity.TestMappedLab;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CampTestPrice extends TestMappedLab {
    private Integer campPrice;
    private Boolean isComplementary;
}

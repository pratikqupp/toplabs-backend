package com.toplabs.bazaar.embedded;

import com.toplabs.bazaar.entity.TestMappedLab;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CampTestPrice extends TestMappedLab {
    private Integer campPrice;
    private Boolean isComplementary;
}

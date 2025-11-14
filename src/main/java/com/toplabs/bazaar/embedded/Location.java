package com.toplabs.bazaar.embedded;

import jakarta.persistence.Embeddable;
import lombok.Data;


import java.time.LocalDateTime;
@Embeddable
@Data
public class Location {
    private Double lat;
    private Double lng;
    private LocalDateTime lastUpdated;
}

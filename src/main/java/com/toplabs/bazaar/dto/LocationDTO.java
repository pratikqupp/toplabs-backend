package com.toplabs.bazaar.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LocationDTO {
    private double lat;
    private double lng;
    private LocalDateTime lastUpdated;
}

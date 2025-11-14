package com.toplabs.bazaar.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class PhleboLocation {
    private String locationName;
    private Double latitude;
    private Double longitude;
}

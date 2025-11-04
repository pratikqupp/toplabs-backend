package com.TopLabBazaar2909.TLBnew2909.entity;

import com.TopLabBazaar2909.TLBnew2909.embedded.LabType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lab {
    @Id
    private String id;

    private String labName;
    private List<LabType> labTypes;

    private Boolean active;

    private Set<String> labCertification;
    private String postalCode;
    private String address;
    private String city;
    private String state;
    private String country;
    private String locality;
    private String landmark;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "testid")
    private List<TestPrice> testPrices;
    private String logoS3Path;


}

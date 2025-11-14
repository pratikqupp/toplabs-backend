package com.toplabs.bazaar.entity;

//import com.TopLabBazaar2909.TLBnew2909.embadded.TestPrice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lab_prices")

public class LabPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Primary key for entity

    @Column(name = "total_mmd_price")
    private double totalMmdPrice;

    @Column(name = "total_mrp")
    private double totalMrp;

    @Column(name = "lab_id", nullable = false)
    private String labId;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "logo_s3_path")
    private String logoS3Path;


    @OneToMany(mappedBy = "labPrice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TestPrice> testPrices;

    @ManyToOne
    @JoinColumn(name = "leads_id") // foreign key column
    private Leads lead;
}

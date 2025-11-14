package com.toplabs.bazaar.entity;

import com.toplabs.bazaar.embedded.MappedTest;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestProfile {

   @Id
   private String id;
   private String name;
   private String labId;
   private String labName;
   private Integer b2bPrice;
   private Integer mrp;
   private Integer mmdPrice;
   private String logoS3Path;

   @ElementCollection
   @CollectionTable(name = "test_profile_mapped_tests", joinColumns = @JoinColumn(name = "test_profile_id"))
   private List<MappedTest> mappedTests;
}

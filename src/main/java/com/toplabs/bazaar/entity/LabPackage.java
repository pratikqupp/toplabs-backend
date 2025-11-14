package com.toplabs.bazaar.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;



import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LabPackage {
  @Id
  private String id;
  private String name;
  private String categoryId;
  private String labId;
  private List<String> testIds;
  private List<String> profileIds;
  private Integer b2bPrice;
  private Integer mrp;
  private Integer mmdPrice;
}

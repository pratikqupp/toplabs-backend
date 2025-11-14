package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabPackageDTO {
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

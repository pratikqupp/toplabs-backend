package com.TopLabBazaar2909.TLBnew2909.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
  @Id
  private String id;
  private String name;
  private String description;
}

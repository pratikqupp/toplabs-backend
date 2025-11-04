package com.TopLabBazaar2909.TLBnew2909.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private String address;
    private String postalCode;
    private String gender;

    private LocalDate dateOfBirth;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();
}

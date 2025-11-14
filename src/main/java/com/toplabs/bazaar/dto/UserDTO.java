package com.toplabs.bazaar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;

    private String firstName;   // required
    private String lastName;    // required

    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private Integer postalCode;

    private LocalDateTime createdAt;   // corresponds to timestamps: true
    private LocalDateTime updatedAt;
}

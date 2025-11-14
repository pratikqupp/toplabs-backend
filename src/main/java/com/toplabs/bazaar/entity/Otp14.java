package com.toplabs.bazaar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp17")
public class Otp14 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String otpCode;

    private LocalDateTime expiry;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "user_id3")
    private AppUser user;
}

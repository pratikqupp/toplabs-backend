package com.TopLabBazaar2909.TLBnew2909.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {
    private String mobileNumber;
    private String otpCode;
}

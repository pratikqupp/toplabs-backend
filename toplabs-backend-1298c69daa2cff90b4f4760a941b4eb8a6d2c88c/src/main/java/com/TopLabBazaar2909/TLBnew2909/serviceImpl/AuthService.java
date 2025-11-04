package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.dto.JwtResponse;
import com.TopLabBazaar2909.TLBnew2909.dto.OtpRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SigninRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SignupRequest;

import java.util.Map;

public interface AuthService {
    String signup(SignupRequest request);

    String signin(SigninRequest request);

    JwtResponse verifyOtp(OtpRequest request);

    String regenerateOtp(String mobileNumber);

    void logout(String token);

    Map<String, String> refreshAccessToken(String refreshToken);
}

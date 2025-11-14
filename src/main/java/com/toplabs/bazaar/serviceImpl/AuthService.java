package com.toplabs.bazaar.serviceImpl;

import com.toplabs.bazaar.dto.JwtResponse;
import com.toplabs.bazaar.dto.OtpRequest;
import com.toplabs.bazaar.dto.SigninRequest;
import com.toplabs.bazaar.dto.SignupRequest;

import java.util.Map;

public interface AuthService {
    String signup(SignupRequest request);

    String signin(SigninRequest request);

    JwtResponse verifyOtp(OtpRequest request);

    String regenerateOtp(String mobileNumber);

    void logout(String token);

    Map<String, String> refreshAccessToken(String refreshToken);
}

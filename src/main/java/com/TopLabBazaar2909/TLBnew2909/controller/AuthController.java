package com.TopLabBazaar2909.TLBnew2909.controller;

import com.TopLabBazaar2909.TLBnew2909.ServiceIntr.UserService;
import com.TopLabBazaar2909.TLBnew2909.dto.JwtResponse;
import com.TopLabBazaar2909.TLBnew2909.dto.OtpRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SigninRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SignupRequest;
import com.TopLabBazaar2909.TLBnew2909.serviceImpl.AuthService;
import com.TopLabBazaar2909.TLBnew2909.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/signin")
    public String signin(@RequestBody SigninRequest request) {
        return authService.signin(request);
    }

    @PostMapping("/verify-otp")
    public JwtResponse verifyOtp(@RequestBody OtpRequest request) {
        return authService.verifyOtp(request);
    }

    @PostMapping("/regenerate-otp")
    public String regenerateOtp(@RequestParam String mobileNumber) {
        return authService.regenerateOtp(mobileNumber);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.logout(token);
            return "Logged out successfully";
        }
        return "Invalid token";
    }

    // ---------------- REFRESH TOKEN FLOW ----------------
    @PostMapping("/oauth/token")
    public JwtResponse refreshToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("refresh_token") String refreshToken,
            @RequestHeader("Authorization") String authHeader
    ) {
        if (!"refresh_token".equals(grantType)) {
            throw new RuntimeException("Unsupported grant_type");
        }

        // Validate Basic auth header
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String base64Credentials = authHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            throw new RuntimeException("Invalid Authorization header format");
        }

        String clientId = values[0];
        String clientSecret = values[1];

        // TODO: Replace with your real validation logic (configurable)
        if (!"app_id".equals(clientId) || !"secret".equals(clientSecret)) {
            throw new RuntimeException("Invalid client credentials");
        }

        // Delegate to AuthService
        return (JwtResponse) authService.refreshAccessToken(refreshToken);
    }
}

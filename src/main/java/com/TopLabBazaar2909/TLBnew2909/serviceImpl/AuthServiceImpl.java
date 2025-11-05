package com.TopLabBazaar2909.TLBnew2909.serviceImpl;

import com.TopLabBazaar2909.TLBnew2909.dto.JwtResponse;
import com.TopLabBazaar2909.TLBnew2909.dto.OtpRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SigninRequest;
import com.TopLabBazaar2909.TLBnew2909.dto.SignupRequest;
import com.TopLabBazaar2909.TLBnew2909.entity.Otp14;
import com.TopLabBazaar2909.TLBnew2909.entity.Position;
import com.TopLabBazaar2909.TLBnew2909.entity.Role22;
import com.TopLabBazaar2909.TLBnew2909.entity.AppUser;
import com.TopLabBazaar2909.TLBnew2909.repository.OtpRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.PositionRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.RoleRepository;
import com.TopLabBazaar2909.TLBnew2909.repository.UserRepository;
import com.TopLabBazaar2909.TLBnew2909.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;

    private final OtpRepository otpRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private final Random random = new Random();

    private final String BASIC_USERNAME = "appuser";
    private final String BASIC_PASSWORD = "password123";

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PositionRepository positionRepository, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.positionRepository = positionRepository;
        this.otpRepository = otpRepository;
    }

    @Override
    public String signup(SignupRequest request) {
        //  Check if user already exists by phone
        if (userRepository.findByMobileNumber(request.getMobile()).isPresent()) {
            return "User already exists. Please login with OTP.";
        }

        //  Create new user
        AppUser user = new AppUser();

        //  Manually set ID
        if (request.getId() != null && !request.getId().isEmpty()) {
            user.setId(request.getId());
        } else {
            throw new RuntimeException("User ID is required since it's manually assigned.");
        }

        // Set basic info
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : "Unknown");
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobile());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPostalCode(request.getPostalCode());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        //  Assign Role
        Role22 role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Invalid Role ID"));
        user.setRole(role);

        //  Assign Position
        if (request.getPositionId() != null && !request.getPositionId().isEmpty()) {
            Position position = positionRepository.findById(Long.valueOf(request.getPositionId()))
                    .orElseThrow(() -> new RuntimeException("Invalid Position ID"));
            user.setPosition(position);
        }

        //Save user
        userRepository.save(user);

        //  Return OTP or success message
        return generateOtpForUser(user);
    }


    @Override
    public String signin(SigninRequest request) {
        AppUser user = userRepository.findByMobileNumber((request.getMobileNumber()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return generateOtpForUser(user);
    }

    @Override
    public JwtResponse verifyOtp(OtpRequest request) {
        AppUser user = userRepository.findByMobileNumber((request.getMobileNumber()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Otp14 otp = otpRepository.findFirstByUserAndUsedFalseOrderByExpiryDesc(user)
                .orElseThrow(() -> new RuntimeException("No active OTP found"));

        if (otp.getOtpCode().equals(request.getOtpCode()) && otp.getExpiry().isAfter(LocalDateTime.now())) {
            otp.setUsed(true);
            otpRepository.save(otp);

            // Generate tokens using user ID (not mobile number)
            String accessToken = jwtUtil.generateAccessToken(
                    String.valueOf(user.getId()),
                    user.getRole().getRoleName()

            );
            String refreshToken = jwtUtil.generateRefreshToken(
                    String.valueOf(user.getId()),
                    user.getRole().getRoleName()
            );

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid or expired OTP");
        }
    }

    @Override
    public String regenerateOtp(String mobileNumber) {
        AppUser user = userRepository.findByMobileNumber((mobileNumber))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return generateOtpForUser(user);
    }

    @Override
    public void logout(String token) {
        jwtUtil.blacklistToken(token);
    }

    @Override
    public Map<String, String> refreshAccessToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid or blacklisted refresh token");
        }

        if (jwtUtil.isTokenExpired(refreshToken)) {
            jwtUtil.blacklistToken(refreshToken);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Provided token is not a refresh token");
        }

        String userId = jwtUtil.getUsernameFromToken(refreshToken);
        String role = jwtUtil.getRoleFromToken(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(userId, role);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId, role);

        jwtUtil.blacklistToken(refreshToken);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    private String generateOtpForUser(AppUser user) {
        otpRepository.findByUserAndUsedFalse(user).forEach(o -> {
            o.setUsed(true);
            otpRepository.save(o);
        });

        String otpCode = String.format("%06d", random.nextInt(999999));
        Otp14 otp = new Otp14();
        otp.setOtpCode(otpCode);
        otp.setUser(user);
        otp.setExpiry(LocalDateTime.now().plusMinutes(10));
        otpRepository.save(otp);

        // Log safely (without exposing OTP)
        log.info("New OTP generated for user with mobile: {}", user.getMobileNumber());

        //  return OTP (so you can send via SMS/Email service)
        return otpCode;
    }
}

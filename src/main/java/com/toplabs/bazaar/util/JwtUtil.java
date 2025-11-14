package com.toplabs.bazaar.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    // 🔐 Secret key (must be >= 32 characters)
    private final String secret = "MySuperSecureSecretKeyForHS256Algorithm123!";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    // ⏰ Expiration times
    private final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000;
    private final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000; // 7 days

    // 🧾 Blacklist for logged-out/invalidated tokens
    private final Set<String> blacklist = new HashSet<>();

    // ----------------------------------------------------
    // ✅ Generate Access Token
    // ----------------------------------------------------
    public String generateAccessToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ----------------------------------------------------
    // ✅ Generate Refresh Token
    // ----------------------------------------------------
    public String generateRefreshToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ----------------------------------------------------
    // ✅ Extract All Claims (NEW)
    // ----------------------------------------------------
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    // ----------------------------------------------------
    // ✅ Validate Token (IMPROVED)
    // ----------------------------------------------------
    public boolean validateToken(String token) {
        if (token == null || token.trim().isEmpty()) return false;
        if (blacklist.contains(token)) return false;

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token is null or empty: " + e.getMessage());
        }
        return false;
    }

    // ----------------------------------------------------
    // ✅ Check Expiration
    // ----------------------------------------------------
    public boolean isTokenExpired(String token) {
        try {
            Date expiry = getExpirationDateFromToken(token);
            return expiry.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    // ----------------------------------------------------
    // ✅ Check if Token is Refresh Token
    // ----------------------------------------------------
    public boolean isRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String type = claims.get("type", String.class);
            return "refresh".equals(type);
        } catch (Exception e) {
            return false;
        }
    }

    // ----------------------------------------------------
    // ✅ Extract Specific Claims
    // ----------------------------------------------------
    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ----------------------------------------------------
    // ✅ Blacklist Operations
    // ----------------------------------------------------
    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

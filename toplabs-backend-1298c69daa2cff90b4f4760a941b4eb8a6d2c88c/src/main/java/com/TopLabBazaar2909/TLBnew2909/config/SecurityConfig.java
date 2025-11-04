package com.TopLabBazaar2909.TLBnew2909.config;

import com.TopLabBazaar2909.TLBnew2909.filter.JwtFilter;
import com.TopLabBazaar2909.TLBnew2909.filter.RateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private RateLimitFilter rateLimitFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/signin",
                                "/api/auth/verify-otp",
                                "/api/auth/regenerate-otp",
                                "/api/auth/refresh-token"   // allow refresh token API without JWT
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // Enable Basic Auth (for refresh token requests)
                .httpBasic(httpBasic -> {})
                // JWT = stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Apply filters
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(rateLimitFilter, JwtFilter.class);

        return http.build();
    }

    // In-memory client credentials for Basic Auth (app_id / secret)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails client = User.builder()
                .username("app_id")   // same as CLIENT_ID
                .password(passwordEncoder().encode("secret")) // same as CLIENT_SECRET
                .roles("CLIENT")
                .build();

        return new InMemoryUserDetailsManager(client);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

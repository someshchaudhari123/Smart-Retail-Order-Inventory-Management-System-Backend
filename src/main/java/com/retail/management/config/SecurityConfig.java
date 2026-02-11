package com.retail.management.config;

import com.retail.management.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // enable cors bean
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // -------- PUBLIC --------
                        .requestMatchers("/auth/**").permitAll()

                        // -------- PRODUCTS --------
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/products/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/products/**")
                        .hasRole("ADMIN")

                        // -------- CATEGORIES --------
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/categories/**")
                        .hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/categories/**")
                        .hasRole("ADMIN")

                        // -------- ORDERS --------
                        .requestMatchers("/orders/**")
                        .hasAnyRole("ADMIN", "USER")

                        // -------- REPORTS --------
                        .requestMatchers("/reports/**")
                        .hasRole("ADMIN")

                        // EVERYTHING ELSE
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

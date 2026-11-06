package com.library.library_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disabled for easy testing in Postman
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll() // Allowing all requests for now so everything runs smoothly
//                )
//                .httpBasic(withDefaults());
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable()) // Disabled for Postman testing
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/books/**").permitAll() // Anyone can view/search books!
                    .anyRequest().authenticated() // Everything else (like issuing books/members) requires a login!
            )
            .httpBasic(withDefaults()); // Uses basic auth for now

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encodes passwords securely using BCrypt
    }
}
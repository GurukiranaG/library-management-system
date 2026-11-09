package com.library.library_management_system.config;

import com.library.library_management_system.entity.User;
import com.library.library_management_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String targetUsername = "admin";
            String rawPassword = "password123";

            // Find existing user or create a new one
            User user = userRepository.findByUsername(targetUsername).orElse(new User());

            user.setUsername(targetUsername);
            user.setPassword(passwordEncoder.encode(rawPassword)); // Encrypts "password123"
            user.setRole("ROLE_ADMIN");

            userRepository.save(user);
            System.out.println(">>> Test user ready: username = " + targetUsername + ", password = " + rawPassword);
        };
    }
}
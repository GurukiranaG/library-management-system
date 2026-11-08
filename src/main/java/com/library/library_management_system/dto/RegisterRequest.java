package com.library.library_management_system.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // e.g., "ROLE_USER" or "ROLE_ADMIN"
}

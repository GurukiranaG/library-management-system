package com.library.library_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberRequestDTO {
    @NotBlank(message = "Name should not blank")
    private String name;
    @NotBlank(message = "Email should not blank")
    @Email(message ="Email must be valid")
    private String email;
    @NotBlank(message = "PhoneNumber should not blank")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

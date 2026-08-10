package com.library.library_management_system.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Welcome to Library Management System  Gurukiran ";
    }

}

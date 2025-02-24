package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User registeredUser = authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully: " + registeredUser.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        String result = authService.loginUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody User user) {
        String result = authService.logoutUser(user.getUsername());
        return ResponseEntity.ok(result);
    }
}

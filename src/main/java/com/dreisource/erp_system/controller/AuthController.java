package com.dreisource.erp_system.controller;


import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.payload.LoginRequest;
import com.dreisource.erp_system.payload.RefreshTokenRequest;
import com.dreisource.erp_system.payload.JwtResponse;
import com.dreisource.erp_system.payload.MessageResponse;
import com.dreisource.erp_system.security.JwtTokenProvider;
import com.dreisource.erp_system.service.RefreshTokenService;
import com.dreisource.erp_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          RefreshTokenService refreshTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    // 🔹 Login API - Returns JWT & Refresh Token
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Extract Username from Authentication Object
        String username = authentication.getName();

        // ✅ FIXED: Fetch User ID from Database
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtTokenProvider.generateToken(username);
        String refreshToken = refreshTokenService.createRefreshToken(user.getId()); // ✅ FIXED: Pass Long userId

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken));
    }

    // 🔹 Refresh Token API
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newToken = refreshTokenService.validateAndGenerateNewToken(request.getRefreshToken());
        return ResponseEntity.ok(new JwtResponse(newToken, request.getRefreshToken()));
    }

    // 🔹 Register API (For new user signup)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
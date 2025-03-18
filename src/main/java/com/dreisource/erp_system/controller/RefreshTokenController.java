package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh-access-token") // ✅ Renamed to avoid conflict
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        if (refreshTokenService.validateRefreshToken(refreshToken)) {
            String newAccessToken = refreshTokenService.validateAndGenerateNewToken(refreshToken);
            return ResponseEntity.ok(newAccessToken);
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired refresh token.");
        }
    }
}
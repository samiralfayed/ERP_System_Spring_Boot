package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.RefreshToken;
import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.repository.RefreshTokenRepository;
import com.dreisource.erp_system.repository.UserRepository;
import com.dreisource.erp_system.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refreshExpiration}")
    private Long refreshExpirationMs;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserRepository userRepository,
                               JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ✅ Create a new refresh token
    public String createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = new RefreshToken(
                user, UUID.randomUUID().toString(), Instant.now().plusMillis(refreshExpirationMs)
        );

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    // ✅ Validate Refresh Token (Check if expired)
    public boolean validateRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);

        if (refreshTokenOpt.isPresent()) {
            RefreshToken storedToken = refreshTokenOpt.get();
            return storedToken.getExpiryDate().isAfter(Instant.now());
        }
        return false;
    }

    // ✅ Validate and generate a new access token using refresh token
    public String validateAndGenerateNewToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(refreshToken);

        if (refreshTokenOpt.isPresent()) {
            RefreshToken storedToken = refreshTokenOpt.get();
            if (storedToken.getExpiryDate().isAfter(Instant.now())) {
                return jwtTokenProvider.generateToken(storedToken.getUser().getUsername());
            } else {
                throw new RuntimeException("Refresh token has expired. Please log in again.");
            }
        }
        throw new RuntimeException("Invalid refresh token");
    }
}
package com.dreisource.erp_system.serviceimpl;

import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.repository.UserRepository;
import com.dreisource.erp_system.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User registerUser(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String loginUser(String username, String password) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            return "Login successful for user: " + username;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public String logoutUser(String username) {
        // Logout logic (handled in JWT by token invalidation)
        return "Logout successful for user: " + username;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        // Call the instance method on the userRepository
        return userRepository.findByUsername(username);
    }
}
package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Find user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ✅ Check if username exists
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // ✅ Save user with encrypted password
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔹 Encrypt password before saving
        return userRepository.save(user);
    }
}
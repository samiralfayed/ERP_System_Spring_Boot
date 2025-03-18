package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.User;
import java.util.Optional;

public interface AuthService {  // Ensure method signatures match the implementation

    User registerUser(User user);  // Correct method

    String loginUser(String username, String password);  // Correct method

    String logoutUser(String username);  // Correct method

    Optional<User> findUserByUsername(String username);  // Correct method
}
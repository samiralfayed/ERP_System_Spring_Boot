package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.User;
import java.util.Optional;

public interface AuthService {
    User registerUser(User user);
    String loginUser(String username, String password);
    String logoutUser(String username);
    Optional<User> findUserByUsername(String username);
}

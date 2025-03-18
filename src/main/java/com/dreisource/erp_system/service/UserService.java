package com.dreisource.erp_system.service;

import com.dreisource.erp_system.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User findById(Long id);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    User updateUser(Long id, User updatedUser);
    boolean deleteUserById(Long id);
    void deleteAllUsers();

    // ✅ Add missing saveUser method
    User saveUser(User user);
}
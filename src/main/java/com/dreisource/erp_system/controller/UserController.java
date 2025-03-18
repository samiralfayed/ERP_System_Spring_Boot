package com.dreisource.erp_system.controller;

import com.dreisource.erp_system.model.User;
import com.dreisource.erp_system.payload.MessageResponse;
import com.dreisource.erp_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ 1. Create User (POST)
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    // ✅ 2. Get All Users (GET)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ 3. Get User by ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findById(id); // Change getUserById to findById
        return ResponseEntity.ok(user);
    }

    // ✅ 4. Update User (PUT /{id})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // ✅ 5. Delete User by ID (DELETE /{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if (!deleted) {
            return ResponseEntity.status(404).body(new MessageResponse("User not found"));
        }
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    // ✅ 6. Delete All Users (DELETE /)
    @DeleteMapping
    public ResponseEntity<?> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok(new MessageResponse("All users deleted successfully"));
    }
}
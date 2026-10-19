package com.example.mealmate.service;

import com.example.mealmate.entity.User;
import com.example.mealmate.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create User
    public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
}

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Filter users by name
public List<User> filterUsersByName(String name) {
    return userRepository.findByNameContainingIgnoreCase(name);
}

    // Get User by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update User
    public User updateUser(Long id, User updatedUser) {

    User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    existingUser.setName(updatedUser.getName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPassword(
            passwordEncoder.encode(updatedUser.getPassword())
    );
    existingUser.setPhone(updatedUser.getPhone());
    existingUser.setAddress(updatedUser.getAddress());

    return userRepository.save(existingUser);
}

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
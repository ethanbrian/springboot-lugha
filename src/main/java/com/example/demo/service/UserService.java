package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

// UserService.java (Service)
public interface UserService {
    void registerUser(UserDTO userDTO);
    User loginUser(UserDTO userDTO); // Change the return type to User
    User getUserByEmail(String email); // New method to retrieve user by email
}

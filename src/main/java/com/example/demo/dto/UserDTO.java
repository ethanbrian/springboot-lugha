package com.example.demo.dto;

public class UserDTO {
    private String email;
    private String password;
    private UserRole role;
    private String avatar; // Add the avatar field

    // Constructors, getters, and setters

    public UserDTO() {
        // Default constructor
    }

    public UserDTO(String email, String password, UserRole role, String avatar) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

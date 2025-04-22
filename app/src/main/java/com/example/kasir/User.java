package com.example.kasir;

public class User{
    private String email;
    private String role;
    private String status;

    public User() {
        // Empty constructor needed for Firebase
    }

    public User(String email, String role, String status) {
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
}
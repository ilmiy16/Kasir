package com.example.kasir;

public class Kasir {
    private String email;
    private String role;
    private String status;

    // Konstruktor kosong wajib untuk Firebase
    public Kasir() {}

    public Kasir(String email, String role, String status) {
        this.email = email;
        this.role = role;
        this.status = status;
    }

    // Getter dan Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

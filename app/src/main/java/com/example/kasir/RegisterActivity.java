package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Firebase Auth dan Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Inisialisasi View
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Tombol Register
        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email harus diisi!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password harus diisi!");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter!");
            return;
        }

        // Proses Registrasi di Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            saveUserToDatabase(userId, email);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToDatabase(String userId, String email) {
        User user = new User(email, "User"); // Default role "User"

        mDatabase.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, AdminMenuMakananActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Gagal menyimpan data: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Class model untuk menyimpan data pengguna
    public static class User {
        public String email, role;

        public User() {
        }

        public User(String email, String role) {
            this.email = email;
            this.role = role;
        }
    }
}

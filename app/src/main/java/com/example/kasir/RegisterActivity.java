package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnRegister;
    private TextView tvSudahPunyaAkun;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi Firebase Database dengan URL eksplisit
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://jajanan-khadijah-eb1ab-default-rtdb.asia-southeast1.firebasedatabase.app");
        mDatabase = database.getReference("Users");

        // Inisialisasi View
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvSudahPunyaAkun = findViewById(R.id.tvSudahPunyaAkun); // <-- Tambahan penting

        // Listener tombol register
        btnRegister.setOnClickListener(view -> registerUser());

        // Listener teks "Sudah punya akun?"
        tvSudahPunyaAkun.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginKasirActivity.class);
            startActivity(intent);
            finish(); // Tutup activity registrasi
        });
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

        // Registrasi dengan Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Registrasi sukses.");
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            saveUserToDatabase(userId, email);
                        }
                    } else {
                        Log.e(TAG, "Registrasi gagal: ", task.getException());
                        Toast.makeText(this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToDatabase(String userId, String email) {
        User user = new User(email, "User"); // Role default: User

        Log.d(TAG, "Menyimpan user ke database...");

        mDatabase.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User berhasil disimpan di Realtime Database.");
                        Toast.makeText(this, "Registrasi Berhasil! Silakan login.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginKasirActivity.class));
                        finish();
                    } else {
                        Log.e(TAG, "Gagal menyimpan user: ", task.getException());
                        Toast.makeText(this, "Gagal menyimpan data: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Class model User
    public static class User {
        public String email;
        public String role;

        public User() {
            // Diperlukan oleh Firebase
        }

        public User(String email, String role) {
            this.email = email;
            this.role = role;
        }
    }
}
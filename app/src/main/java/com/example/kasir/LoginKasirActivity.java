package com.example.kasir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasir.AdminMenuMakananActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginKasirActivity extends AppCompatActivity {

    private EditText etKasirId, etPassword;
    private Button btnLogin;
    private ImageButton btnBack;
    private TextView tvForgotPassword, tvHello, tvSubHello;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kasir);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("kasir");

        // Initialize UI components
        etKasirId = findViewById(R.id.etKasirId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvHello = findViewById(R.id.tvHello);
        tvSubHello = findViewById(R.id.tvSubHello);

        // Setup click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginKasir();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to previous activity
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password
                showForgotPasswordDialog();
            }
        });
    }

    private void loginKasir() {
        // Get input values
        final String kasirId = etKasirId.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(kasirId)) {
            etKasirId.setError("ID Kasir tidak boleh kosong");
            etKasirId.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }

        // Show progress
        Toast.makeText(LoginKasirActivity.this, "Sedang memproses...", Toast.LENGTH_SHORT).show();

        // Check if kasir exists in database
        mDatabase.child(kasirId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Kasir exists, get email to authenticate
                    String email = dataSnapshot.child("email").getValue(String.class);

                    // Authenticate with Firebase Auth
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginKasirActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login successful
                                        Toast.makeText(LoginKasirActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                                        // Navigate to kasir dashboard
                                        Intent intent = new Intent(LoginKasirActivity.this, AdminMenuMakananActivity.class);
                                        intent.putExtra("KASIR_ID", kasirId);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If login fails, display a message to the user
                                        Toast.makeText(LoginKasirActivity.this, "Password salah",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Kasir ID not found
                    Toast.makeText(LoginKasirActivity.this, "ID Kasir tidak ditemukan",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginKasirActivity.this, "Error: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showForgotPasswordDialog() {
        // You can implement a forgot password dialog or start a new activity
        Toast.makeText(this, "Silahkan hubungi admin untuk reset password", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already signed in
        if (mAuth.getCurrentUser() != null) {
            // Check if the current user is a kasir
            String uid = mAuth.getCurrentUser().getUid();
            checkKasirRole(uid);
        }
    }

    private void checkKasirRole(String uid) {
        DatabaseReference userRoleRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("role");
        userRoleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && "kasir".equals(dataSnapshot.getValue(String.class))) {
                    // User is already logged in as kasir, redirect to kasir dashboard
                    Intent intent = new Intent(LoginKasirActivity.this, AdminMenuMakananActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // User is logged in but not as kasir, sign them out
                    mAuth.signOut();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginKasirActivity.this, "Error: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
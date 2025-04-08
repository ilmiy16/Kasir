package com.example.kasir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginKasirActivity extends AppCompatActivity {

    private EditText etKasirId, etPassword;
    private Button btnLogin;
    private ImageButton btnBack;
    private TextView tvRegister, tvForgotPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kasir);

        // Initialize Firebase Auth & Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("kasir");

        // Initialize UI components
        etKasirId = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        tvRegister = findViewById(R.id.tvRegistrasi);

        // Setup Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang memproses...");
        progressDialog.setCancelable(false);

        // Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String role = getIntent().getStringExtra("ROLE");
                loginKasir(role);
            }
        });

        // Back button click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke halaman sebelumnya
            }
        });


        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginKasirActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void loginKasir(String role) {

        Log.d("Khadija-Role", role);
        Log.d("Khadija-Role", String.valueOf(Objects.equals(role, "kasir")));


        if (Objects.equals(role, "kasir")) {
            Intent intent = new Intent(LoginKasirActivity.this, MainMenuKasir.class);
            intent.putExtra("KASIR_ID", 12345);
            startActivity(intent);

            finish();
        }

        final String kasirId = etKasirId.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

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

        // Show progress dialog
        progressDialog.show();

        // Cek apakah ID kasir tersedia di database
        mDatabase.child(kasirId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String email = dataSnapshot.child("email").getValue(String.class);

                    if (email == null || email.isEmpty()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginKasirActivity.this, "Email tidak ditemukan untuk ID Kasir ini", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Login dengan FirebaseAuth
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginKasirActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginKasirActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginKasirActivity.this, AdminMenuMakananActivity.class);
                                        intent.putExtra("KASIR_ID", kasirId);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginKasirActivity.this, "Password salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginKasirActivity.this, "ID Kasir tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(LoginKasirActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showForgotPasswordDialog() {
        Toast.makeText(this, "Silahkan hubungi admin untuk reset password", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
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
                    Intent intent = new Intent(LoginKasirActivity.this, MainMenuKasir.class);
                    startActivity(intent);
                    finish();
                } else {
                    mAuth.signOut();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginKasirActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.kasir;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import androidx.core.content.ContextCompat;

public class editmenu extends Activity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgMenu, imgProfile;
    private EditText edtNamaMenu, edtHargaMenu;
    private Button btnUbahFoto, btnSimpan;
    private ImageButton btnBack; // Tambahan tombol back
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        imgMenu = findViewById(R.id.imgMenu);
        imgProfile = findViewById(R.id.imgProfile);
        edtNamaMenu = findViewById(R.id.edtNamaMenu);
        edtHargaMenu = findViewById(R.id.edtHargaMenu);
        btnUbahFoto = findViewById(R.id.btnUbahFoto);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack); // Inisialisasi tombol back

        // Ubah warna icon profile jadi putih
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

        // Tombol ubah foto lonjong
        btnUbahFoto.setBackgroundResource(R.drawable.rounded_button);

        // Aksi tombol ubah foto
        btnUbahFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        // Aksi tombol simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanPerubahan();
            }
        });

        // Aksi tombol kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke activity sebelumnya
            }
        });
    }

    private void pilihGambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgMenu.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void simpanPerubahan() {
        String namaMenu = edtNamaMenu.getText().toString().trim();
        String hargaMenu = edtHargaMenu.getText().toString().trim();

        if (namaMenu.isEmpty() || hargaMenu.isEmpty()) {
            Toast.makeText(this, "Nama dan Harga Menu harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Perubahan disimpan!", Toast.LENGTH_SHORT).show();
    }
}

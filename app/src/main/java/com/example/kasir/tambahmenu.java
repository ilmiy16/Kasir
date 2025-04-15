package com.example.kasir;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;


import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class tambahmenu extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgPreview;
    private Button btnUploadImage, btnSimpanMenu, btnMakanan, btnMinuman;
    private EditText etNamaMenu, etHarga, etDeskripsi;
    private TextView tvKategori;

    private String kategori = "Makanan";
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);

        // Inisialisasi view
        imgPreview = findViewById(R.id.imgPreview);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnSimpanMenu = findViewById(R.id.btnSimpanMenu);
        btnMakanan = findViewById(R.id.btnMakanan);
        btnMinuman = findViewById(R.id.btnMinuman);
        etNamaMenu = findViewById(R.id.etNamaMenu);
        etHarga = findViewById(R.id.etHarga);
        tvKategori = findViewById(R.id.tvKategori);

        // Menerima kategori yang dikirim dari AdminMenuMinumanActivity
        String kategoriDariIntent = getIntent().getStringExtra("kategori");
        if (kategoriDariIntent != null) {
            kategori = kategoriDariIntent;
            tvKategori.setText("Kategori: " + kategori);
        }

        // Pilih Gambar
        btnUploadImage.setOnClickListener(v -> openFileChooser());

        // Tab kategori: Makanan
        btnMakanan.setOnClickListener(v -> {
            kategori = "Makanan";
            tvKategori.setText("Kategori: Makanan");

            // Warna dan state tombol
            btnMakanan.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.redd));
            btnMakanan.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            btnMakanan.setEnabled(false);

            btnMinuman.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.abumuda));
            btnMinuman.setTextColor(ContextCompat.getColor(this, R.color.black));
            btnMinuman.setEnabled(true);
        });

        // Tab kategori: Minuman
        btnMinuman.setOnClickListener(v -> {
            kategori = "Minuman";
            tvKategori.setText("Kategori: Minuman");

            // Warna dan state tombol
            btnMinuman.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.redd));
            btnMinuman.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            btnMinuman.setEnabled(false);

            btnMakanan.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.abumuda));
            btnMakanan.setTextColor(ContextCompat.getColor(this, R.color.black));
            btnMakanan.setEnabled(true);
        });

        // Simpan Menu
        btnSimpanMenu.setOnClickListener(v -> {
            String nama = etNamaMenu.getText().toString().trim();
            String harga = etHarga.getText().toString().trim();
            String deskripsi = etDeskripsi.getText().toString().trim();

            if (nama.isEmpty() || harga.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Lengkapi semua data termasuk gambar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan ke database (Firebase / SQLite / lainnya)
            Log.d("TambahMenu", "Nama: " + nama);
            Log.d("TambahMenu", "Harga: " + harga);
            Log.d("TambahMenu", "Deskripsi: " + deskripsi);
            Log.d("TambahMenu", "Kategori: " + kategori);
            Log.d("TambahMenu", "Image URI: " + imageUri.toString());

            Toast.makeText(this, "Menu berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
            finish(); // tutup activity
        });

        // Set default kategori
        if ("Minuman".equals(kategori)) {
            btnMinuman.performClick(); // Trigger klik Minuman
        } else {
            btnMakanan.performClick(); // Trigger klik Makanan
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

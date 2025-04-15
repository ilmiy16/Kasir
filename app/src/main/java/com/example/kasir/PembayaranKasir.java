package com.example.kasir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PembayaranKasir extends AppCompatActivity {

    private LinearLayout tempatTotalPesanan;
    private TextView subtotalText, discountText, totalText;
    private Button btnTunai, btnQRIS, btnCheckout;

    private List<ItemPesanan> listPesanan;
    private double subtotal = 0;
    private double discount = 0;
    private double total = 0;

    // Nama-nama makanan dan minuman (contoh, sesuaikan dengan aplikasi Anda)
    private String[] namaMakanan = {
            "Nasi Goreng", "Mie Goreng", "Ayam Bakar", "Sate Ayam",
            "Gado-gado", "Soto Ayam", "Rendang", "Bakso", "Pecel Lele"
    };

    private String[] namaMinuman = {
            "Es Teh", "Es Jeruk", "Kopi Hitam", "Teh Tawar", "Jus Alpukat",
            "Jus Mangga", "Air Mineral", "Soda Gembira", "Cappuccino",
            "Es Kopi Susu", "Teh Botol", "Jahe Hangat", "Milk Shake",
            "Smoothie", "Lemon Tea"
    };

    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_kasir);

        // Inisialisasi view
        tempatTotalPesanan = findViewById(R.id.TempatTotalPesanan);
        subtotalText = findViewById(R.id.subtotalText);
        discountText = findViewById(R.id.discountText);
        totalText = findViewById(R.id.totalText);
        btnTunai = findViewById(R.id.btnTunai);
        btnQRIS = findViewById(R.id.btnQRIS);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Mengambil data pesanan dari Intent
        listPesanan = getPesananFromIntent();

        // Menampilkan daftar pesanan
        displayPesanan();

        // Menghitung total
        calculateTotal();

        // Set listener untuk button
        btnTunai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle pembayaran tunai
                Toast.makeText(PembayaranKasir.this, "Metode Pembayaran: Tunai", Toast.LENGTH_SHORT).show();
            }
        });

        btnQRIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle pembayaran QRIS
                Toast.makeText(PembayaranKasir.this, "Metode Pembayaran: QRIS", Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle proses checkout
                Toast.makeText(PembayaranKasir.this, "Pembayaran Selesai!", Toast.LENGTH_SHORT).show();
                // Tambahkan kode untuk menyimpan transaksi ke database
                finish(); // Kembali ke halaman sebelumnya setelah checkout
            }
        });
    }

    private List<ItemPesanan> getPesananFromIntent() {
        List<ItemPesanan> items = new ArrayList<>();

        // Mengambil data dari Intent
        int[] jumlahPesananMakanan = getIntent().getIntArrayExtra("JUMLAH_PESANAN_MAKANAN");
        int[] jumlahPesananMinuman = getIntent().getIntArrayExtra("JUMLAH_PESANAN_MINUMAN");
        int[] hargaMakanan = getIntent().getIntArrayExtra("HARGA_MAKANAN");
        int[] hargaMinuman = getIntent().getIntArrayExtra("HARGA_MINUMAN");

        // Format waktu dan tanggal
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());

        // Menambahkan item makanan ke list pesanan
        if (jumlahPesananMakanan != null && hargaMakanan != null) {
            for (int i = 0; i < jumlahPesananMakanan.length; i++) {
                if (jumlahPesananMakanan[i] > 0) {
                    items.add(new ItemPesanan(
                            namaMakanan[i],
                            jumlahPesananMakanan[i],
                            hargaMakanan[i],
                            currentTime,
                            currentDate
                    ));
                }
            }
        }

        // Menambahkan item minuman ke list pesanan
        if (jumlahPesananMinuman != null && hargaMinuman != null) {
            for (int i = 0; i < jumlahPesananMinuman.length; i++) {
                if (jumlahPesananMinuman[i] > 0) {
                    items.add(new ItemPesanan(
                            namaMinuman[i],
                            jumlahPesananMinuman[i],
                            hargaMinuman[i],
                            currentTime,
                            currentDate
                    ));
                }
            }
        }

        return items;
    }

    private void displayPesanan() {
        // Hapus semua view yang ada sebelumnya
        tempatTotalPesanan.removeAllViews();

        // Layout inflater untuk menambahkan item pesanan
        LayoutInflater inflater = getLayoutInflater();

        // Tambahkan setiap item pesanan ke layout
        for (ItemPesanan item : listPesanan) {
            View itemView = inflater.inflate(R.layout.item_layout_pembayaran, tempatTotalPesanan, false);

            TextView itemName = itemView.findViewById(R.id.itemName);
            TextView itemPrice = itemView.findViewById(R.id.itemPrice);
            TextView jam = itemView.findViewById(R.id.jam);
            TextView tanggal = itemView.findViewById(R.id.tanggal);
            ImageView itemImage = itemView.findViewById(R.id.itemimage);

            // Set nama item dengan jumlahnya
            itemName.setText(item.getNama() + " x" + item.getJumlah());

            // Set harga total untuk item ini (harga satuan * jumlah)
            double totalHarga = item.getHarga() * item.getJumlah();
            itemPrice.setText(currencyFormat.format(totalHarga));

            jam.setText(item.getJam());
            tanggal.setText(item.getTanggal());

            // Set gambar sesuai dengan item (opsional)
            // itemImage.setImageResource(getItemImageResource(item.getNama()));

            tempatTotalPesanan.addView(itemView);
        }
    }

    private void calculateTotal() {
        // Hitung subtotal
        subtotal = 0;
        for (ItemPesanan item : listPesanan) {
            subtotal += (item.getHarga() * item.getJumlah());
        }

        // Hitung diskon (contoh: diskon 10% jika subtotal > 100000)
        if (subtotal > 100000) {
            discount = subtotal * 0.1;
        } else {
            discount = 0;
        }

        // Hitung total
        total = subtotal - discount;

        // Update tampilan
        subtotalText.setText("Subtotal: " + currencyFormat.format(subtotal));
        discountText.setText("Discount: " + currencyFormat.format(discount));
        totalText.setText("TOTAL: " + currencyFormat.format(total));
    }

    // Fungsi untuk mendapatkan resource gambar berdasarkan nama item (implementasi opsional)
    private int getItemImageResource(String itemName) {
        String[] makananNames = {
                "Sosis Bakar", "Burger", "Kentang Goreng",
                "Pisang Goreng", "Tela-tela", "Nasi Gigit",
                "Bakso Bakar", "Banana Roll", "sosis bakar"
        };

        int[] makananImages = {
                R.drawable.sosis_bakar, R.drawable.burger, R.drawable.kentang_goreng,
                R.drawable.pisang_goreng, R.drawable.tela_tela, R.drawable.nasi_gigit,
                R.drawable.bakso_bakar, R.drawable.banana_roll, R.drawable.sosis_bakar
        };

        String[] minumanNames = {
                "Green tea", "Thai tea", "Es Milo",
                "Lemon tea", "Es Taro", "Es Strawberry",
                "Es Coklat", "Es Teh", "Es Oreo",
                "Pop Ice", "Pop Ice Small", "Es Leci",
                "Es Red Velvet", "Kopi Susu Panas", "Es Kopi Susu"
        };

        int[] minumanImages = {
                R.drawable.greentea, R.drawable.thaitea, R.drawable.esmilo,
                R.drawable.lemontea, R.drawable.estaro, R.drawable.esstrawberry,
                R.drawable.escoklat, R.drawable.esteh, R.drawable.esoreo,
                R.drawable.popice, R.drawable.popice, R.drawable.esleci,
                R.drawable.redvelvet, R.drawable.kopsuspanas, R.drawable.kopsus
        };

        // Cek di daftar makanan
        for (int i = 0; i < makananNames.length; i++) {
            if (makananNames[i].equalsIgnoreCase(itemName)) {
                return makananImages[i];
            }
        }

        // Cek di daftar minuman
        for (int i = 0; i < minumanNames.length; i++) {
            if (minumanNames[i].equalsIgnoreCase(itemName)) {
                return minumanImages[i];
            }
        }

        // Default jika tidak ditemukan
        return R.drawable.ic_launcher_background;
    }

}
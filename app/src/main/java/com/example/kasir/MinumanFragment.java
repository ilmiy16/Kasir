package com.example.kasir;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasir.databinding.FregmentMinumanBinding;

public class MinumanFragment extends Fragment {

    private FregmentMinumanBinding binding;

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

    int[] minumanPrices = {
            10000, 15000, 10000, 10000, 15000, 10000,
            10000, 15000, 10000, 10000, 10000, 10000,
            15000, 12000, 15000
    };

    // Array untuk menyimpan jumlah pesanan
    int[] jumlahPesanan = new int[minumanNames.length];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FregmentMinumanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainMenuKasir) {
            jumlahPesanan = ((MainMenuKasir) getActivity()).getJumlahPesananMinuman();
        }

        RecyclerView recyclerView = binding.recyclerViewMinuman;
        int columnCount = calculateNoOfColumns(160); // misalnya 160dp per item
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        recyclerView.setAdapter(new MinumanAdapter());

        // Update cart saat pertama kali tampil
        hitungTotalPesanan();
    }

    // Metode untuk menghitung total pesanan dan update floating cart
    private void hitungTotalPesanan() {
        int totalItems = 0;
        int totalPrice = 0;

        for (int i = 0; i < jumlahPesanan.length; i++) {
            totalItems += jumlahPesanan[i];
            totalPrice += jumlahPesanan[i] * minumanPrices[i];
        }

        // Update floating cart di MainMenuKasir
        if (getActivity() instanceof MainMenuKasir) {
            ((MainMenuKasir) getActivity()).updateCart(totalItems, totalPrice);
        }
    }

    private int calculateNoOfColumns(float itemWidthDp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return Math.max(2, (int) (screenWidthDp / itemWidthDp + 0.5));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class MinumanAdapter extends RecyclerView.Adapter<MinumanAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView namaMinuman, hargaMinuman, jumlahPesanan;
            ImageView gambarMinuman;
            ImageButton tambahButton, kurangiButton;

            ViewHolder(View itemView) {
                super(itemView);
                namaMinuman = itemView.findViewById(R.id.beverageNameMenu);
                gambarMinuman = itemView.findViewById(R.id.beverageImageMenu);
                hargaMinuman = itemView.findViewById(R.id.priceTextViewMenu);
                tambahButton = itemView.findViewById(R.id.addToCartButton);
                kurangiButton = itemView.findViewById(R.id.KurangiPesanan);
                jumlahPesanan = itemView.findViewById(R.id.JumlahPesanan);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beverage, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.namaMinuman.setText(minumanNames[position]);
            holder.gambarMinuman.setImageResource(minumanImages[position]);

            // Periksa jumlah pesanan
            if (jumlahPesanan[position] > 0) {
                // Jika jumlah pesanan minimal 1, tampilkan jumlahPesanan dan sembunyikan harga
                holder.jumlahPesanan.setVisibility(View.VISIBLE);
                holder.jumlahPesanan.setText(String.valueOf(jumlahPesanan[position]));
                holder.hargaMinuman.setVisibility(View.GONE);
            } else {
                // Jika jumlah pesanan 0, tampilkan harga dan sembunyikan jumlahPesanan
                holder.hargaMinuman.setVisibility(View.VISIBLE);
                holder.hargaMinuman.setText("Rp " + minumanPrices[position]);
                holder.jumlahPesanan.setVisibility(View.GONE);
            }

            // Tombol tambah
            holder.tambahButton.setOnClickListener(v -> {
                jumlahPesanan[position]++;

                // Tampilkan jumlahPesanan dan sembunyikan harga
                holder.jumlahPesanan.setVisibility(View.VISIBLE);
                holder.jumlahPesanan.setText(String.valueOf(jumlahPesanan[position]));
                holder.hargaMinuman.setVisibility(View.GONE);

                Toast.makeText(getContext(), minumanNames[position] + " ditambahkan ke pesanan", Toast.LENGTH_SHORT).show();

                // Update floating cart
                hitungTotalPesanan();
            });

            // Tombol kurangi
            holder.kurangiButton.setOnClickListener(v -> {
                if (jumlahPesanan[position] > 0) {
                    jumlahPesanan[position]--;

                    if (jumlahPesanan[position] > 0) {
                        // Jika jumlah pesanan masih ada, update teks jumlahPesanan
                        holder.jumlahPesanan.setText(String.valueOf(jumlahPesanan[position]));
                    } else {
                        // Jika jumlah pesanan sudah 0, sembunyikan jumlahPesanan dan tampilkan harga
                        holder.jumlahPesanan.setVisibility(View.GONE);
                        holder.hargaMinuman.setVisibility(View.VISIBLE);
                        holder.hargaMinuman.setText("Rp " + minumanPrices[position]);
                    }

                    Toast.makeText(getContext(), minumanNames[position] + " dikurangi dari pesanan", Toast.LENGTH_SHORT).show();

                    // Update floating cart
                    hitungTotalPesanan();
                }
            });
        }

        @Override
        public int getItemCount() {
            return minumanNames.length;
        }
    }
}
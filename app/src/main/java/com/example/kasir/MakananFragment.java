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

import com.example.kasir.databinding.FragementMakananBinding;

public class MakananFragment extends Fragment {
    private FragementMakananBinding binding;

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

    int[] makananPrices = {
            10000, 15000, 10000, 10000, 15000, 10000, 10000, 15000, 12314
    };

    int[] jumlahPesanan = new int[makananNames.length];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragementMakananBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainMenuKasir) {
            jumlahPesanan = ((MainMenuKasir) getActivity()).getJumlahPesananMakanan();
        }

        RecyclerView recyclerView = binding.recyclerViewMakanan;
        int columnCount = calculateNoOfColumns(160); // lebar 1 item (dp)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        recyclerView.setAdapter(new MakananAdapter());

        // Update cart saat pertama kali tampil
        hitungTotalPesanan();
    }

    // Metode untuk menghitung total pesanan dan update floating cart
    private void hitungTotalPesanan() {
        int totalItems = 0;
        int totalPrice = 0;

        for (int i = 0; i < jumlahPesanan.length; i++) {
            totalItems += jumlahPesanan[i];
            totalPrice += jumlahPesanan[i] * makananPrices[i];
        }

        // Update floating cart di MainMenuKasir
        if (getActivity() instanceof MainMenuKasir) {
            ((MainMenuKasir) getActivity()).updateCart(totalItems, totalPrice);
        }
    }

    // Hitung jumlah kolom berdasarkan lebar layar
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

    private class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView namaMakanan, hargaMakanan, jumlahPesanan;
            ImageView gambarMakanan;
            ImageButton tambahButton;
            ImageButton kurangiButton;

            ViewHolder(View itemView) {
                super(itemView);
                namaMakanan = itemView.findViewById(R.id.beverageNameMenu);
                gambarMakanan = itemView.findViewById(R.id.beverageImageMenu);
                hargaMakanan = itemView.findViewById(R.id.priceTextViewMenu);
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
            holder.namaMakanan.setText(makananNames[position]);
            holder.gambarMakanan.setImageResource(makananImages[position]);

            // Periksa jumlah pesanan
            if (jumlahPesanan[position] > 0) {
                // Jika jumlah pesanan minimal 1, tampilkan jumlahPesanan dan sembunyikan harga
                holder.jumlahPesanan.setVisibility(View.VISIBLE);
                holder.jumlahPesanan.setText(String.valueOf(jumlahPesanan[position]));
                holder.hargaMakanan.setVisibility(View.GONE);
            } else {
                // Jika jumlah pesanan 0, tampilkan harga dan sembunyikan jumlahPesanan
                holder.hargaMakanan.setVisibility(View.VISIBLE);
                holder.hargaMakanan.setText("Rp " + makananPrices[position]);
                holder.jumlahPesanan.setVisibility(View.GONE);
            }

            // Tombol tambah
            holder.tambahButton.setOnClickListener(v -> {
                jumlahPesanan[position]++;

                // Tampilkan jumlahPesanan dan sembunyikan harga
                holder.jumlahPesanan.setVisibility(View.VISIBLE);
                holder.jumlahPesanan.setText(String.valueOf(jumlahPesanan[position]));
                holder.hargaMakanan.setVisibility(View.GONE);

                Toast.makeText(getContext(), makananNames[position] + " ditambahkan ke pesanan", Toast.LENGTH_SHORT).show();

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
                        holder.hargaMakanan.setVisibility(View.VISIBLE);
                        holder.hargaMakanan.setText("Rp " + makananPrices[position]);
                    }

                    Toast.makeText(getContext(), makananNames[position] + " dikurangi dari pesanan", Toast.LENGTH_SHORT).show();

                    // Update floating cart
                    hitungTotalPesanan();
                }
            });
        }

        @Override
        public int getItemCount() {
            return makananNames.length;
        }
    }
}
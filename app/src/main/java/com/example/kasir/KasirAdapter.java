package com.example.kasir;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class KasirAdapter extends RecyclerView.Adapter<KasirAdapter.KasirViewHolder> {
    private List<User> kasirList;

    public KasirAdapter(List<User> kasirList) {
        this.kasirList = kasirList;
    }

    @Override
    public KasirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kasir, parent, false);
        return new KasirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KasirViewHolder holder, int position) {
        User user = kasirList.get(position);
        holder.namaKasirTextView.setText(user.getEmail());

        // Tombol "Acc" - Aksi untuk mengaktifkan kasir
        holder.btnAcc.setOnClickListener(v -> {
            holder.btnAcc.setEnabled(false);
            holder.btnAcc.setText("Acc ✔");
        });

        // Tombol "Hapus" - Aksi untuk menghapus kasir
        holder.btnHapus.setOnClickListener(v -> {
            holder.btnAcc.setVisibility(View.GONE);
            holder.btnHapus.setVisibility(View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return kasirList.size();
    }

    public class KasirViewHolder extends RecyclerView.ViewHolder {
        TextView namaKasirTextView, statusTextView;
        Button btnAcc, btnHapus;

        public KasirViewHolder(View itemView) {
            super(itemView);
            namaKasirTextView = itemView.findViewById(R.id.textViewNama);
            btnAcc = itemView.findViewById(R.id.btnAcc);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
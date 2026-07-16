package com.lilaclab.dadimadu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private final List<Produk> allItems = new ArrayList<>();
    private final List<Produk> visibleItems = new ArrayList<>();
    private final OnProdukAction listener;

    public ProdukAdapter(OnProdukAction listener) {
        this.listener = listener;
    }

    public void setItems(List<Produk> items) {
        allItems.clear();
        visibleItems.clear();
        if (items != null) {
            allItems.addAll(items);
            visibleItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        visibleItems.clear();
        String query = keyword == null ? "" : keyword.toLowerCase(Locale.US).trim();
        for (Produk produk : allItems) {
            String haystack = (produk.namaProduk + " " + produk.jenisAsal + " " + produk.ukuranKemasan).toLowerCase(Locale.US);
            if (query.isEmpty() || haystack.contains(query)) {
                visibleItems.add(produk);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context) parent.getContext()).inflate(R.layout.item_produk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk produk = visibleItems.get(position);
        holder.txtNamaProduk.setText(produk.namaProduk);
        holder.txtMetaProduk.setText("Jenis: " + produk.jenisAsal + "  |  " + produk.ukuranKemasan);
        holder.txtHargaProduk.setText("Harga jual  : " + FormatHelper.rupiah(produk.hargaJual));
        holder.txtModalProduk.setText("Modal       : " + FormatHelper.rupiah(produk.hargaModal) + "  |  Margin: " + FormatHelper.rupiah(produk.hargaJual - produk.hargaModal));
        holder.txtStok.setText("Stok: " + produk.stok + "  |  Min: " + produk.minimumStok);
        if ("habis".equals(produk.statusStok())) {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_red);
            holder.txtStok.setTextColor(0xff991b1b);
        } else if ("menipis".equals(produk.statusStok())) {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.txtStok.setTextColor(0xff92400e);
        } else {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_green);
            holder.txtStok.setTextColor(0xff065f46);
        }
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(produk));
        holder.btnHapus.setOnClickListener(v -> listener.onDelete(produk));
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaProduk;
        TextView txtMetaProduk;
        TextView txtHargaProduk;
        TextView txtModalProduk;
        TextView txtStok;
        TextView btnEdit;
        TextView btnHapus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaProduk = itemView.findViewById(R.id.txtNamaProduk);
            txtMetaProduk = itemView.findViewById(R.id.txtMetaProduk);
            txtHargaProduk = itemView.findViewById(R.id.txtHargaProduk);
            txtModalProduk = itemView.findViewById(R.id.txtModalProduk);
            txtStok = itemView.findViewById(R.id.txtStok);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }

    public interface OnProdukAction {
        void onEdit(Produk produk);
        void onDelete(Produk produk);
        void onStokChange(Produk produk, int delta);
    }
}

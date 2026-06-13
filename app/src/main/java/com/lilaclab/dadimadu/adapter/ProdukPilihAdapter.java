package com.lilaclab.dadimadu.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;

public class ProdukPilihAdapter extends RecyclerView.Adapter<ProdukPilihAdapter.ViewHolder> {
    private final List<Produk> items = new ArrayList<>();
    private final SparseIntArray qtyMap = new SparseIntArray();
    private final OnQtyChanged listener;

    public ProdukPilihAdapter(OnQtyChanged listener) {
        this.listener = listener;
    }

    public void setItems(List<Produk> products) {
        items.clear();
        qtyMap.clear();
        if (products != null) {
            items.addAll(products);
        }
        notifyDataSetChanged();
        listener.onChanged();
    }

    public List<SelectedItem> getSelectedItems() {
        ArrayList<SelectedItem> selected = new ArrayList<>();
        for (Produk produk : items) {
            int qty = qtyMap.get(produk.idProduk, 0);
            if (qty > 0) {
                selected.add(new SelectedItem(produk, qty));
            }
        }
        return selected;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (SelectedItem item : getSelectedItems()) {
            subtotal += item.produk.hargaJual * item.qty;
        }
        return subtotal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context) parent.getContext()).inflate(R.layout.item_produk_pilih, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk produk = items.get(position);
        int qty = qtyMap.get(produk.idProduk, 0);
        holder.txtNamaProduk.setText(produk.namaProduk);
        holder.txtInfoProduk.setText(produk.ukuranKemasan + " | Stok " + produk.stok + " | " + FormatHelper.rupiah(produk.hargaJual));
        holder.txtQty.setText(String.valueOf(qty));
        holder.txtSubtotal.setText(FormatHelper.rupiah(qty * produk.hargaJual));
        holder.btnPlus.setOnClickListener(v -> {
            int next = qtyMap.get(produk.idProduk, 0) + 1;
            if (next > produk.stok) {
                Toast.makeText(v.getContext(), "Stok " + produk.namaProduk + " tidak cukup.", Toast.LENGTH_SHORT).show();
                return;
            }
            qtyMap.put(produk.idProduk, next);
            holder.txtQty.setText(String.valueOf(next));
            holder.txtSubtotal.setText(FormatHelper.rupiah(next * produk.hargaJual));
            listener.onChanged();
        });
        holder.btnMinus.setOnClickListener(v -> {
            int current = qtyMap.get(produk.idProduk, 0);
            if (current <= 0) return;
            int updated = current - 1;
            qtyMap.put(produk.idProduk, updated);
            holder.txtQty.setText(String.valueOf(updated));
            holder.txtSubtotal.setText(FormatHelper.rupiah(updated * produk.hargaJual));
            listener.onChanged();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaProduk;
        TextView txtInfoProduk;
        TextView btnMinus;
        TextView txtQty;
        TextView btnPlus;
        TextView txtSubtotal;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaProduk = itemView.findViewById(R.id.txtNamaProduk);
            txtInfoProduk = itemView.findViewById(R.id.txtInfoProduk);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            txtQty = itemView.findViewById(R.id.txtQty);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
        }
    }

    public static class SelectedItem {
        public final Produk produk;
        public final int qty;

        public SelectedItem(Produk produk, int qty) {
            this.produk = produk;
            this.qty = qty;
        }
    }

    public interface OnQtyChanged {
        void onChanged();
    }
}

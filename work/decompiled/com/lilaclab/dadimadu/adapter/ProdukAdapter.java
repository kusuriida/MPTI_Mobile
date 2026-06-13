/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  androidx.annotation.NonNull
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  com.lilaclab.dadimadu.R$drawable
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.adapter.ProdukAdapter
 *  com.lilaclab.dadimadu.adapter.ProdukAdapter$OnProdukAction
 *  com.lilaclab.dadimadu.adapter.ProdukAdapter$ViewHolder
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.utils.FormatHelper
 */
package com.lilaclab.dadimadu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.adapter.ProdukAdapter;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdukAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private final List<Produk> allItems = new ArrayList();
    private final List<Produk> visibleItems = new ArrayList();
    private final OnProdukAction listener;

    public ProdukAdapter(OnProdukAction listener) {
        this.listener = listener;
    }

    public void setItems(List<Produk> items) {
        this.allItems.clear();
        this.visibleItems.clear();
        if (items != null) {
            this.allItems.addAll(items);
            this.visibleItems.addAll(items);
        }
        this.notifyDataSetChanged();
    }

    public void filter(String keyword) {
        this.visibleItems.clear();
        String query = keyword == null ? "" : keyword.toLowerCase(Locale.US).trim();
        for (Produk produk : this.allItems) {
            String haystack = (produk.namaProduk + " " + produk.jenisAsal + " " + produk.ukuranKemasan).toLowerCase(Locale.US);
            if (!query.isEmpty() && !haystack.contains(query)) continue;
            this.visibleItems.add(produk);
        }
        this.notifyDataSetChanged();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context)parent.getContext()).inflate(R.layout.item_produk, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk produk = (Produk)this.visibleItems.get(position);
        holder.txtNamaProduk.setText((CharSequence)produk.namaProduk);
        holder.txtMetaProduk.setText((CharSequence)("Jenis: " + produk.jenisAsal + " \u2022 " + produk.ukuranKemasan));
        holder.txtHargaProduk.setText((CharSequence)("Harga jual: " + FormatHelper.rupiah((double)produk.hargaJual)));
        holder.txtModalProduk.setText((CharSequence)("Modal: " + FormatHelper.rupiah((double)produk.hargaModal) + " \u2022 Margin: " + FormatHelper.rupiah((double)(produk.hargaJual - produk.hargaModal))));
        holder.txtStok.setText((CharSequence)("Stok: " + produk.stok + " / min " + produk.minimumStok));
        if ("habis".equals(produk.statusStok())) {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_red);
            holder.txtStok.setTextColor(-6743269);
        } else if ("menipis".equals(produk.statusStok())) {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.txtStok.setTextColor(-7192562);
        } else {
            holder.txtStok.setBackgroundResource(R.drawable.bg_badge_green);
            holder.txtStok.setTextColor(-16359610);
        }
        holder.btnEdit.setOnClickListener(v -> this.listener.onEdit(produk));
        holder.btnHapus.setOnClickListener(v -> this.listener.onDelete(produk));
        holder.btnPlusStok.setOnClickListener(v -> this.listener.onPlusStock(produk));
        holder.btnMinusStok.setOnClickListener(v -> this.listener.onMinusStock(produk));
    }

    public int getItemCount() {
        return this.visibleItems.size();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.SparseIntArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.Toast
 *  androidx.annotation.NonNull
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.adapter.ProdukPilihAdapter
 *  com.lilaclab.dadimadu.adapter.ProdukPilihAdapter$OnQtyChanged
 *  com.lilaclab.dadimadu.adapter.ProdukPilihAdapter$SelectedItem
 *  com.lilaclab.dadimadu.adapter.ProdukPilihAdapter$ViewHolder
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.utils.FormatHelper
 */
package com.lilaclab.dadimadu.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.adapter.ProdukPilihAdapter;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;

public class ProdukPilihAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private final List<Produk> items = new ArrayList();
    private final SparseIntArray qtyMap = new SparseIntArray();
    private final OnQtyChanged listener;

    public ProdukPilihAdapter(OnQtyChanged listener) {
        this.listener = listener;
    }

    public void setItems(List<Produk> products) {
        this.items.clear();
        this.qtyMap.clear();
        if (products != null) {
            this.items.addAll(products);
        }
        this.notifyDataSetChanged();
        this.listener.onChanged();
    }

    public List<SelectedItem> getSelectedItems() {
        ArrayList<SelectedItem> selected = new ArrayList<SelectedItem>();
        for (Produk produk : this.items) {
            int qty = this.qtyMap.get(produk.idProduk, 0);
            if (qty <= 0) continue;
            selected.add(new SelectedItem(produk, qty));
        }
        return selected;
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (SelectedItem item : this.getSelectedItems()) {
            subtotal += item.produk.hargaJual * (double)item.qty;
        }
        return subtotal;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context)parent.getContext()).inflate(R.layout.item_produk_pilih, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produk produk = (Produk)this.items.get(position);
        int qty = this.qtyMap.get(produk.idProduk, 0);
        holder.txtNamaProduk.setText((CharSequence)produk.namaProduk);
        holder.txtInfoProduk.setText((CharSequence)(produk.ukuranKemasan + " \u2022 Stok " + produk.stok + " \u2022 " + FormatHelper.rupiah((double)produk.hargaJual)));
        holder.txtQty.setText((CharSequence)String.valueOf(qty));
        holder.txtSubtotal.setText((CharSequence)FormatHelper.rupiah((double)((double)qty * produk.hargaJual)));
        holder.btnPlus.setOnClickListener(v -> {
            int next = this.qtyMap.get(produk.idProduk, 0) + 1;
            if (next > produk.stok) {
                Toast.makeText((Context)v.getContext(), (CharSequence)("Stok " + produk.namaProduk + " tidak cukup."), (int)0).show();
                return;
            }
            this.qtyMap.put(produk.idProduk, next);
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != -1) {
                this.notifyItemChanged(adapterPosition);
            }
            this.listener.onChanged();
        });
        holder.btnMinus.setOnClickListener(v -> {
            int current = this.qtyMap.get(produk.idProduk, 0);
            if (current <= 0) {
                return;
            }
            this.qtyMap.put(produk.idProduk, current - 1);
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != -1) {
                this.notifyItemChanged(adapterPosition);
            }
            this.listener.onChanged();
        });
    }

    public int getItemCount() {
        return this.items.size();
    }
}


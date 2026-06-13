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
 *  com.lilaclab.dadimadu.adapter.TransaksiAdapter
 *  com.lilaclab.dadimadu.adapter.TransaksiAdapter$OnTransaksiAction
 *  com.lilaclab.dadimadu.adapter.TransaksiAdapter$ViewHolder
 *  com.lilaclab.dadimadu.model.TransaksiListItem
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
import com.lilaclab.dadimadu.adapter.TransaksiAdapter;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private final List<TransaksiListItem> items = new ArrayList();
    private final OnTransaksiAction listener;

    public TransaksiAdapter(OnTransaksiAction listener) {
        this.listener = listener;
    }

    public void setItems(List<TransaksiListItem> newItems) {
        this.items.clear();
        if (newItems != null) {
            this.items.addAll(newItems);
        }
        this.notifyDataSetChanged();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context)parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransaksiListItem item = (TransaksiListItem)this.items.get(position);
        holder.txtIdTransaksi.setText((CharSequence)item.idTransaksi);
        holder.txtPelanggan.setText((CharSequence)(item.namaPelanggan == null ? "Umum" : item.namaPelanggan));
        holder.txtTanggal.setText((CharSequence)(FormatHelper.tanggalIndo((String)item.tanggal) + " \u2022 " + item.metodeBayar));
        holder.txtTotal.setText((CharSequence)FormatHelper.rupiah((double)item.grandtotal));
        holder.txtStatus.setText((CharSequence)item.statusBayar);
        if ("Lunas".equals(item.statusBayar)) {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_green);
            holder.txtStatus.setTextColor(-16359610);
        } else if ("DP".equals(item.statusBayar)) {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.txtStatus.setTextColor(-7192562);
        } else {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_red);
            holder.txtStatus.setTextColor(-6743269);
        }
        holder.btnDetail.setOnClickListener(v -> this.listener.onDetail(item));
        holder.btnCetak.setOnClickListener(v -> this.listener.onCetak(item));
        holder.itemView.setOnClickListener(v -> this.listener.onDetail(item));
    }

    public int getItemCount() {
        return this.items.size();
    }
}


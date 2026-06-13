package com.lilaclab.dadimadu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.ArrayList;
import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private final List<TransaksiListItem> items = new ArrayList<>();
    private final OnTransaksiAction listener;

    public TransaksiAdapter(OnTransaksiAction listener) {
        this.listener = listener;
    }

    public void setItems(List<TransaksiListItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((Context) parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransaksiListItem item = items.get(position);
        holder.txtIdTransaksi.setText(item.idTransaksi);
        holder.txtPelanggan.setText(item.namaPelanggan == null ? "Umum" : item.namaPelanggan);
        holder.txtTanggal.setText(FormatHelper.tanggalIndo(item.tanggal) + " | " + item.metodeBayar);
        holder.txtTotal.setText(FormatHelper.rupiah(item.grandtotal));
        holder.txtStatus.setText(item.statusBayar);
        if ("Lunas".equals(item.statusBayar)) {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_green);
            holder.txtStatus.setTextColor(0xff065f46);
        } else if ("DP".equals(item.statusBayar)) {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_amber);
            holder.txtStatus.setTextColor(0xff92400e);
        } else {
            holder.txtStatus.setBackgroundResource(R.drawable.bg_badge_red);
            holder.txtStatus.setTextColor(0xff991b1b);
        }
        holder.btnDetail.setOnClickListener(v -> listener.onDetail(item));
        holder.btnCetak.setOnClickListener(v -> listener.onCetak(item));
        holder.itemView.setOnClickListener(v -> listener.onDetail(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdTransaksi;
        TextView txtStatus;
        TextView txtPelanggan;
        TextView txtTanggal;
        TextView txtTotal;
        TextView btnDetail;
        TextView btnCetak;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdTransaksi = itemView.findViewById(R.id.txtIdTransaksi);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPelanggan = itemView.findViewById(R.id.txtPelanggan);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnCetak = itemView.findViewById(R.id.btnCetak);
        }
    }

    public interface OnTransaksiAction {
        void onDetail(TransaksiListItem item);
        void onCetak(TransaksiListItem item);
    }
}

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.TextWatcher
 *  android.widget.EditText
 *  android.widget.ProgressBar
 *  android.widget.Toast
 *  androidx.appcompat.app.AlertDialog$Builder
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.recyclerview.widget.LinearLayoutManager
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$LayoutManager
 *  com.lilaclab.dadimadu.R$id
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.activity.TambahTransaksiActivity
 *  com.lilaclab.dadimadu.activity.TransaksiActivity
 *  com.lilaclab.dadimadu.adapter.TransaksiAdapter
 *  com.lilaclab.dadimadu.adapter.TransaksiAdapter$OnTransaksiAction
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.model.TransaksiListItem
 *  com.lilaclab.dadimadu.utils.FormatHelper
 */
package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.TambahTransaksiActivity;
import com.lilaclab.dadimadu.adapter.TransaksiAdapter;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.List;

public class TransaksiActivity
extends AppCompatActivity {
    private AppDatabase db;
    private TransaksiAdapter adapter;
    private ProgressBar progressBar;
    private String keyword = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_transaksi);
        this.db = AppDatabase.getInstance((Context)this);
        this.progressBar = (ProgressBar)this.findViewById(R.id.progressTransaksi);
        this.adapter = new TransaksiAdapter((TransaksiAdapter.OnTransaksiAction)new /* Unavailable Anonymous Inner Class!! */);
        RecyclerView rvTransaksi = (RecyclerView)this.findViewById(R.id.rvTransaksi);
        rvTransaksi.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this));
        rvTransaksi.setAdapter((RecyclerView.Adapter)this.adapter);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnTambah).setOnClickListener(v -> this.openTambah());
        this.findViewById(R.id.btnTambahTop).setOnClickListener(v -> this.openTambah());
        ((EditText)this.findViewById(R.id.edtCariTransaksi)).addTextChangedListener((TextWatcher)new /* Unavailable Anonymous Inner Class!! */);
    }

    protected void onResume() {
        super.onResume();
        this.loadTransaksi();
    }

    private void loadTransaksi() {
        this.progressBar.setVisibility(0);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List items = this.keyword.trim().isEmpty() ? this.db.transaksiDao().getAllList() : this.db.transaksiDao().search(this.keyword.trim());
            this.runOnUiThread(() -> {
                this.progressBar.setVisibility(8);
                this.adapter.setItems(items);
            });
        });
    }

    private void openTambah() {
        this.startActivity(new Intent((Context)this, TambahTransaksiActivity.class));
    }

    private void showReceipt(TransaksiListItem item) {
        String receipt = "Dadi Madu\n" + item.idTransaksi + "\nPelanggan: " + item.namaPelanggan + "\nTanggal: " + FormatHelper.tanggalIndo((String)item.tanggal) + "\nStatus: " + item.statusBayar + "\nTotal: " + FormatHelper.rupiah((double)item.grandtotal);
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Preview Struk").setMessage((CharSequence)receipt).setNegativeButton((CharSequence)"Tutup", null).setPositiveButton((CharSequence)"Cetak", (dialog, which) -> Toast.makeText((Context)this, (CharSequence)"Fitur cetak siap dihubungkan ke printer Bluetooth.", (int)0).show()).show();
    }

    static /* synthetic */ void access$000(TransaksiActivity x0, TransaksiListItem x1) {
        x0.showReceipt(x1);
    }

    static /* synthetic */ String access$102(TransaksiActivity x0, String x1) {
        x0.keyword = x1;
        return x0.keyword;
    }

    static /* synthetic */ void access$200(TransaksiActivity x0) {
        x0.loadTransaksi();
    }
}


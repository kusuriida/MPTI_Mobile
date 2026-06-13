/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.TextWatcher
 *  android.widget.EditText
 *  android.widget.Toast
 *  androidx.appcompat.app.AlertDialog$Builder
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.recyclerview.widget.LinearLayoutManager
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$LayoutManager
 *  com.lilaclab.dadimadu.R$id
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.activity.ProdukActivity
 *  com.lilaclab.dadimadu.activity.ProdukActivity$DeleteResult
 *  com.lilaclab.dadimadu.activity.TambahProdukActivity
 *  com.lilaclab.dadimadu.adapter.ProdukAdapter
 *  com.lilaclab.dadimadu.adapter.ProdukAdapter$OnProdukAction
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.entity.Produk
 */
package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.ProdukActivity;
import com.lilaclab.dadimadu.activity.TambahProdukActivity;
import com.lilaclab.dadimadu.adapter.ProdukAdapter;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Produk;
import java.util.List;

public class ProdukActivity
extends AppCompatActivity {
    private AppDatabase db;
    private ProdukAdapter adapter;
    private String keyword = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_produk);
        this.db = AppDatabase.getInstance((Context)this);
        this.adapter = new ProdukAdapter((ProdukAdapter.OnProdukAction)new /* Unavailable Anonymous Inner Class!! */);
        RecyclerView rvProduk = (RecyclerView)this.findViewById(R.id.rvProduk);
        rvProduk.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager((Context)this));
        rvProduk.setAdapter((RecyclerView.Adapter)this.adapter);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnTambahProduk).setOnClickListener(v -> this.openTambahProduk());
        this.findViewById(R.id.btnTambahProdukTop).setOnClickListener(v -> this.openTambahProduk());
        ((EditText)this.findViewById(R.id.edtCariProduk)).addTextChangedListener((TextWatcher)new /* Unavailable Anonymous Inner Class!! */);
    }

    protected void onResume() {
        super.onResume();
        this.reload();
    }

    private void reload() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List items = this.db.produkDao().getAll();
            this.runOnUiThread(() -> {
                this.adapter.setItems(items);
                this.adapter.filter(this.keyword);
            });
        });
    }

    private void changeStock(int idProduk, int delta) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            this.db.produkDao().changeStock(idProduk, delta);
            this.reload();
        });
    }

    private void openTambahProduk() {
        this.startActivity(new Intent((Context)this, TambahProdukActivity.class));
    }

    private void confirmDelete(Produk produk) {
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Hapus produk?").setMessage((CharSequence)("Produk " + produk.namaProduk + " akan dihapus dari data lokal. Jika produk pernah dipakai di transaksi atau pembelian stok, data detail terkait produk ini ikut dibersihkan.")).setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Hapus", (dialog, which) -> this.deleteProduk(produk)).show();
    }

    private void deleteProduk(Produk produk) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            DeleteResult result = new DeleteResult(null);
            try {
                this.db.runInTransaction(() -> {
                    result.detailCount = this.db.transaksiDao().countDetailsByProduk(produk.idProduk);
                    result.pembelianCount = this.db.pembelianStokDao().countByProduk(produk.idProduk);
                    this.db.transaksiDao().deleteDetailsByProduk(produk.idProduk);
                    this.db.pembelianStokDao().deleteByProduk(produk.idProduk);
                    this.db.produkDao().deleteById(produk.idProduk);
                });
                result.success = true;
            }
            catch (Exception e) {
                result.errorMessage = e.getMessage();
            }
            this.runOnUiThread(() -> {
                if (result.success) {
                    String message = "Produk berhasil dihapus.";
                    if (result.detailCount > 0 || result.pembelianCount > 0) {
                        message = "Produk dihapus beserta data terkaitnya.";
                    }
                    Toast.makeText((Context)this, (CharSequence)message, (int)0).show();
                    this.reload();
                } else {
                    Toast.makeText((Context)this, (CharSequence)("Produk gagal dihapus: " + result.errorMessage), (int)1).show();
                }
            });
        });
    }

    static /* synthetic */ void access$000(ProdukActivity x0, Produk x1) {
        x0.confirmDelete(x1);
    }

    static /* synthetic */ void access$100(ProdukActivity x0, int x1, int x2) {
        x0.changeStock(x1, x2);
    }

    static /* synthetic */ String access$202(ProdukActivity x0, String x1) {
        x0.keyword = x1;
        return x0.keyword;
    }

    static /* synthetic */ String access$200(ProdukActivity x0) {
        return x0.keyword;
    }

    static /* synthetic */ ProdukAdapter access$300(ProdukActivity x0) {
        return x0.adapter;
    }
}


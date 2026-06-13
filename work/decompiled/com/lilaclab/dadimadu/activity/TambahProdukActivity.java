/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AppCompatActivity
 *  com.lilaclab.dadimadu.R$id
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.activity.TambahProdukActivity
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.utils.FormatHelper
 */
package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.utils.FormatHelper;

public class TambahProdukActivity
extends AppCompatActivity {
    private EditText edtNamaProduk;
    private EditText edtUkuran;
    private EditText edtHargaJual;
    private EditText edtHargaModal;
    private EditText edtStok;
    private EditText edtMinimumStok;
    private Spinner spinnerJenis;
    private TextView txtTitle;
    private AppDatabase db;
    private int idProduk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tambah_produk);
        this.db = AppDatabase.getInstance((Context)this);
        this.idProduk = this.getIntent().getIntExtra("id_produk", 0);
        this.bindViews();
        this.setupSpinner();
        if (this.idProduk > 0) {
            this.txtTitle.setText((CharSequence)"Edit Produk");
            this.loadProduk();
        }
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnBatal).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnSimpan).setOnClickListener(v -> this.simpan());
    }

    private void bindViews() {
        this.txtTitle = (TextView)this.findViewById(R.id.txtTitle);
        this.edtNamaProduk = (EditText)this.findViewById(R.id.edtNamaProduk);
        this.edtUkuran = (EditText)this.findViewById(R.id.edtUkuran);
        this.edtHargaJual = (EditText)this.findViewById(R.id.edtHargaJual);
        this.edtHargaModal = (EditText)this.findViewById(R.id.edtHargaModal);
        this.edtStok = (EditText)this.findViewById(R.id.edtStok);
        this.edtMinimumStok = (EditText)this.findViewById(R.id.edtMinimumStok);
        this.spinnerJenis = (Spinner)this.findViewById(R.id.spinnerJenis);
    }

    private void setupSpinner() {
        ArrayAdapter adapter = new ArrayAdapter((Context)this, 17367048, (Object[])new String[]{"Supplier", "Ternak"});
        adapter.setDropDownViewResource(0x1090009);
        this.spinnerJenis.setAdapter((SpinnerAdapter)adapter);
    }

    private void loadProduk() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Produk produk = this.db.produkDao().getById(this.idProduk);
            this.runOnUiThread(() -> {
                if (produk == null) {
                    return;
                }
                this.edtNamaProduk.setText((CharSequence)produk.namaProduk);
                this.edtUkuran.setText((CharSequence)produk.ukuranKemasan);
                this.edtHargaJual.setText((CharSequence)String.valueOf((int)produk.hargaJual));
                this.edtHargaModal.setText((CharSequence)String.valueOf((int)produk.hargaModal));
                this.edtStok.setText((CharSequence)String.valueOf(produk.stok));
                this.edtMinimumStok.setText((CharSequence)String.valueOf(produk.minimumStok));
                this.spinnerJenis.setSelection("Ternak".equals(produk.jenisAsal) ? 1 : 0);
            });
        });
    }

    private void simpan() {
        String nama = this.edtNamaProduk.getText().toString().trim();
        String ukuran = this.edtUkuran.getText().toString().trim();
        if (nama.isEmpty() || ukuran.isEmpty()) {
            Toast.makeText((Context)this, (CharSequence)"Nama produk dan ukuran wajib diisi.", (int)0).show();
            return;
        }
        Produk produk = new Produk(nama, this.spinnerJenis.getSelectedItem().toString(), ukuran, FormatHelper.parseMoney((String)this.edtHargaJual.getText().toString()), FormatHelper.parseMoney((String)this.edtHargaModal.getText().toString()), FormatHelper.parseInt((String)this.edtStok.getText().toString()), FormatHelper.parseInt((String)this.edtMinimumStok.getText().toString()));
        produk.idProduk = this.idProduk;
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (produk.idProduk > 0) {
                this.db.produkDao().update(produk);
            } else {
                this.db.produkDao().insert(produk);
            }
            this.runOnUiThread(() -> {
                Toast.makeText((Context)this, (CharSequence)"Produk berhasil disimpan.", (int)0).show();
                this.finish();
            });
        });
    }
}


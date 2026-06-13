package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.adapter.TransaksiAdapter;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import java.util.List;

public class TransaksiActivity extends AppCompatActivity {
    private AppDatabase db;
    private TransaksiAdapter adapter;
    private ProgressBar progressBar;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        db = AppDatabase.getInstance((Context) this);
        progressBar = findViewById(R.id.progressTransaksi);
        adapter = new TransaksiAdapter(new TransaksiAdapter.OnTransaksiAction() {
            @Override public void onDetail(TransaksiListItem item) {
                Intent intent = new Intent((Context) TransaksiActivity.this, DetailTransaksiActivity.class);
                intent.putExtra("id_transaksi", item.idTransaksi);
                startActivity(intent);
            }
            @Override public void onCetak(TransaksiListItem item) { showReceipt(item); }
        });
        RecyclerView rvTransaksi = findViewById(R.id.rvTransaksi);
        rvTransaksi.setLayoutManager(new LinearLayoutManager((Context) this));
        rvTransaksi.setAdapter(adapter);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnTambah).setOnClickListener(v -> openTambah());
        ((EditText) findViewById(R.id.edtCariTransaksi)).addTextChangedListener(new SimpleTextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                keyword = s.toString();
                loadTransaksi();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransaksi();
    }

    private void loadTransaksi() {
        progressBar.setVisibility(0);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<TransaksiListItem> items = keyword.trim().isEmpty() ? db.transaksiDao().getAllList() : db.transaksiDao().search(keyword.trim());
            runOnUiThread(() -> {
                progressBar.setVisibility(8);
                adapter.setItems(items);
            });
        });
    }

    private void openTambah() {
        startActivity(new Intent((Context) this, TambahTransaksiActivity.class));
    }

    private void showReceipt(TransaksiListItem item) {
        String receipt = "Dadi Madu\n" + item.idTransaksi + "\nPelanggan: " + item.namaPelanggan + "\nTanggal: " + FormatHelper.tanggalIndo(item.tanggal) + "\nStatus: " + item.statusBayar + "\nTotal: " + FormatHelper.rupiah(item.grandtotal);
        new AlertDialog.Builder((Context) this)
                .setTitle("Preview Struk")
                .setMessage(receipt)
                .setNegativeButton("Tutup", null)
                .setPositiveButton("Cetak", (dialog, which) -> Toast.makeText((Context) this, "Fitur cetak siap dihubungkan ke printer Bluetooth.", Toast.LENGTH_SHORT).show())
                .show();
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}

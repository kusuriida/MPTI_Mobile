package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.adapter.ProdukAdapter;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Produk;
import java.util.List;

public class ProdukActivity extends AppCompatActivity {
    private AppDatabase db;
    private ProdukAdapter adapter;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        db = AppDatabase.getInstance((Context) this);
        adapter = new ProdukAdapter(new ProdukAdapter.OnProdukAction() {
            @Override public void onEdit(Produk produk) { openEditProduk(produk); }
            @Override public void onDelete(Produk produk) { confirmDelete(produk); }
            @Override public void onStokChange(Produk produk, int delta) { changeStock(produk.idProduk, delta); }
        });
        RecyclerView rvProduk = findViewById(R.id.rvProduk);
        rvProduk.setLayoutManager(new LinearLayoutManager((Context) this));
        rvProduk.setAdapter(adapter);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnTambahProdukTop).setOnClickListener(v -> openTambahProduk());
        ((EditText) findViewById(R.id.edtCariProduk)).addTextChangedListener(new SimpleTextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                keyword = s.toString();
                adapter.filter(keyword);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Produk> items = db.produkDao().getAll();
            runOnUiThread(() -> {
                adapter.setItems(items);
                adapter.filter(keyword);
            });
        });
    }

    private void changeStock(int idProduk, int delta) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.produkDao().changeStock(idProduk, delta);
            reload();
        });
    }

    private void openTambahProduk() {
        startActivity(new Intent((Context) this, TambahProdukActivity.class));
    }

    private void openEditProduk(Produk produk) {
        Intent intent = new Intent((Context) this, TambahProdukActivity.class);
        intent.putExtra("id_produk", produk.idProduk);
        startActivity(intent);
    }



    private void confirmDelete(Produk produk) {
        new AlertDialog.Builder((Context) this)
                .setTitle("Hapus produk?")
                .setMessage("Produk " + produk.namaProduk + " akan dihapus dari data lokal. Data detail terkait ikut dibersihkan.")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus", (dialog, which) -> deleteProduk(produk))
                .show();
    }

    private void deleteProduk(Produk produk) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            DeleteResult result = new DeleteResult();
            try {
                db.runInTransaction(() -> {
                    result.detailCount = db.transaksiDao().countDetailsByProduk(produk.idProduk);
                    result.pembelianCount = db.pembelianStokDao().countByProduk(produk.idProduk);
                    db.transaksiDao().deleteDetailsByProduk(produk.idProduk);
                    db.pembelianStokDao().deleteByProduk(produk.idProduk);
                    db.produkDao().deleteById(produk.idProduk);
                });
                result.success = true;
            } catch (Exception e) {
                result.errorMessage = e.getMessage();
            }
            runOnUiThread(() -> {
                if (result.success) {
                    Toast.makeText((Context) this, result.detailCount > 0 || result.pembelianCount > 0 ? "Produk dihapus beserta data terkaitnya." : "Produk berhasil dihapus.", Toast.LENGTH_SHORT).show();
                    reload();
                } else {
                    Toast.makeText((Context) this, "Produk gagal dihapus: " + result.errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private static class DeleteResult {
        boolean success;
        int detailCount;
        int pembelianCount;
        String errorMessage;
    }

    private abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    }
}

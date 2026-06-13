package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.BaseListActivity;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.model.PembelianListItem;
import com.lilaclab.dadimadu.utils.FormatHelper;
import com.lilaclab.dadimadu.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;

public class PembelianActivity
extends BaseListActivity {
    protected String title() {
        return "Pembelian Stok";
    }

    protected String subtitle() {
        return "Catat stok madu masuk dari supplier atau ternak.";
    }

    protected String actionText() {
        return "Tambah";
    }

    protected void onAction() {
        this.loadProdukForDialog();
    }

    protected void loadItems() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List items = this.db.pembelianStokDao().getAllList();
            this.runOnUiThread(() -> this.render(items));
        });
    }

    private void render(List<PembelianListItem> items) {
        this.listContainer.removeAllViews();
        if (items.isEmpty()) {
            this.showEmpty("Belum ada pembelian stok yang dicatat.");
            return;
        }
        for (PembelianListItem item : items) {
            this.addCard(item.namaProduk + " +" + item.jumlah + " pcs", FormatHelper.tanggalIndo((String)item.tanggal) + "\n\nSupplier : " + this.safe(item.supplier) + "\nTotal    : " + FormatHelper.rupiah((double)item.total) + "\nKet.     : " + this.safe(item.keterangan));
        }
    }

    private void loadProdukForDialog() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List products = this.db.produkDao().getAll();
            this.runOnUiThread(() -> this.showForm(products));
        });
    }

    private void showForm(List<Produk> products) {
        if (products.isEmpty()) {
            Toast.makeText((Context)this, (CharSequence)"Tambahkan produk terlebih dahulu.", (int)0).show();
            return;
        }
        LinearLayout body = this.dialogBody();
        Spinner spinnerProduk = new Spinner((Context)this);
        ArrayList<String> names = new ArrayList<String>();
        for (Produk produk : products) {
            names.add(produk.namaProduk + " - " + produk.ukuranKemasan);
        }
        ArrayAdapter productAdapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, names);
        productAdapter.setDropDownViewResource(R.layout.item_spinner_text);
        spinnerProduk.setAdapter((SpinnerAdapter)productAdapter);
        EditText tanggal = this.input("Tanggal yyyy-MM-dd");
        tanggal.setText((CharSequence)FormatHelper.today());
        EditText supplier = this.input("Supplier");
        EditText jumlah = this.input("Jumlah stok masuk");
        EditText hargaBeli = this.input("Harga beli per item");
        EditText keterangan = this.input("Keterangan");
        body.addView((View)this.label("Produk"));
        body.addView((View)spinnerProduk);
        body.addView((View)this.label("Tanggal"));
        body.addView((View)tanggal);
        body.addView((View)this.label("Supplier"));
        body.addView((View)supplier);
        body.addView((View)this.label("Jumlah Stok Masuk"));
        body.addView((View)jumlah);
        body.addView((View)this.label("Harga Beli per Item"));
        body.addView((View)hargaBeli);
        body.addView((View)this.label("Keterangan"));
        body.addView((View)keterangan);
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Tambah Pembelian Stok").setView((View)body).setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Simpan", (dialog, which) -> {
            Produk produk = (Produk)products.get(spinnerProduk.getSelectedItemPosition());
            int qty = FormatHelper.parseInt((String)jumlah.getText().toString());
            double harga = FormatHelper.parseMoney((String)hargaBeli.getText().toString());
            if (qty <= 0) {
                Toast.makeText((Context)this, (CharSequence)"Jumlah stok harus lebih dari 0.", (int)0).show();
                return;
            }
            PembelianStok data = new PembelianStok();
            data.idProduk = produk.idProduk;
            data.idUser = new SessionManager((Context)this).getUserId() == 0 ? 1 : new SessionManager((Context)this).getUserId();
            data.tanggal = tanggal.getText().toString().trim();
            data.supplier = supplier.getText().toString().trim();
            data.jumlah = qty;
            data.hargaBeli = harga;
            data.total = harga * (double)qty;
            data.keterangan = keterangan.getText().toString().trim();
            AppDatabase.databaseWriteExecutor.execute(() -> {
                this.db.runInTransaction(() -> {
                    this.db.pembelianStokDao().insert(data);
                    this.db.produkDao().addStock(data.idProduk, data.jumlah);
                });
                this.runOnUiThread(() -> {
                    Toast.makeText((Context)this, (CharSequence)"Pembelian stok tersimpan.", (int)0).show();
                    this.loadItems();
                });
            });
        }).show();
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}

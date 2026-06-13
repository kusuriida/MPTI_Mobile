package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.lilaclab.dadimadu.activity.BaseListActivity;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import java.util.List;

public class PelangganActivity
extends BaseListActivity {
    protected String title() {
        return "Pelanggan";
    }

    protected String subtitle() {
        return "Kelola data pelanggan Dadi Madu.";
    }

    protected String actionText() {
        return "Tambah";
    }

    protected void onAction() {
        this.showForm(null);
    }

    protected void loadItems() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List items = this.db.pelangganDao().getAll();
            this.runOnUiThread(() -> this.render(items));
        });
    }

    private void render(List<Pelanggan> items) {
        this.listContainer.removeAllViews();
        if (items.isEmpty()) {
            this.showEmpty("Data pelanggan akan muncul setelah transaksi atau ditambah manual.");
            return;
        }
        for (Pelanggan item : items) {
            String body = "No HP   : " + this.safe(item.noHp) + "\nAlamat : " + this.safe(item.alamat) + "\n\n\u25BA Ketuk untuk edit";
            TextView card = this.addCard(item.namaPelanggan, body);
            card.setOnClickListener(v -> this.showForm(item));
        }
    }

    private void showForm(Pelanggan pelanggan) {
        LinearLayout body = this.dialogBody();
        EditText nama = this.input("Nama pelanggan");
        EditText noHp = this.input("Nomor HP");
        EditText alamat = this.input("Alamat");
        body.addView((View)this.label("Nama Pelanggan"));
        body.addView((View)nama);
        body.addView((View)this.label("Nomor HP"));
        body.addView((View)noHp);
        body.addView((View)this.label("Alamat"));
        body.addView((View)alamat);
        if (pelanggan != null) {
            nama.setText((CharSequence)pelanggan.namaPelanggan);
            noHp.setText((CharSequence)pelanggan.noHp);
            alamat.setText((CharSequence)pelanggan.alamat);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this).setTitle((CharSequence)(pelanggan == null ? "Tambah Pelanggan" : "Edit Pelanggan")).setView((View)body).setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Simpan", (dialog, which) -> {
            String namaValue = nama.getText().toString().trim();
            if (namaValue.isEmpty()) {
                Toast.makeText((Context)this, (CharSequence)"Nama pelanggan wajib diisi.", (int)0).show();
                return;
            }
            Pelanggan data = pelanggan == null ? new Pelanggan() : pelanggan;
            data.namaPelanggan = namaValue;
            data.noHp = noHp.getText().toString().trim();
            data.alamat = alamat.getText().toString().trim();
            AppDatabase.databaseWriteExecutor.execute(() -> {
                if (data.idPelanggan > 0) {
                    this.db.pelangganDao().update(data);
                } else {
                    this.db.pelangganDao().insert(data);
                }
                this.runOnUiThread(() -> {
                    Toast.makeText((Context)this, (CharSequence)"Pelanggan tersimpan.", (int)0).show();
                    this.loadItems();
                });
            });
        });
        if (pelanggan != null) {
            builder.setNeutralButton((CharSequence)"Hapus", (dialog, which) -> AppDatabase.databaseWriteExecutor.execute(() -> {
                this.db.pelangganDao().deleteById(pelanggan.idPelanggan);
                this.runOnUiThread(() -> this.loadItems());
            }));
        }
        builder.show();
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }
}

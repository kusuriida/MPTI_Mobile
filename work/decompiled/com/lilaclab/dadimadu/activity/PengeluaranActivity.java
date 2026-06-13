/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 *  androidx.appcompat.app.AlertDialog
 *  androidx.appcompat.app.AlertDialog$Builder
 *  com.lilaclab.dadimadu.R$color
 *  com.lilaclab.dadimadu.R$drawable
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.activity.BaseListActivity
 *  com.lilaclab.dadimadu.activity.PengeluaranActivity
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.entity.Pengeluaran
 *  com.lilaclab.dadimadu.utils.FormatHelper
 *  com.lilaclab.dadimadu.utils.SessionManager
 */
package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.BaseListActivity;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import com.lilaclab.dadimadu.utils.FormatHelper;
import com.lilaclab.dadimadu.utils.SessionManager;
import java.util.List;

public class PengeluaranActivity
extends BaseListActivity {
    protected String title() {
        return "Pengeluaran";
    }

    protected String subtitle() {
        return "Catat biaya botol, label, segel, dan pembelian madu.";
    }

    protected String actionText() {
        return "Tambah";
    }

    protected void onAction() {
        this.showForm();
    }

    protected void loadItems() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List items = this.db.pengeluaranDao().getAll();
            this.runOnUiThread(() -> this.render(items));
        });
    }

    private void render(List<Pengeluaran> items) {
        this.listContainer.removeAllViews();
        if (items.isEmpty()) {
            this.showEmpty("Belum ada pengeluaran yang dicatat.");
            return;
        }
        for (Pengeluaran item : items) {
            TextView card = this.addCard("\ud83d\udcb8 " + item.kategori + " \u2022 " + FormatHelper.rupiah((double)item.jumlahPengeluaran), FormatHelper.tanggalIndo((String)item.tanggal) + "\n" + item.keterangan + "\nKetuk untuk hapus");
            card.setOnClickListener(v -> this.confirmDelete(item));
        }
    }

    private void showForm() {
        LinearLayout body = this.dialogBody();
        EditText tanggal = this.input("Tanggal yyyy-MM-dd");
        tanggal.setText((CharSequence)FormatHelper.today());
        EditText keterangan = this.input("Keterangan");
        EditText jumlah = this.input("Jumlah pengeluaran");
        Spinner kategori = new Spinner((Context)this);
        ArrayAdapter adapter = new ArrayAdapter((Context)this, R.layout.item_spinner_text, (Object[])new String[]{"Botol", "Label", "Segel", "Madu"});
        adapter.setDropDownViewResource(R.layout.item_spinner_text);
        kategori.setAdapter((SpinnerAdapter)adapter);
        kategori.setBackgroundResource(R.drawable.bg_input);
        kategori.setPopupBackgroundResource(R.color.white);
        kategori.setPadding(this.dp(12), 0, this.dp(12), 0);
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(-1, this.dp(50));
        spinnerParams.setMargins(0, 0, 0, this.dp(10));
        kategori.setLayoutParams((ViewGroup.LayoutParams)spinnerParams);
        body.addView((View)kategori);
        body.addView((View)tanggal);
        body.addView((View)keterangan);
        body.addView((View)jumlah);
        AlertDialog dialog = new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Tambah Pengeluaran").setView((View)body).setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Simpan", (dialogInterface, which) -> {
            Pengeluaran data = new Pengeluaran();
            data.idUser = new SessionManager((Context)this).getUserId() == 0 ? 1 : new SessionManager((Context)this).getUserId();
            data.tanggal = tanggal.getText().toString().trim();
            data.kategori = kategori.getSelectedItem().toString();
            data.keterangan = keterangan.getText().toString().trim();
            data.jumlahPengeluaran = FormatHelper.parseMoney((String)jumlah.getText().toString());
            AppDatabase.databaseWriteExecutor.execute(() -> {
                this.db.pengeluaranDao().insert(data);
                this.runOnUiThread(() -> {
                    Toast.makeText((Context)this, (CharSequence)"Pengeluaran tersimpan.", (int)0).show();
                    this.loadItems();
                });
            });
        }).create();
        dialog.setOnShowListener(d -> {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
            }
            dialog.getButton(-2).setTextColor(this.getResources().getColor(R.color.text_muted));
            dialog.getButton(-1).setTextColor(this.getResources().getColor(R.color.amber_dark));
        });
        dialog.show();
    }

    private void confirmDelete(Pengeluaran item) {
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Hapus pengeluaran?").setMessage((CharSequence)(item.kategori + " - " + FormatHelper.rupiah((double)item.jumlahPengeluaran))).setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Hapus", (dialog, which) -> AppDatabase.databaseWriteExecutor.execute(() -> {
            this.db.pengeluaranDao().deleteById(item.idPengeluaran);
            this.runOnUiThread(() -> this.loadItems());
        })).show();
    }
}


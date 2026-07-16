package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import com.lilaclab.dadimadu.database.entity.User;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public class PengaturanActivity
extends AppCompatActivity {
    private static final int REQ_BACKUP = 11;
    private static final int REQ_IMPORT = 12;
    private static final String[] TABLES = {"user", "pelanggan", "produk", "transaksi", "detail_transaksi", "pembelian_stok", "pengeluaran", "pengaturan"};
    private static final String[] DELETE_TABLES = {"detail_transaksi", "transaksi", "pembelian_stok", "pengeluaran", "pelanggan", "produk", "pengaturan", "user"};
    private AppDatabase db;
    private EditText edtNamaBisnis;
    private EditText edtNamaPemilik;
    private EditText edtNoTelepon;
    private EditText edtAlamat;
    private LinearLayout userContainer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pengaturan);
        this.db = AppDatabase.getInstance((Context)this);
        this.edtNamaBisnis = (EditText)this.findViewById(R.id.edtNamaBisnis);
        this.edtNamaPemilik = (EditText)this.findViewById(R.id.edtNamaPemilik);
        this.edtNoTelepon = (EditText)this.findViewById(R.id.edtNoTelepon);
        this.edtAlamat = (EditText)this.findViewById(R.id.edtAlamat);
        this.userContainer = (LinearLayout)this.findViewById(R.id.userContainer);
        this.findViewById(R.id.btnBack).setOnClickListener(v -> this.finish());
        this.findViewById(R.id.btnSimpanPengaturan).setOnClickListener(v -> this.saveSettings());
        this.findViewById(R.id.btnBackupData).setOnClickListener(v -> this.pickBackupFile());
        this.findViewById(R.id.btnImportData).setOnClickListener(v -> this.pickImportFile());
        this.loadSettings();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || data == null || data.getData() == null) {
            return;
        }
        if (requestCode == REQ_BACKUP) {
            this.backupTo(data.getData());
        } else if (requestCode == REQ_IMPORT) {
            Uri uri = data.getData();
            new AlertDialog.Builder((Context)this)
                .setTitle((CharSequence)"Import data?")
                .setMessage((CharSequence)"Data aplikasi saat ini akan diganti dengan isi file backup.")
                .setNegativeButton((CharSequence)"Batal", null)
                .setPositiveButton((CharSequence)"Import", (dialog, which) -> this.importFrom(uri))
                .show();
        }
    }

    private void loadSettings() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Pengaturan pengaturan = this.db.pengaturanDao().get();
            List users = this.db.userDao().getAll();
            this.runOnUiThread(() -> {
                if (pengaturan != null) {
                    this.edtNamaBisnis.setText((CharSequence)pengaturan.namaBisnis);
                    this.edtNamaPemilik.setText((CharSequence)pengaturan.namaPemilik);
                    this.edtNoTelepon.setText((CharSequence)pengaturan.noTelepon);
                    this.edtAlamat.setText((CharSequence)pengaturan.alamat);
                }
                this.renderUsers(users);
            });
        });
    }

    private void saveSettings() {
        Pengaturan pengaturan = new Pengaturan(this.edtNamaBisnis.getText().toString().trim(), this.edtNamaPemilik.getText().toString().trim(), this.edtNoTelepon.getText().toString().trim(), this.edtAlamat.getText().toString().trim());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            this.db.pengaturanDao().upsert(pengaturan);
            this.runOnUiThread(() -> Toast.makeText((Context)this, (CharSequence)"Pengaturan tersimpan.", (int)0).show());
        });
    }

    private void renderUsers(List<User> users) {
        this.userContainer.removeAllViews();
        TextView title = new TextView((Context)this);
        title.setText((CharSequence)"Daftar Pengguna");
        title.setTextColor(this.getResources().getColor(R.color.text_main));
        title.setTextSize(17.0f);
        title.setTypeface(null, 1);
        this.userContainer.addView((View)title);
        for (User user : users) {
            TextView card = new TextView((Context)this);
            card.setText((CharSequence)(user.nama + "\n------------------------------\nUsername : " + user.username + "\nRole     : " + user.role));
            card.setTextColor(this.getResources().getColor(R.color.text_main));
            card.setTextSize(14.0f);
            card.setLineSpacing((float)this.dp(4), 1.0f);
            card.setBackgroundResource(R.drawable.bg_card);
            card.setPadding(this.dp(16), this.dp(14), this.dp(16), this.dp(14));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            params.setMargins(0, this.dp(10), 0, 0);
            this.userContainer.addView((View)card, (ViewGroup.LayoutParams)params);
        }
    }

    private void pickBackupFile() {
        String date = new SimpleDateFormat("yyyyMMdd-HHmm", Locale.US).format(new Date());
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        intent.putExtra(Intent.EXTRA_TITLE, "backup-dadimadu-" + date + ".json");
        this.startActivityForResult(intent, REQ_BACKUP);
    }

    private void pickImportFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        this.startActivityForResult(intent, REQ_IMPORT);
    }

    private void backupTo(Uri uri) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                JSONObject backup = this.exportData();
                try (OutputStream out = this.getContentResolver().openOutputStream(uri, "wt")) {
                    out.write(backup.toString(2).getBytes(StandardCharsets.UTF_8));
                }
                this.runOnUiThread(() -> Toast.makeText((Context)this, (CharSequence)"Backup data berhasil disimpan.", (int)0).show());
            } catch (Exception e) {
                this.runOnUiThread(() -> Toast.makeText((Context)this, (CharSequence)("Backup gagal: " + e.getMessage()), (int)1).show());
            }
        });
    }

    private void importFrom(Uri uri) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                JSONObject backup = new JSONObject(this.readText(uri));
                if (!"DadiMadu".equals(backup.optString("app"))) {
                    throw new IllegalArgumentException("File backup tidak valid.");
                }
                this.importData(backup.getJSONObject("data"));
                this.runOnUiThread(() -> {
                    Toast.makeText((Context)this, (CharSequence)"Import data berhasil.", (int)0).show();
                    this.loadSettings();
                });
            } catch (Exception e) {
                this.runOnUiThread(() -> Toast.makeText((Context)this, (CharSequence)("Import gagal: " + e.getMessage()), (int)1).show());
            }
        });
    }

    private JSONObject exportData() throws Exception {
        SupportSQLiteDatabase sql = this.db.getOpenHelper().getWritableDatabase();
        JSONObject root = new JSONObject();
        JSONObject data = new JSONObject();
        root.put("app", "DadiMadu");
        root.put("version", 1);
        root.put("createdAt", System.currentTimeMillis());
        for (String table : TABLES) {
            data.put(table, this.readTable(sql, table));
        }
        root.put("data", data);
        return root;
    }

    private JSONArray readTable(SupportSQLiteDatabase sql, String table) throws Exception {
        JSONArray rows = new JSONArray();
        try (Cursor cursor = sql.query("SELECT * FROM " + table)) {
            while (cursor.moveToNext()) {
                JSONObject row = new JSONObject();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (cursor.isNull(i)) {
                        row.put(cursor.getColumnName(i), JSONObject.NULL);
                    } else {
                        switch (cursor.getType(i)) {
                            case Cursor.FIELD_TYPE_INTEGER:
                                row.put(cursor.getColumnName(i), cursor.getLong(i));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                row.put(cursor.getColumnName(i), cursor.getDouble(i));
                                break;
                            default:
                                row.put(cursor.getColumnName(i), cursor.getString(i));
                                break;
                        }
                    }
                }
                rows.put(row);
            }
        }
        return rows;
    }

    private void importData(JSONObject data) throws Exception {
        SupportSQLiteDatabase sql = this.db.getOpenHelper().getWritableDatabase();
        sql.beginTransaction();
        try {
            sql.execSQL("PRAGMA foreign_keys=OFF");
            for (String table : DELETE_TABLES) {
                sql.execSQL("DELETE FROM " + table);
            }
            for (String table : TABLES) {
                JSONArray rows = data.optJSONArray(table);
                if (rows == null) continue;
                for (int i = 0; i < rows.length(); i++) {
                    this.insertRow(sql, table, rows.getJSONObject(i));
                }
            }
            sql.execSQL("PRAGMA foreign_keys=ON");
            sql.setTransactionSuccessful();
        } finally {
            sql.endTransaction();
        }
    }

    private void insertRow(SupportSQLiteDatabase sql, String table, JSONObject row) throws Exception {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        JSONArray values = new JSONArray();
        Iterator<String> keys = row.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (columns.length() > 0) {
                columns.append(',');
                placeholders.append(',');
            }
            columns.append(key);
            placeholders.append('?');
            values.put(row.isNull(key) ? null : row.get(key));
        }
        Object[] args = new Object[values.length()];
        for (int i = 0; i < values.length(); i++) {
            args[i] = values.isNull(i) ? null : values.get(i);
        }
        sql.execSQL("INSERT OR REPLACE INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")", args);
    }

    private String readText(Uri uri) throws Exception {
        StringBuilder text = new StringBuilder();
        try (InputStream in = this.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        }
        return text.toString();
    }

    private int dp(int value) {
        return (int)((float)value * this.getResources().getDisplayMetrics().density);
    }
}

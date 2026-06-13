package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import com.lilaclab.dadimadu.database.entity.User;
import java.util.List;

public class PengaturanActivity
extends AppCompatActivity {
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
        this.loadSettings();
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
            card.setText((CharSequence)(user.nama + "\n\nUsername : " + user.username + "\nRole     : " + user.role));
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

    private int dp(int value) {
        return (int)((float)value * this.getResources().getDisplayMetrics().density);
    }
}

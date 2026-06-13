/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 *  androidx.appcompat.app.AlertDialog$Builder
 *  androidx.appcompat.app.AppCompatActivity
 *  androidx.drawerlayout.widget.DrawerLayout
 *  com.lilaclab.dadimadu.R$color
 *  com.lilaclab.dadimadu.R$drawable
 *  com.lilaclab.dadimadu.R$id
 *  com.lilaclab.dadimadu.R$layout
 *  com.lilaclab.dadimadu.activity.LaporanActivity
 *  com.lilaclab.dadimadu.activity.LoginActivity
 *  com.lilaclab.dadimadu.activity.MainActivity
 *  com.lilaclab.dadimadu.activity.PelangganActivity
 *  com.lilaclab.dadimadu.activity.PembelianActivity
 *  com.lilaclab.dadimadu.activity.PengaturanActivity
 *  com.lilaclab.dadimadu.activity.PengeluaranActivity
 *  com.lilaclab.dadimadu.activity.ProdukActivity
 *  com.lilaclab.dadimadu.activity.TransaksiActivity
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.model.DashboardSummary
 *  com.lilaclab.dadimadu.utils.FormatHelper
 *  com.lilaclab.dadimadu.utils.SessionManager
 */
package com.lilaclab.dadimadu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.lilaclab.dadimadu.R;
import com.lilaclab.dadimadu.activity.LaporanActivity;
import com.lilaclab.dadimadu.activity.LoginActivity;
import com.lilaclab.dadimadu.activity.PelangganActivity;
import com.lilaclab.dadimadu.activity.PembelianActivity;
import com.lilaclab.dadimadu.activity.PengaturanActivity;
import com.lilaclab.dadimadu.activity.PengeluaranActivity;
import com.lilaclab.dadimadu.activity.ProdukActivity;
import com.lilaclab.dadimadu.activity.TransaksiActivity;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.model.DashboardSummary;
import com.lilaclab.dadimadu.utils.FormatHelper;
import com.lilaclab.dadimadu.utils.SessionManager;
import java.util.List;

public class MainActivity
extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private TextView txtPemasukan;
    private TextView txtPengeluaran;
    private TextView txtLaba;
    private TextView txtToolbarUser;
    private TextView txtToolbarRole;
    private TextView txtWelcomeName;
    private LinearLayout sectionLowStock;
    private LinearLayout lowStockContainer;
    private AppDatabase db;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.db = AppDatabase.getInstance((Context)this);
        this.session = new SessionManager((Context)this);
        this.bindViews();
        this.bindNavigation();
    }

    protected void onResume() {
        super.onResume();
        this.loadDashboard();
    }

    private void bindViews() {
        this.drawerLayout = (DrawerLayout)this.findViewById(R.id.drawerLayout);
        this.txtPemasukan = (TextView)this.findViewById(R.id.txtPemasukan);
        this.txtPengeluaran = (TextView)this.findViewById(R.id.txtPengeluaran);
        this.txtLaba = (TextView)this.findViewById(R.id.txtLaba);
        this.txtToolbarUser = (TextView)this.findViewById(R.id.txtToolbarUser);
        this.txtToolbarRole = (TextView)this.findViewById(R.id.txtToolbarRole);
        this.txtWelcomeName = (TextView)this.findViewById(R.id.txtWelcomeName);
        this.sectionLowStock = (LinearLayout)this.findViewById(R.id.sectionLowStock);
        this.lowStockContainer = (LinearLayout)this.findViewById(R.id.lowStockContainer);
        this.txtToolbarUser.setText((CharSequence)this.session.getNama());
        this.txtToolbarRole.setText((CharSequence)this.session.getRole());
        this.txtWelcomeName.setText((CharSequence)("Halo, " + this.session.getNama() + ". Kelola usaha madu hari ini."));
        this.findViewById(R.id.btnMenu).setOnClickListener(v -> this.drawerLayout.openDrawer(3));
    }

    private void bindNavigation() {
        this.findViewById(R.id.menuTransaksi).setOnClickListener(v -> this.open(TransaksiActivity.class));
        this.findViewById(R.id.menuProduk).setOnClickListener(v -> this.open(ProdukActivity.class));
        this.findViewById(R.id.menuPelanggan).setOnClickListener(v -> this.open(PelangganActivity.class));
        this.findViewById(R.id.menuLaporan).setOnClickListener(v -> this.open(LaporanActivity.class));
        this.findViewById(R.id.navBeranda).setOnClickListener(v -> this.drawerLayout.closeDrawers());
        this.findViewById(R.id.navTransaksi).setOnClickListener(v -> this.open(TransaksiActivity.class));
        this.findViewById(R.id.navProduk).setOnClickListener(v -> this.open(ProdukActivity.class));
        this.findViewById(R.id.navPelanggan).setOnClickListener(v -> this.open(PelangganActivity.class));
        this.findViewById(R.id.navPembelian).setOnClickListener(v -> this.open(PembelianActivity.class));
        this.findViewById(R.id.navPengeluaran).setOnClickListener(v -> this.open(PengeluaranActivity.class));
        this.findViewById(R.id.navLaporan).setOnClickListener(v -> this.open(LaporanActivity.class));
        this.findViewById(R.id.navPengaturan).setOnClickListener(v -> this.open(PengaturanActivity.class));
        this.findViewById(R.id.btnLogout).setOnClickListener(v -> this.confirmLogout());
    }

    private void loadDashboard() {
        String bulan = FormatHelper.currentMonth();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            double pemasukan = this.db.transaksiDao().sumPemasukanBulan(bulan);
            double pengeluaran = this.db.pengeluaranDao().sumPengeluaranBulan(bulan);
            DashboardSummary summary = new DashboardSummary(pemasukan, pengeluaran, this.db.transaksiDao().countByMonth(bulan), this.db.produkDao().countProduk());
            List lowStock = this.db.produkDao().getLowStock();
            this.runOnUiThread(() -> {
                this.txtPemasukan.setText((CharSequence)FormatHelper.rupiah((double)summary.pemasukan));
                this.txtPengeluaran.setText((CharSequence)FormatHelper.rupiah((double)summary.pengeluaran));
                this.txtLaba.setText((CharSequence)FormatHelper.rupiah((double)summary.laba));
                this.renderLowStock(lowStock);
            });
        });
    }

    private void renderLowStock(List<Produk> products) {
        this.lowStockContainer.removeAllViews();
        this.sectionLowStock.setVisibility(products.isEmpty() ? 8 : 0);
        for (Produk produk : products) {
            TextView view = new TextView((Context)this);
            view.setText((CharSequence)("\u26a0 " + produk.namaProduk + " tersisa " + produk.stok + " " + produk.ukuranKemasan));
            view.setTextColor(this.getResources().getColor(R.color.danger));
            view.setTextSize(13.0f);
            view.setBackgroundResource(R.drawable.bg_warning_card);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            params.setMargins(0, 0, 0, 8);
            this.lowStockContainer.addView((View)view, (ViewGroup.LayoutParams)params);
        }
    }

    private void open(Class<?> target) {
        this.drawerLayout.closeDrawers();
        this.startActivity(new Intent((Context)this, target));
    }

    private void confirmLogout() {
        new AlertDialog.Builder((Context)this).setTitle((CharSequence)"Keluar dari aplikasi?").setMessage((CharSequence)"Sesi login lokal akan dihapus dari perangkat ini.").setNegativeButton((CharSequence)"Batal", null).setPositiveButton((CharSequence)"Keluar", (dialog, which) -> {
            AppDatabase.databaseWriteExecutor.execute(() -> this.db.userDao().logoutAll());
            this.session.clear();
            Intent intent = new Intent((Context)this, LoginActivity.class);
            intent.setFlags(0x10008000);
            this.startActivity(intent);
        }).show();
    }
}


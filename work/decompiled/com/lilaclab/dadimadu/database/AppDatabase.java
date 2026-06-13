/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  androidx.room.Database
 *  androidx.room.Room
 *  androidx.room.RoomDatabase
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.dao.PelangganDao
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao
 *  com.lilaclab.dadimadu.database.dao.ProdukDao
 *  com.lilaclab.dadimadu.database.dao.TransaksiDao
 *  com.lilaclab.dadimadu.database.dao.UserDao
 *  com.lilaclab.dadimadu.database.entity.DetailTransaksi
 *  com.lilaclab.dadimadu.database.entity.Pelanggan
 *  com.lilaclab.dadimadu.database.entity.PembelianStok
 *  com.lilaclab.dadimadu.database.entity.Pengaturan
 *  com.lilaclab.dadimadu.database.entity.Pengeluaran
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.database.entity.Transaksi
 *  com.lilaclab.dadimadu.database.entity.User
 */
package com.lilaclab.dadimadu.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.lilaclab.dadimadu.database.dao.PelangganDao;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao;
import com.lilaclab.dadimadu.database.dao.PengaturanDao;
import com.lilaclab.dadimadu.database.dao.PengeluaranDao;
import com.lilaclab.dadimadu.database.dao.ProdukDao;
import com.lilaclab.dadimadu.database.dao.TransaksiDao;
import com.lilaclab.dadimadu.database.dao.UserDao;
import com.lilaclab.dadimadu.database.entity.DetailTransaksi;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.database.entity.Transaksi;
import com.lilaclab.dadimadu.database.entity.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={User.class, Produk.class, Pelanggan.class, Transaksi.class, DetailTransaksi.class, PembelianStok.class, Pengeluaran.class, Pengaturan.class}, version=1, exportSchema=false)
public abstract class AppDatabase
extends RoomDatabase {
    private static final String DB_NAME = "dadi_madu_room.db";
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor;

    public abstract UserDao userDao();

    public abstract ProdukDao produkDao();

    public abstract PelangganDao pelangganDao();

    public abstract TransaksiDao transaksiDao();

    public abstract PembelianStokDao pembelianStokDao();

    public abstract PengeluaranDao pengeluaranDao();

    public abstract PengaturanDao pengaturanDao();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE != null) return INSTANCE;
        Class<AppDatabase> clazz = AppDatabase.class;
        synchronized (AppDatabase.class) {
            if (INSTANCE != null) return INSTANCE;
            INSTANCE = (AppDatabase)Room.databaseBuilder((Context)context.getApplicationContext(), AppDatabase.class, (String)DB_NAME).fallbackToDestructiveMigration().build();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return INSTANCE;
        }
    }

    static {
        databaseWriteExecutor = Executors.newFixedThreadPool(4);
    }
}


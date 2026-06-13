package com.lilaclab.dadimadu.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.database.entity.User;
import com.lilaclab.dadimadu.utils.FormatHelper;

/*
 * Exception performing whole class analysis ignored.
 */
public class DatabaseSeeder {
    public static void seedIfNeeded(Context context) {
        DatabaseSeeder.seedIfNeeded((Context)context, null);
    }

    public static void seedIfNeeded(Context context, Runnable onDone) {
        AppDatabase db = AppDatabase.getInstance((Context)context);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (db.userDao().countUsers() == 0) {
                db.runInTransaction(() -> {
                    long idUser = db.userDao().insert(new User("Widyono", "admin", "admin123", "pemilik", false));
                    if (idUser <= 0L) {
                        idUser = 1L;
                    }
                    db.produkDao().insert(new Produk("Madu Randu", "Supplier", "250 ml", 45000.0, 30000.0, 25, 5));
                    db.produkDao().insert(new Produk("Madu Kaliandra", "Supplier", "500 ml", 85000.0, 62000.0, 14, 4));
                    db.produkDao().insert(new Produk("Madu Hutan", "Ternak", "1 liter", 150000.0, 110000.0, 8, 3));
                    db.produkDao().insert(new Produk("Madu Klanceng", "Ternak", "250 ml", 70000.0, 50000.0, 6, 3));
                    db.produkDao().insert(new Produk("Madu Kopi", "Supplier", "350 ml", 65000.0, 43000.0, 4, 5));
                    db.pelangganDao().insert(new Pelanggan("Budi", "08123456789", "Sleman"));
                    db.pelangganDao().insert(new Pelanggan("Siti", "082233445566", "Yogyakarta"));
                    Pengeluaran pengeluaran = new Pengeluaran();
                    pengeluaran.idUser = (int)idUser;
                    pengeluaran.tanggal = FormatHelper.today();
                    pengeluaran.kategori = "Botol";
                    pengeluaran.keterangan = "Pembelian botol kaca";
                    pengeluaran.jumlahPengeluaran = 125000.0;
                    db.pengeluaranDao().insert(pengeluaran);
                    PembelianStok pembelian = new PembelianStok();
                    pembelian.idProduk = 1;
                    pembelian.idUser = (int)idUser;
                    pembelian.tanggal = FormatHelper.today();
                    pembelian.supplier = "Supplier Lokal";
                    pembelian.jumlah = 10;
                    pembelian.hargaBeli = 30000.0;
                    pembelian.total = 300000.0;
                    pembelian.keterangan = "Stok awal tambahan";
                    db.pembelianStokDao().insert(pembelian);
                    db.pengaturanDao().upsert(new Pengaturan("Dadi Madu", "Widyono", "08123456789", "Indonesia"));
                });
            }
            if (onDone != null) {
                new Handler(Looper.getMainLooper()).post(onDone);
            }
        });
    }
}


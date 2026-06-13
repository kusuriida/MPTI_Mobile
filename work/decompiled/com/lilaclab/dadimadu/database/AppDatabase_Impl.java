/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  androidx.annotation.NonNull
 *  androidx.room.DatabaseConfiguration
 *  androidx.room.InvalidationTracker
 *  androidx.room.RoomDatabase
 *  androidx.room.RoomOpenHelper
 *  androidx.room.RoomOpenHelper$Delegate
 *  androidx.room.migration.AutoMigrationSpec
 *  androidx.room.migration.Migration
 *  androidx.sqlite.db.SupportSQLiteDatabase
 *  androidx.sqlite.db.SupportSQLiteOpenHelper
 *  androidx.sqlite.db.SupportSQLiteOpenHelper$Callback
 *  androidx.sqlite.db.SupportSQLiteOpenHelper$Configuration
 *  com.lilaclab.dadimadu.database.AppDatabase
 *  com.lilaclab.dadimadu.database.AppDatabase_Impl
 *  com.lilaclab.dadimadu.database.dao.PelangganDao
 *  com.lilaclab.dadimadu.database.dao.PelangganDao_Impl
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao_Impl
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao_Impl
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao_Impl
 *  com.lilaclab.dadimadu.database.dao.ProdukDao
 *  com.lilaclab.dadimadu.database.dao.ProdukDao_Impl
 *  com.lilaclab.dadimadu.database.dao.TransaksiDao
 *  com.lilaclab.dadimadu.database.dao.TransaksiDao_Impl
 *  com.lilaclab.dadimadu.database.dao.UserDao
 *  com.lilaclab.dadimadu.database.dao.UserDao_Impl
 */
package com.lilaclab.dadimadu.database;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.lilaclab.dadimadu.database.AppDatabase;
import com.lilaclab.dadimadu.database.dao.PelangganDao;
import com.lilaclab.dadimadu.database.dao.PelangganDao_Impl;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao_Impl;
import com.lilaclab.dadimadu.database.dao.PengaturanDao;
import com.lilaclab.dadimadu.database.dao.PengaturanDao_Impl;
import com.lilaclab.dadimadu.database.dao.PengeluaranDao;
import com.lilaclab.dadimadu.database.dao.PengeluaranDao_Impl;
import com.lilaclab.dadimadu.database.dao.ProdukDao;
import com.lilaclab.dadimadu.database.dao.ProdukDao_Impl;
import com.lilaclab.dadimadu.database.dao.TransaksiDao;
import com.lilaclab.dadimadu.database.dao.TransaksiDao_Impl;
import com.lilaclab.dadimadu.database.dao.UserDao;
import com.lilaclab.dadimadu.database.dao.UserDao_Impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AppDatabase_Impl
extends AppDatabase {
    private volatile UserDao _userDao;
    private volatile ProdukDao _produkDao;
    private volatile PelangganDao _pelangganDao;
    private volatile TransaksiDao _transaksiDao;
    private volatile PembelianStokDao _pembelianStokDao;
    private volatile PengeluaranDao _pengeluaranDao;
    private volatile PengaturanDao _pengaturanDao;

    @NonNull
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration config) {
        RoomOpenHelper _openCallback = new RoomOpenHelper(config, (RoomOpenHelper.Delegate)new /* Unavailable Anonymous Inner Class!! */, "1a4e574047e92f13e381cd3e5ad9c176", "115a41b6325164e2219732226bb4b52d");
        SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder((Context)config.context).name(config.name).callback((SupportSQLiteOpenHelper.Callback)_openCallback).build();
        SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
        return _helper;
    }

    @NonNull
    protected InvalidationTracker createInvalidationTracker() {
        HashMap _shadowTablesMap = new HashMap(0);
        HashMap _viewTables = new HashMap(0);
        return new InvalidationTracker((RoomDatabase)this, _shadowTablesMap, _viewTables, new String[]{"user", "produk", "pelanggan", "transaksi", "detail_transaksi", "pembelian_stok", "pengeluaran", "pengaturan"});
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
        boolean _supportsDeferForeignKeys = Build.VERSION.SDK_INT >= 21;
        try {
            if (!_supportsDeferForeignKeys) {
                _db.execSQL("PRAGMA foreign_keys = FALSE");
            }
            super.beginTransaction();
            if (_supportsDeferForeignKeys) {
                _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
            }
            _db.execSQL("DELETE FROM `user`");
            _db.execSQL("DELETE FROM `produk`");
            _db.execSQL("DELETE FROM `pelanggan`");
            _db.execSQL("DELETE FROM `transaksi`");
            _db.execSQL("DELETE FROM `detail_transaksi`");
            _db.execSQL("DELETE FROM `pembelian_stok`");
            _db.execSQL("DELETE FROM `pengeluaran`");
            _db.execSQL("DELETE FROM `pengaturan`");
            super.setTransactionSuccessful();
        }
        finally {
            super.endTransaction();
            if (!_supportsDeferForeignKeys) {
                _db.execSQL("PRAGMA foreign_keys = TRUE");
            }
            _db.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!_db.inTransaction()) {
                _db.execSQL("VACUUM");
            }
        }
    }

    @NonNull
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap _typeConvertersMap = new HashMap();
        _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(ProdukDao.class, ProdukDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(PelangganDao.class, PelangganDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(TransaksiDao.class, TransaksiDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(PembelianStokDao.class, PembelianStokDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(PengeluaranDao.class, PengeluaranDao_Impl.getRequiredConverters());
        _typeConvertersMap.put(PengaturanDao.class, PengaturanDao_Impl.getRequiredConverters());
        return _typeConvertersMap;
    }

    @NonNull
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
        return _autoMigrationSpecsSet;
    }

    @NonNull
    public List<Migration> getAutoMigrations(@NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        ArrayList<Migration> _autoMigrations = new ArrayList<Migration>();
        return _autoMigrations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public UserDao userDao() {
        if (this._userDao != null) {
            return this._userDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._userDao == null) {
                this._userDao = new UserDao_Impl((RoomDatabase)this);
            }
            return this._userDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ProdukDao produkDao() {
        if (this._produkDao != null) {
            return this._produkDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._produkDao == null) {
                this._produkDao = new ProdukDao_Impl((RoomDatabase)this);
            }
            return this._produkDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PelangganDao pelangganDao() {
        if (this._pelangganDao != null) {
            return this._pelangganDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._pelangganDao == null) {
                this._pelangganDao = new PelangganDao_Impl((RoomDatabase)this);
            }
            return this._pelangganDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TransaksiDao transaksiDao() {
        if (this._transaksiDao != null) {
            return this._transaksiDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._transaksiDao == null) {
                this._transaksiDao = new TransaksiDao_Impl((RoomDatabase)this);
            }
            return this._transaksiDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PembelianStokDao pembelianStokDao() {
        if (this._pembelianStokDao != null) {
            return this._pembelianStokDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._pembelianStokDao == null) {
                this._pembelianStokDao = new PembelianStokDao_Impl((RoomDatabase)this);
            }
            return this._pembelianStokDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PengeluaranDao pengeluaranDao() {
        if (this._pengeluaranDao != null) {
            return this._pengeluaranDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._pengeluaranDao == null) {
                this._pengeluaranDao = new PengeluaranDao_Impl((RoomDatabase)this);
            }
            return this._pengeluaranDao;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PengaturanDao pengaturanDao() {
        if (this._pengaturanDao != null) {
            return this._pengaturanDao;
        }
        AppDatabase_Impl appDatabase_Impl = this;
        synchronized (appDatabase_Impl) {
            if (this._pengaturanDao == null) {
                this._pengaturanDao = new PengaturanDao_Impl((RoomDatabase)this);
            }
            return this._pengaturanDao;
        }
    }

    static /* synthetic */ List access$000(AppDatabase_Impl x0) {
        return x0.mCallbacks;
    }

    static /* synthetic */ List access$100(AppDatabase_Impl x0) {
        return x0.mCallbacks;
    }

    static /* synthetic */ SupportSQLiteDatabase access$202(AppDatabase_Impl x0, SupportSQLiteDatabase x1) {
        x0.mDatabase = x1;
        return x0.mDatabase;
    }

    static /* synthetic */ void access$300(AppDatabase_Impl x0, SupportSQLiteDatabase x1) {
        x0.internalInitInvalidationTracker(x1);
    }

    static /* synthetic */ List access$400(AppDatabase_Impl x0) {
        return x0.mCallbacks;
    }
}


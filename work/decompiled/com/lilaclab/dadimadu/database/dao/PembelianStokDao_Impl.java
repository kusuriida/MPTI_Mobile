/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  androidx.annotation.NonNull
 *  androidx.room.EntityInsertionAdapter
 *  androidx.room.RoomDatabase
 *  androidx.room.RoomSQLiteQuery
 *  androidx.room.SharedSQLiteStatement
 *  androidx.room.util.DBUtil
 *  androidx.sqlite.db.SupportSQLiteQuery
 *  androidx.sqlite.db.SupportSQLiteStatement
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao_Impl
 *  com.lilaclab.dadimadu.database.entity.PembelianStok
 *  com.lilaclab.dadimadu.model.PembelianListItem
 */
package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.model.PembelianListItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PembelianStokDao_Impl
implements PembelianStokDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<PembelianStok> __insertionAdapterOfPembelianStok;
    private final SharedSQLiteStatement __preparedStmtOfDeleteByProduk;

    public PembelianStokDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPembelianStok = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfDeleteByProduk = new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insert(PembelianStok pembelianStok) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long _result = this.__insertionAdapterOfPembelianStok.insertAndReturnId((Object)pembelianStok);
            this.__db.setTransactionSuccessful();
            long l = _result;
            return l;
        }
        finally {
            this.__db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteByProduk(int idProduk) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteByProduk.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)idProduk);
        try {
            this.__db.beginTransaction();
            try {
                _stmt.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
            }
            finally {
                this.__db.endTransaction();
            }
        }
        finally {
            this.__preparedStmtOfDeleteByProduk.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<PembelianListItem> getAllList() {
        String _sql = "SELECT b.id_pembelian AS idPembelian, p.nama_produk AS namaProduk, b.tanggal, b.supplier, b.jumlah, b.harga_beli AS hargaBeli, b.total, b.keterangan FROM pembelian_stok b INNER JOIN produk p ON b.id_produk = p.id_produk ORDER BY b.tanggal DESC, b.id_pembelian DESC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT b.id_pembelian AS idPembelian, p.nama_produk AS namaProduk, b.tanggal, b.supplier, b.jumlah, b.harga_beli AS hargaBeli, b.total, b.keterangan FROM pembelian_stok b INNER JOIN produk p ON b.id_produk = p.id_produk ORDER BY b.tanggal DESC, b.id_pembelian DESC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            boolean _cursorIndexOfIdPembelian = false;
            boolean _cursorIndexOfNamaProduk = true;
            int _cursorIndexOfTanggal = 2;
            int _cursorIndexOfSupplier = 3;
            int _cursorIndexOfJumlah = 4;
            int _cursorIndexOfHargaBeli = 5;
            int _cursorIndexOfTotal = 6;
            int _cursorIndexOfKeterangan = 7;
            ArrayList<PembelianListItem> _result = new ArrayList<PembelianListItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                PembelianListItem _item = new PembelianListItem();
                _item.idPembelian = _cursor.getInt(0);
                _item.namaProduk = _cursor.isNull(1) ? null : _cursor.getString(1);
                _item.tanggal = _cursor.isNull(2) ? null : _cursor.getString(2);
                _item.supplier = _cursor.isNull(3) ? null : _cursor.getString(3);
                _item.jumlah = _cursor.getInt(4);
                _item.hargaBeli = _cursor.getDouble(5);
                _item.total = _cursor.getDouble(6);
                _item.keterangan = _cursor.isNull(7) ? null : _cursor.getString(7);
                _result.add(_item);
            }
            ArrayList<PembelianListItem> arrayList = _result;
            return arrayList;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public double sumPembelianBulan(String bulan) {
        String _sql = "SELECT IFNULL(SUM(total),0) FROM pembelian_stok WHERE substr(tanggal, 1, 7) = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT IFNULL(SUM(total),0) FROM pembelian_stok WHERE substr(tanggal, 1, 7) = ?", (int)1);
        int _argIndex = 1;
        if (bulan == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, bulan);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            double _result = _cursor.moveToFirst() ? _cursor.getDouble(0) : 0.0;
            double d = _result;
            return d;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int countByProduk(int idProduk) {
        String _sql = "SELECT COUNT(*) FROM pembelian_stok WHERE id_produk = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM pembelian_stok WHERE id_produk = ?", (int)1);
        int _argIndex = 1;
        _statement.bindLong(_argIndex, (long)idProduk);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _result = _cursor.moveToFirst() ? _cursor.getInt(0) : 0;
            int n = _result;
            return n;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    @NonNull
    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}


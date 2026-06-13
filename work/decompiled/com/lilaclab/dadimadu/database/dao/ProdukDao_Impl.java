/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  androidx.annotation.NonNull
 *  androidx.room.EntityDeletionOrUpdateAdapter
 *  androidx.room.EntityInsertionAdapter
 *  androidx.room.RoomDatabase
 *  androidx.room.RoomSQLiteQuery
 *  androidx.room.SharedSQLiteStatement
 *  androidx.room.util.CursorUtil
 *  androidx.room.util.DBUtil
 *  androidx.sqlite.db.SupportSQLiteQuery
 *  androidx.sqlite.db.SupportSQLiteStatement
 *  com.lilaclab.dadimadu.database.dao.ProdukDao
 *  com.lilaclab.dadimadu.database.dao.ProdukDao_Impl
 *  com.lilaclab.dadimadu.database.entity.Produk
 *  com.lilaclab.dadimadu.model.PenjualanProdukItem
 *  com.lilaclab.dadimadu.model.StockReportItem
 */
package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.dao.ProdukDao;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.model.PenjualanProdukItem;
import com.lilaclab.dadimadu.model.StockReportItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProdukDao_Impl
implements ProdukDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Produk> __insertionAdapterOfProduk;
    private final EntityDeletionOrUpdateAdapter<Produk> __deletionAdapterOfProduk;
    private final EntityDeletionOrUpdateAdapter<Produk> __updateAdapterOfProduk;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;
    private final SharedSQLiteStatement __preparedStmtOfChangeStock;
    private final SharedSQLiteStatement __preparedStmtOfReduceStock;
    private final SharedSQLiteStatement __preparedStmtOfAddStock;

    public ProdukDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfProduk = new /* Unavailable Anonymous Inner Class!! */;
        this.__deletionAdapterOfProduk = new /* Unavailable Anonymous Inner Class!! */;
        this.__updateAdapterOfProduk = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfDeleteById = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfChangeStock = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfReduceStock = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfAddStock = new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insert(Produk produk) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long _result = this.__insertionAdapterOfProduk.insertAndReturnId((Object)produk);
            this.__db.setTransactionSuccessful();
            long l = _result;
            return l;
        }
        finally {
            this.__db.endTransaction();
        }
    }

    public void delete(Produk produk) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfProduk.handle((Object)produk);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    public void update(Produk produk) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfProduk.handle((Object)produk);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteById(int idProduk) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteById.acquire();
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
            this.__preparedStmtOfDeleteById.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void changeStock(int idProduk, int delta) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfChangeStock.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)delta);
        _argIndex = 2;
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
            this.__preparedStmtOfChangeStock.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int reduceStock(int idProduk, int qty) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfReduceStock.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)qty);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, (long)idProduk);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, (long)qty);
        try {
            int n;
            this.__db.beginTransaction();
            try {
                int _result = _stmt.executeUpdateDelete();
                this.__db.setTransactionSuccessful();
                n = _result;
            }
            catch (Throwable throwable) {
                this.__db.endTransaction();
                throw throwable;
            }
            this.__db.endTransaction();
            return n;
        }
        finally {
            this.__preparedStmtOfReduceStock.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addStock(int idProduk, int qty) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfAddStock.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)qty);
        _argIndex = 2;
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
            this.__preparedStmtOfAddStock.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Produk> getAll() {
        String _sql = "SELECT * FROM produk ORDER BY nama_produk ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM produk ORDER BY nama_produk ASC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_produk");
            int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_produk");
            int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"jenis_asal");
            int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"ukuran_kemasan");
            int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_jual");
            int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_modal");
            int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"stok");
            int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"minimum_stok");
            ArrayList<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Produk _item = new Produk();
                _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
                _item.namaProduk = _cursor.isNull(_cursorIndexOfNamaProduk) ? null : _cursor.getString(_cursorIndexOfNamaProduk);
                _item.jenisAsal = _cursor.isNull(_cursorIndexOfJenisAsal) ? null : _cursor.getString(_cursorIndexOfJenisAsal);
                _item.ukuranKemasan = _cursor.isNull(_cursorIndexOfUkuranKemasan) ? null : _cursor.getString(_cursorIndexOfUkuranKemasan);
                _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
                _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
                _item.stok = _cursor.getInt(_cursorIndexOfStok);
                _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
                _result.add(_item);
            }
            ArrayList<Produk> arrayList = _result;
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
    public List<Produk> search(String keyword) {
        String _sql = "SELECT * FROM produk WHERE nama_produk LIKE '%' || ? || '%' OR ukuran_kemasan LIKE '%' || ? || '%' ORDER BY nama_produk ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM produk WHERE nama_produk LIKE '%' || ? || '%' OR ukuran_kemasan LIKE '%' || ? || '%' ORDER BY nama_produk ASC", (int)2);
        int _argIndex = 1;
        if (keyword == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, keyword);
        }
        _argIndex = 2;
        if (keyword == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, keyword);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_produk");
            int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_produk");
            int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"jenis_asal");
            int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"ukuran_kemasan");
            int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_jual");
            int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_modal");
            int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"stok");
            int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"minimum_stok");
            ArrayList<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Produk _item = new Produk();
                _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
                _item.namaProduk = _cursor.isNull(_cursorIndexOfNamaProduk) ? null : _cursor.getString(_cursorIndexOfNamaProduk);
                _item.jenisAsal = _cursor.isNull(_cursorIndexOfJenisAsal) ? null : _cursor.getString(_cursorIndexOfJenisAsal);
                _item.ukuranKemasan = _cursor.isNull(_cursorIndexOfUkuranKemasan) ? null : _cursor.getString(_cursorIndexOfUkuranKemasan);
                _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
                _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
                _item.stok = _cursor.getInt(_cursorIndexOfStok);
                _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
                _result.add(_item);
            }
            ArrayList<Produk> arrayList = _result;
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
    public Produk getById(int idProduk) {
        String _sql = "SELECT * FROM produk WHERE id_produk = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM produk WHERE id_produk = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        _statement.bindLong(_argIndex, (long)idProduk);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            Produk _result;
            int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_produk");
            int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_produk");
            int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"jenis_asal");
            int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"ukuran_kemasan");
            int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_jual");
            int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_modal");
            int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"stok");
            int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"minimum_stok");
            if (_cursor.moveToFirst()) {
                _result = new Produk();
                _result.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
                _result.namaProduk = _cursor.isNull(_cursorIndexOfNamaProduk) ? null : _cursor.getString(_cursorIndexOfNamaProduk);
                _result.jenisAsal = _cursor.isNull(_cursorIndexOfJenisAsal) ? null : _cursor.getString(_cursorIndexOfJenisAsal);
                _result.ukuranKemasan = _cursor.isNull(_cursorIndexOfUkuranKemasan) ? null : _cursor.getString(_cursorIndexOfUkuranKemasan);
                _result.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
                _result.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
                _result.stok = _cursor.getInt(_cursorIndexOfStok);
                _result.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
            } else {
                _result = null;
            }
            Produk produk = _result;
            return produk;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Produk> getLowStock() {
        String _sql = "SELECT * FROM produk WHERE stok <= minimum_stok ORDER BY stok ASC, nama_produk ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM produk WHERE stok <= minimum_stok ORDER BY stok ASC, nama_produk ASC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_produk");
            int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_produk");
            int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"jenis_asal");
            int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"ukuran_kemasan");
            int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_jual");
            int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"harga_modal");
            int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"stok");
            int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"minimum_stok");
            ArrayList<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Produk _item = new Produk();
                _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
                _item.namaProduk = _cursor.isNull(_cursorIndexOfNamaProduk) ? null : _cursor.getString(_cursorIndexOfNamaProduk);
                _item.jenisAsal = _cursor.isNull(_cursorIndexOfJenisAsal) ? null : _cursor.getString(_cursorIndexOfJenisAsal);
                _item.ukuranKemasan = _cursor.isNull(_cursorIndexOfUkuranKemasan) ? null : _cursor.getString(_cursorIndexOfUkuranKemasan);
                _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
                _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
                _item.stok = _cursor.getInt(_cursorIndexOfStok);
                _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
                _result.add(_item);
            }
            ArrayList<Produk> arrayList = _result;
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
    public int countProduk() {
        String _sql = "SELECT COUNT(*) FROM produk";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM produk", (int)0);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<StockReportItem> getStockReport() {
        String _sql = "SELECT nama_produk AS namaProduk, ukuran_kemasan AS ukuranKemasan, stok, minimum_stok AS minimumStok, (stok * harga_modal) AS nilaiStok FROM produk ORDER BY stok ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT nama_produk AS namaProduk, ukuran_kemasan AS ukuranKemasan, stok, minimum_stok AS minimumStok, (stok * harga_modal) AS nilaiStok FROM produk ORDER BY stok ASC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            boolean _cursorIndexOfNamaProduk = false;
            boolean _cursorIndexOfUkuranKemasan = true;
            int _cursorIndexOfStok = 2;
            int _cursorIndexOfMinimumStok = 3;
            int _cursorIndexOfNilaiStok = 4;
            ArrayList<StockReportItem> _result = new ArrayList<StockReportItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                StockReportItem _item = new StockReportItem();
                _item.namaProduk = _cursor.isNull(0) ? null : _cursor.getString(0);
                _item.ukuranKemasan = _cursor.isNull(1) ? null : _cursor.getString(1);
                _item.stok = _cursor.getInt(2);
                _item.minimumStok = _cursor.getInt(3);
                _item.nilaiStok = _cursor.getDouble(4);
                _result.add(_item);
            }
            ArrayList<StockReportItem> arrayList = _result;
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
    public List<PenjualanProdukItem> getPenjualanProduk(String bulan) {
        String _sql = "SELECT p.nama_produk AS namaProduk, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.qty ELSE 0 END),0) AS totalQty, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.subtotal ELSE 0 END),0) AS totalPenjualan FROM produk p LEFT JOIN detail_transaksi d ON p.id_produk = d.id_produk LEFT JOIN transaksi t ON d.id_transaksi = t.id_transaksi GROUP BY p.id_produk ORDER BY totalPenjualan DESC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT p.nama_produk AS namaProduk, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.qty ELSE 0 END),0) AS totalQty, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.subtotal ELSE 0 END),0) AS totalPenjualan FROM produk p LEFT JOIN detail_transaksi d ON p.id_produk = d.id_produk LEFT JOIN transaksi t ON d.id_transaksi = t.id_transaksi GROUP BY p.id_produk ORDER BY totalPenjualan DESC", (int)2);
        int _argIndex = 1;
        if (bulan == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, bulan);
        }
        _argIndex = 2;
        if (bulan == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, bulan);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            boolean _cursorIndexOfNamaProduk = false;
            boolean _cursorIndexOfTotalQty = true;
            int _cursorIndexOfTotalPenjualan = 2;
            ArrayList<PenjualanProdukItem> _result = new ArrayList<PenjualanProdukItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                PenjualanProdukItem _item = new PenjualanProdukItem();
                _item.namaProduk = _cursor.isNull(0) ? null : _cursor.getString(0);
                _item.totalQty = _cursor.getInt(1);
                _item.totalPenjualan = _cursor.getDouble(2);
                _result.add(_item);
            }
            ArrayList<PenjualanProdukItem> arrayList = _result;
            return arrayList;
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


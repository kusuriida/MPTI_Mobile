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
 *  androidx.room.util.CursorUtil
 *  androidx.room.util.DBUtil
 *  androidx.sqlite.db.SupportSQLiteQuery
 *  androidx.sqlite.db.SupportSQLiteStatement
 *  com.lilaclab.dadimadu.database.dao.TransaksiDao
 *  com.lilaclab.dadimadu.database.dao.TransaksiDao_Impl
 *  com.lilaclab.dadimadu.database.entity.DetailTransaksi
 *  com.lilaclab.dadimadu.database.entity.Transaksi
 *  com.lilaclab.dadimadu.model.DetailTransaksiItem
 *  com.lilaclab.dadimadu.model.TransaksiListItem
 */
package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.dao.TransaksiDao;
import com.lilaclab.dadimadu.database.entity.DetailTransaksi;
import com.lilaclab.dadimadu.database.entity.Transaksi;
import com.lilaclab.dadimadu.model.DetailTransaksiItem;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TransaksiDao_Impl
implements TransaksiDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Transaksi> __insertionAdapterOfTransaksi;
    private final EntityInsertionAdapter<DetailTransaksi> __insertionAdapterOfDetailTransaksi;
    private final SharedSQLiteStatement __preparedStmtOfDeleteDetailsByProduk;

    public TransaksiDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfTransaksi = new /* Unavailable Anonymous Inner Class!! */;
        this.__insertionAdapterOfDetailTransaksi = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfDeleteDetailsByProduk = new /* Unavailable Anonymous Inner Class!! */;
    }

    public void insert(Transaksi transaksi) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfTransaksi.insert((Object)transaksi);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    public void insertDetails(List<DetailTransaksi> details) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfDetailTransaksi.insert(details);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteDetailsByProduk(int idProduk) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteDetailsByProduk.acquire();
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
            this.__preparedStmtOfDeleteDetailsByProduk.release(_stmt);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TransaksiListItem> getAllList() {
        String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan ORDER BY t.create_at DESC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan ORDER BY t.create_at DESC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            boolean _cursorIndexOfIdTransaksi = false;
            boolean _cursorIndexOfNamaPelanggan = true;
            int _cursorIndexOfTanggal = 2;
            int _cursorIndexOfMetodeBayar = 3;
            int _cursorIndexOfStatusBayar = 4;
            int _cursorIndexOfGrandtotal = 5;
            ArrayList<TransaksiListItem> _result = new ArrayList<TransaksiListItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                TransaksiListItem _item = new TransaksiListItem();
                _item.idTransaksi = _cursor.isNull(0) ? null : _cursor.getString(0);
                _item.namaPelanggan = _cursor.isNull(1) ? null : _cursor.getString(1);
                _item.tanggal = _cursor.isNull(2) ? null : _cursor.getString(2);
                _item.metodeBayar = _cursor.isNull(3) ? null : _cursor.getString(3);
                _item.statusBayar = _cursor.isNull(4) ? null : _cursor.getString(4);
                _item.grandtotal = _cursor.getDouble(5);
                _result.add(_item);
            }
            ArrayList<TransaksiListItem> arrayList = _result;
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
    public List<TransaksiListItem> search(String keyword) {
        String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi LIKE '%' || ? || '%' OR p.nama_pelanggan LIKE '%' || ? || '%' ORDER BY t.create_at DESC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi LIKE '%' || ? || '%' OR p.nama_pelanggan LIKE '%' || ? || '%' ORDER BY t.create_at DESC", (int)2);
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
            boolean _cursorIndexOfIdTransaksi = false;
            boolean _cursorIndexOfNamaPelanggan = true;
            int _cursorIndexOfTanggal = 2;
            int _cursorIndexOfMetodeBayar = 3;
            int _cursorIndexOfStatusBayar = 4;
            int _cursorIndexOfGrandtotal = 5;
            ArrayList<TransaksiListItem> _result = new ArrayList<TransaksiListItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                TransaksiListItem _item = new TransaksiListItem();
                _item.idTransaksi = _cursor.isNull(0) ? null : _cursor.getString(0);
                _item.namaPelanggan = _cursor.isNull(1) ? null : _cursor.getString(1);
                _item.tanggal = _cursor.isNull(2) ? null : _cursor.getString(2);
                _item.metodeBayar = _cursor.isNull(3) ? null : _cursor.getString(3);
                _item.statusBayar = _cursor.isNull(4) ? null : _cursor.getString(4);
                _item.grandtotal = _cursor.getDouble(5);
                _result.add(_item);
            }
            ArrayList<TransaksiListItem> arrayList = _result;
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
    public TransaksiListItem getListItemById(String idTransaksi) {
        String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        if (idTransaksi == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, idTransaksi);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            TransaksiListItem _result;
            boolean _cursorIndexOfIdTransaksi = false;
            boolean _cursorIndexOfNamaPelanggan = true;
            int _cursorIndexOfTanggal = 2;
            int _cursorIndexOfMetodeBayar = 3;
            int _cursorIndexOfStatusBayar = 4;
            int _cursorIndexOfGrandtotal = 5;
            if (_cursor.moveToFirst()) {
                _result = new TransaksiListItem();
                _result.idTransaksi = _cursor.isNull(0) ? null : _cursor.getString(0);
                _result.namaPelanggan = _cursor.isNull(1) ? null : _cursor.getString(1);
                _result.tanggal = _cursor.isNull(2) ? null : _cursor.getString(2);
                _result.metodeBayar = _cursor.isNull(3) ? null : _cursor.getString(3);
                _result.statusBayar = _cursor.isNull(4) ? null : _cursor.getString(4);
                _result.grandtotal = _cursor.getDouble(5);
            } else {
                _result = null;
            }
            TransaksiListItem transaksiListItem = _result;
            return transaksiListItem;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Transaksi getById(String idTransaksi) {
        String _sql = "SELECT * FROM transaksi WHERE id_transaksi = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM transaksi WHERE id_transaksi = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        if (idTransaksi == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, idTransaksi);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            Transaksi _result;
            int _cursorIndexOfIdTransaksi = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_transaksi");
            int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pelanggan");
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"tanggal");
            int _cursorIndexOfOngkir = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"ongkir");
            int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"metode_bayar");
            int _cursorIndexOfStatusBayar = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"status_bayar");
            int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"subtotal");
            int _cursorIndexOfGrandtotal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"grandtotal");
            int _cursorIndexOfCreateAt = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"create_at");
            if (_cursor.moveToFirst()) {
                _result = new Transaksi();
                _result.idTransaksi = _cursor.isNull(_cursorIndexOfIdTransaksi) ? null : _cursor.getString(_cursorIndexOfIdTransaksi);
                _result.idPelanggan = _cursor.isNull(_cursorIndexOfIdPelanggan) ? null : Integer.valueOf(_cursor.getInt(_cursorIndexOfIdPelanggan));
                _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _result.tanggal = _cursor.isNull(_cursorIndexOfTanggal) ? null : _cursor.getString(_cursorIndexOfTanggal);
                _result.ongkir = _cursor.getDouble(_cursorIndexOfOngkir);
                _result.metodeBayar = _cursor.isNull(_cursorIndexOfMetodeBayar) ? null : _cursor.getString(_cursorIndexOfMetodeBayar);
                _result.statusBayar = _cursor.isNull(_cursorIndexOfStatusBayar) ? null : _cursor.getString(_cursorIndexOfStatusBayar);
                _result.subtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
                _result.grandtotal = _cursor.getDouble(_cursorIndexOfGrandtotal);
                _result.createAt = _cursor.getLong(_cursorIndexOfCreateAt);
            } else {
                _result = null;
            }
            Transaksi transaksi = _result;
            return transaksi;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DetailTransaksiItem> getDetailItems(String idTransaksi) {
        String _sql = "SELECT p.nama_produk AS namaProduk, p.ukuran_kemasan AS ukuranKemasan, d.qty, d.harga_saat_transaksi AS hargaSaatTransaksi, d.subtotal FROM detail_transaksi d INNER JOIN produk p ON d.id_produk = p.id_produk WHERE d.id_transaksi = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT p.nama_produk AS namaProduk, p.ukuran_kemasan AS ukuranKemasan, d.qty, d.harga_saat_transaksi AS hargaSaatTransaksi, d.subtotal FROM detail_transaksi d INNER JOIN produk p ON d.id_produk = p.id_produk WHERE d.id_transaksi = ?", (int)1);
        int _argIndex = 1;
        if (idTransaksi == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, idTransaksi);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            boolean _cursorIndexOfNamaProduk = false;
            boolean _cursorIndexOfUkuranKemasan = true;
            int _cursorIndexOfQty = 2;
            int _cursorIndexOfHargaSaatTransaksi = 3;
            int _cursorIndexOfSubtotal = 4;
            ArrayList<DetailTransaksiItem> _result = new ArrayList<DetailTransaksiItem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                DetailTransaksiItem _item = new DetailTransaksiItem();
                _item.namaProduk = _cursor.isNull(0) ? null : _cursor.getString(0);
                _item.ukuranKemasan = _cursor.isNull(1) ? null : _cursor.getString(1);
                _item.qty = _cursor.getInt(2);
                _item.hargaSaatTransaksi = _cursor.getDouble(3);
                _item.subtotal = _cursor.getDouble(4);
                _result.add(_item);
            }
            ArrayList<DetailTransaksiItem> arrayList = _result;
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
    public int countByPrefix(String prefix) {
        String _sql = "SELECT COUNT(*) FROM transaksi WHERE id_transaksi LIKE ? || '%'";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM transaksi WHERE id_transaksi LIKE ? || '%'", (int)1);
        int _argIndex = 1;
        if (prefix == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, prefix);
        }
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
    public int countAll() {
        String _sql = "SELECT COUNT(*) FROM transaksi";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM transaksi", (int)0);
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
    public double sumPemasukanBulan(String bulan) {
        String _sql = "SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas' AND substr(tanggal, 1, 7) = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas' AND substr(tanggal, 1, 7) = ?", (int)1);
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
    public double sumPemasukanAll() {
        String _sql = "SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas'";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas'", (int)0);
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
    public int countByMonth(String bulan) {
        String _sql = "SELECT COUNT(*) FROM transaksi WHERE substr(tanggal, 1, 7) = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM transaksi WHERE substr(tanggal, 1, 7) = ?", (int)1);
        int _argIndex = 1;
        if (bulan == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, bulan);
        }
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
    public int countDetailsByProduk(int idProduk) {
        String _sql = "SELECT COUNT(*) FROM detail_transaksi WHERE id_produk = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT COUNT(*) FROM detail_transaksi WHERE id_produk = ?", (int)1);
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


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
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao_Impl
 *  com.lilaclab.dadimadu.database.entity.Pengeluaran
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
import com.lilaclab.dadimadu.database.dao.PengeluaranDao;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PengeluaranDao_Impl
implements PengeluaranDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Pengeluaran> __insertionAdapterOfPengeluaran;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;

    public PengeluaranDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPengeluaran = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfDeleteById = new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insert(Pengeluaran pengeluaran) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long _result = this.__insertionAdapterOfPengeluaran.insertAndReturnId((Object)pengeluaran);
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
    public void deleteById(int idPengeluaran) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)idPengeluaran);
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
    public List<Pengeluaran> getAll() {
        String _sql = "SELECT * FROM pengeluaran ORDER BY tanggal DESC, id_pengeluaran DESC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pengeluaran ORDER BY tanggal DESC, id_pengeluaran DESC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdPengeluaran = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pengeluaran");
            int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_user");
            int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"tanggal");
            int _cursorIndexOfKategori = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"kategori");
            int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"keterangan");
            int _cursorIndexOfJumlahPengeluaran = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"jumlah_pengeluaran");
            ArrayList<Pengeluaran> _result = new ArrayList<Pengeluaran>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Pengeluaran _item = new Pengeluaran();
                _item.idPengeluaran = _cursor.getInt(_cursorIndexOfIdPengeluaran);
                _item.idUser = _cursor.getInt(_cursorIndexOfIdUser);
                _item.tanggal = _cursor.isNull(_cursorIndexOfTanggal) ? null : _cursor.getString(_cursorIndexOfTanggal);
                _item.kategori = _cursor.isNull(_cursorIndexOfKategori) ? null : _cursor.getString(_cursorIndexOfKategori);
                _item.keterangan = _cursor.isNull(_cursorIndexOfKeterangan) ? null : _cursor.getString(_cursorIndexOfKeterangan);
                _item.jumlahPengeluaran = _cursor.getDouble(_cursorIndexOfJumlahPengeluaran);
                _result.add(_item);
            }
            ArrayList<Pengeluaran> arrayList = _result;
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
    public double sumPengeluaranBulan(String bulan) {
        String _sql = "SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran WHERE substr(tanggal, 1, 7) = ?";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran WHERE substr(tanggal, 1, 7) = ?", (int)1);
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
    public double sumPengeluaranAll() {
        String _sql = "SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran", (int)0);
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

    @NonNull
    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}


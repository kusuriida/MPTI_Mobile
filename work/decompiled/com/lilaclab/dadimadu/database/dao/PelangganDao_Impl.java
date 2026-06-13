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
 *  com.lilaclab.dadimadu.database.dao.PelangganDao
 *  com.lilaclab.dadimadu.database.dao.PelangganDao_Impl
 *  com.lilaclab.dadimadu.database.entity.Pelanggan
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
import com.lilaclab.dadimadu.database.dao.PelangganDao;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PelangganDao_Impl
implements PelangganDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Pelanggan> __insertionAdapterOfPelanggan;
    private final EntityDeletionOrUpdateAdapter<Pelanggan> __updateAdapterOfPelanggan;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;

    public PelangganDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPelanggan = new /* Unavailable Anonymous Inner Class!! */;
        this.__updateAdapterOfPelanggan = new /* Unavailable Anonymous Inner Class!! */;
        this.__preparedStmtOfDeleteById = new /* Unavailable Anonymous Inner Class!! */;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long insert(Pelanggan pelanggan) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long _result = this.__insertionAdapterOfPelanggan.insertAndReturnId((Object)pelanggan);
            this.__db.setTransactionSuccessful();
            long l = _result;
            return l;
        }
        finally {
            this.__db.endTransaction();
        }
    }

    public void update(Pelanggan pelanggan) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfPelanggan.handle((Object)pelanggan);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteById(int idPelanggan) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, (long)idPelanggan);
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
    public List<Pelanggan> getAll() {
        String _sql = "SELECT * FROM pelanggan ORDER BY nama_pelanggan ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pelanggan ORDER BY nama_pelanggan ASC", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pelanggan");
            int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_pelanggan");
            int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"no_hp");
            int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"alamat");
            ArrayList<Pelanggan> _result = new ArrayList<Pelanggan>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Pelanggan _item = new Pelanggan();
                _item.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
                _item.namaPelanggan = _cursor.isNull(_cursorIndexOfNamaPelanggan) ? null : _cursor.getString(_cursorIndexOfNamaPelanggan);
                _item.noHp = _cursor.isNull(_cursorIndexOfNoHp) ? null : _cursor.getString(_cursorIndexOfNoHp);
                _item.alamat = _cursor.isNull(_cursorIndexOfAlamat) ? null : _cursor.getString(_cursorIndexOfAlamat);
                _result.add(_item);
            }
            ArrayList<Pelanggan> arrayList = _result;
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
    public List<Pelanggan> search(String keyword) {
        String _sql = "SELECT * FROM pelanggan WHERE nama_pelanggan LIKE '%' || ? || '%' OR no_hp LIKE '%' || ? || '%' ORDER BY nama_pelanggan ASC";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pelanggan WHERE nama_pelanggan LIKE '%' || ? || '%' OR no_hp LIKE '%' || ? || '%' ORDER BY nama_pelanggan ASC", (int)2);
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
            int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pelanggan");
            int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_pelanggan");
            int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"no_hp");
            int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"alamat");
            ArrayList<Pelanggan> _result = new ArrayList<Pelanggan>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                Pelanggan _item = new Pelanggan();
                _item.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
                _item.namaPelanggan = _cursor.isNull(_cursorIndexOfNamaPelanggan) ? null : _cursor.getString(_cursorIndexOfNamaPelanggan);
                _item.noHp = _cursor.isNull(_cursorIndexOfNoHp) ? null : _cursor.getString(_cursorIndexOfNoHp);
                _item.alamat = _cursor.isNull(_cursorIndexOfAlamat) ? null : _cursor.getString(_cursorIndexOfAlamat);
                _result.add(_item);
            }
            ArrayList<Pelanggan> arrayList = _result;
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
    public Pelanggan getById(int idPelanggan) {
        String _sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pelanggan WHERE id_pelanggan = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        _statement.bindLong(_argIndex, (long)idPelanggan);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            Pelanggan _result;
            int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pelanggan");
            int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_pelanggan");
            int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"no_hp");
            int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"alamat");
            if (_cursor.moveToFirst()) {
                _result = new Pelanggan();
                _result.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
                _result.namaPelanggan = _cursor.isNull(_cursorIndexOfNamaPelanggan) ? null : _cursor.getString(_cursorIndexOfNamaPelanggan);
                _result.noHp = _cursor.isNull(_cursorIndexOfNoHp) ? null : _cursor.getString(_cursorIndexOfNoHp);
                _result.alamat = _cursor.isNull(_cursorIndexOfAlamat) ? null : _cursor.getString(_cursorIndexOfAlamat);
            } else {
                _result = null;
            }
            Pelanggan pelanggan = _result;
            return pelanggan;
        }
        finally {
            _cursor.close();
            _statement.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pelanggan findByName(String nama) {
        String _sql = "SELECT * FROM pelanggan WHERE nama_pelanggan = ? LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pelanggan WHERE nama_pelanggan = ? LIMIT 1", (int)1);
        int _argIndex = 1;
        if (nama == null) {
            _statement.bindNull(_argIndex);
        } else {
            _statement.bindString(_argIndex, nama);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            Pelanggan _result;
            int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id_pelanggan");
            int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_pelanggan");
            int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"no_hp");
            int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"alamat");
            if (_cursor.moveToFirst()) {
                _result = new Pelanggan();
                _result.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
                _result.namaPelanggan = _cursor.isNull(_cursorIndexOfNamaPelanggan) ? null : _cursor.getString(_cursorIndexOfNamaPelanggan);
                _result.noHp = _cursor.isNull(_cursorIndexOfNoHp) ? null : _cursor.getString(_cursorIndexOfNoHp);
                _result.alamat = _cursor.isNull(_cursorIndexOfAlamat) ? null : _cursor.getString(_cursorIndexOfAlamat);
            } else {
                _result = null;
            }
            Pelanggan pelanggan = _result;
            return pelanggan;
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


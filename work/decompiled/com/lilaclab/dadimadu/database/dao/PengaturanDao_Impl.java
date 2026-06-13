/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  androidx.annotation.NonNull
 *  androidx.room.EntityInsertionAdapter
 *  androidx.room.RoomDatabase
 *  androidx.room.RoomSQLiteQuery
 *  androidx.room.util.CursorUtil
 *  androidx.room.util.DBUtil
 *  androidx.sqlite.db.SupportSQLiteQuery
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao_Impl
 *  com.lilaclab.dadimadu.database.entity.Pengaturan
 */
package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import com.lilaclab.dadimadu.database.dao.PengaturanDao;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import java.util.Collections;
import java.util.List;

public final class PengaturanDao_Impl
implements PengaturanDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<Pengaturan> __insertionAdapterOfPengaturan;

    public PengaturanDao_Impl(@NonNull RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfPengaturan = new /* Unavailable Anonymous Inner Class!! */;
    }

    public void upsert(Pengaturan pengaturan) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfPengaturan.insert((Object)pengaturan);
            this.__db.setTransactionSuccessful();
        }
        finally {
            this.__db.endTransaction();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Pengaturan get() {
        String _sql = "SELECT * FROM pengaturan WHERE id = 1 LIMIT 1";
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire((String)"SELECT * FROM pengaturan WHERE id = 1 LIMIT 1", (int)0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query((RoomDatabase)this.__db, (SupportSQLiteQuery)_statement, (boolean)false, null);
        try {
            Pengaturan _result;
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"id");
            int _cursorIndexOfNamaBisnis = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_bisnis");
            int _cursorIndexOfNamaPemilik = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"nama_pemilik");
            int _cursorIndexOfNoTelepon = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"no_telepon");
            int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow((Cursor)_cursor, (String)"alamat");
            if (_cursor.moveToFirst()) {
                _result = new Pengaturan();
                _result.id = _cursor.getInt(_cursorIndexOfId);
                _result.namaBisnis = _cursor.isNull(_cursorIndexOfNamaBisnis) ? null : _cursor.getString(_cursorIndexOfNamaBisnis);
                _result.namaPemilik = _cursor.isNull(_cursorIndexOfNamaPemilik) ? null : _cursor.getString(_cursorIndexOfNamaPemilik);
                _result.noTelepon = _cursor.isNull(_cursorIndexOfNoTelepon) ? null : _cursor.getString(_cursorIndexOfNoTelepon);
                _result.alamat = _cursor.isNull(_cursorIndexOfAlamat) ? null : _cursor.getString(_cursorIndexOfAlamat);
            } else {
                _result = null;
            }
            Pengaturan pengaturan = _result;
            return pengaturan;
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


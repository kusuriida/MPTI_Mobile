package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.Pengaturan;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PengaturanDao_Impl implements PengaturanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pengaturan> __insertionAdapterOfPengaturan;

  public PengaturanDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPengaturan = new EntityInsertionAdapter<Pengaturan>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pengaturan` (`id`,`nama_bisnis`,`nama_pemilik`,`no_telepon`,`alamat`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pengaturan value) {
        stmt.bindLong(1, value.id);
        if (value.namaBisnis == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.namaBisnis);
        }
        if (value.namaPemilik == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.namaPemilik);
        }
        if (value.noTelepon == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.noTelepon);
        }
        if (value.alamat == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.alamat);
        }
      }
    };
  }

  @Override
  public void upsert(final Pengaturan pengaturan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPengaturan.insert(pengaturan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Pengaturan get() {
    final String _sql = "SELECT * FROM pengaturan WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNamaBisnis = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_bisnis");
      final int _cursorIndexOfNamaPemilik = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_pemilik");
      final int _cursorIndexOfNoTelepon = CursorUtil.getColumnIndexOrThrow(_cursor, "no_telepon");
      final int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow(_cursor, "alamat");
      final Pengaturan _result;
      if(_cursor.moveToFirst()) {
        _result = new Pengaturan();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfNamaBisnis)) {
          _result.namaBisnis = null;
        } else {
          _result.namaBisnis = _cursor.getString(_cursorIndexOfNamaBisnis);
        }
        if (_cursor.isNull(_cursorIndexOfNamaPemilik)) {
          _result.namaPemilik = null;
        } else {
          _result.namaPemilik = _cursor.getString(_cursorIndexOfNamaPemilik);
        }
        if (_cursor.isNull(_cursorIndexOfNoTelepon)) {
          _result.noTelepon = null;
        } else {
          _result.noTelepon = _cursor.getString(_cursorIndexOfNoTelepon);
        }
        if (_cursor.isNull(_cursorIndexOfAlamat)) {
          _result.alamat = null;
        } else {
          _result.alamat = _cursor.getString(_cursorIndexOfAlamat);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

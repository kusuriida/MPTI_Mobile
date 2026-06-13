package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PelangganDao_Impl implements PelangganDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pelanggan> __insertionAdapterOfPelanggan;

  private final EntityDeletionOrUpdateAdapter<Pelanggan> __updateAdapterOfPelanggan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public PelangganDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPelanggan = new EntityInsertionAdapter<Pelanggan>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pelanggan` (`id_pelanggan`,`nama_pelanggan`,`no_hp`,`alamat`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pelanggan value) {
        stmt.bindLong(1, value.idPelanggan);
        if (value.namaPelanggan == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.namaPelanggan);
        }
        if (value.noHp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.noHp);
        }
        if (value.alamat == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.alamat);
        }
      }
    };
    this.__updateAdapterOfPelanggan = new EntityDeletionOrUpdateAdapter<Pelanggan>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `pelanggan` SET `id_pelanggan` = ?,`nama_pelanggan` = ?,`no_hp` = ?,`alamat` = ? WHERE `id_pelanggan` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pelanggan value) {
        stmt.bindLong(1, value.idPelanggan);
        if (value.namaPelanggan == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.namaPelanggan);
        }
        if (value.noHp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.noHp);
        }
        if (value.alamat == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.alamat);
        }
        stmt.bindLong(5, value.idPelanggan);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Pelanggan pelanggan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPelanggan.insertAndReturnId(pelanggan);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Pelanggan pelanggan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPelanggan.handle(pelanggan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final int idPelanggan) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idPelanggan);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public List<Pelanggan> getAll() {
    final String _sql = "SELECT * FROM pelanggan ORDER BY nama_pelanggan ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pelanggan");
      final int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_pelanggan");
      final int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow(_cursor, "no_hp");
      final int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow(_cursor, "alamat");
      final List<Pelanggan> _result = new ArrayList<Pelanggan>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Pelanggan _item;
        _item = new Pelanggan();
        _item.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _item.namaPelanggan = null;
        } else {
          _item.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfNoHp)) {
          _item.noHp = null;
        } else {
          _item.noHp = _cursor.getString(_cursorIndexOfNoHp);
        }
        if (_cursor.isNull(_cursorIndexOfAlamat)) {
          _item.alamat = null;
        } else {
          _item.alamat = _cursor.getString(_cursorIndexOfAlamat);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Pelanggan> search(final String keyword) {
    final String _sql = "SELECT * FROM pelanggan WHERE nama_pelanggan LIKE '%' || ? || '%' OR no_hp LIKE '%' || ? || '%' ORDER BY nama_pelanggan ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
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
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pelanggan");
      final int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_pelanggan");
      final int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow(_cursor, "no_hp");
      final int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow(_cursor, "alamat");
      final List<Pelanggan> _result = new ArrayList<Pelanggan>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Pelanggan _item;
        _item = new Pelanggan();
        _item.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _item.namaPelanggan = null;
        } else {
          _item.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfNoHp)) {
          _item.noHp = null;
        } else {
          _item.noHp = _cursor.getString(_cursorIndexOfNoHp);
        }
        if (_cursor.isNull(_cursorIndexOfAlamat)) {
          _item.alamat = null;
        } else {
          _item.alamat = _cursor.getString(_cursorIndexOfAlamat);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Pelanggan getById(final int idPelanggan) {
    final String _sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idPelanggan);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pelanggan");
      final int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_pelanggan");
      final int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow(_cursor, "no_hp");
      final int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow(_cursor, "alamat");
      final Pelanggan _result;
      if(_cursor.moveToFirst()) {
        _result = new Pelanggan();
        _result.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _result.namaPelanggan = null;
        } else {
          _result.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfNoHp)) {
          _result.noHp = null;
        } else {
          _result.noHp = _cursor.getString(_cursorIndexOfNoHp);
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

  @Override
  public Pelanggan findByName(final String nama) {
    final String _sql = "SELECT * FROM pelanggan WHERE nama_pelanggan = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (nama == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, nama);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pelanggan");
      final int _cursorIndexOfNamaPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_pelanggan");
      final int _cursorIndexOfNoHp = CursorUtil.getColumnIndexOrThrow(_cursor, "no_hp");
      final int _cursorIndexOfAlamat = CursorUtil.getColumnIndexOrThrow(_cursor, "alamat");
      final Pelanggan _result;
      if(_cursor.moveToFirst()) {
        _result = new Pelanggan();
        _result.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _result.namaPelanggan = null;
        } else {
          _result.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfNoHp)) {
          _result.noHp = null;
        } else {
          _result.noHp = _cursor.getString(_cursorIndexOfNoHp);
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

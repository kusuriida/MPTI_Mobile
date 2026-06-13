package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PengeluaranDao_Impl implements PengeluaranDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pengeluaran> __insertionAdapterOfPengeluaran;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public PengeluaranDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPengeluaran = new EntityInsertionAdapter<Pengeluaran>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pengeluaran` (`id_pengeluaran`,`id_user`,`tanggal`,`kategori`,`keterangan`,`jumlah_pengeluaran`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Pengeluaran value) {
        stmt.bindLong(1, value.idPengeluaran);
        stmt.bindLong(2, value.idUser);
        if (value.tanggal == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.tanggal);
        }
        if (value.kategori == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.kategori);
        }
        if (value.keterangan == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.keterangan);
        }
        stmt.bindDouble(6, value.jumlahPengeluaran);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pengeluaran WHERE id_pengeluaran = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Pengeluaran pengeluaran) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPengeluaran.insertAndReturnId(pengeluaran);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final int idPengeluaran) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idPengeluaran);
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
  public List<Pengeluaran> getAll() {
    final String _sql = "SELECT * FROM pengeluaran ORDER BY tanggal DESC, id_pengeluaran DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPengeluaran = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pengeluaran");
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
      final int _cursorIndexOfKategori = CursorUtil.getColumnIndexOrThrow(_cursor, "kategori");
      final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
      final int _cursorIndexOfJumlahPengeluaran = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah_pengeluaran");
      final List<Pengeluaran> _result = new ArrayList<Pengeluaran>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Pengeluaran _item;
        _item = new Pengeluaran();
        _item.idPengeluaran = _cursor.getInt(_cursorIndexOfIdPengeluaran);
        _item.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _item.tanggal = null;
        } else {
          _item.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        if (_cursor.isNull(_cursorIndexOfKategori)) {
          _item.kategori = null;
        } else {
          _item.kategori = _cursor.getString(_cursorIndexOfKategori);
        }
        if (_cursor.isNull(_cursorIndexOfKeterangan)) {
          _item.keterangan = null;
        } else {
          _item.keterangan = _cursor.getString(_cursorIndexOfKeterangan);
        }
        _item.jumlahPengeluaran = _cursor.getDouble(_cursorIndexOfJumlahPengeluaran);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double sumPengeluaranBulan(final String bulan) {
    final String _sql = "SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran WHERE substr(tanggal, 1, 7) = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bulan == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bulan);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double sumPengeluaranAll() {
    final String _sql = "SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
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

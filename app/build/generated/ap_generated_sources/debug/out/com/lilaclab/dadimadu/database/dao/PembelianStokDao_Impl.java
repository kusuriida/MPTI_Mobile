package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.model.PembelianListItem;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PembelianStokDao_Impl implements PembelianStokDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PembelianStok> __insertionAdapterOfPembelianStok;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByProduk;

  public PembelianStokDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPembelianStok = new EntityInsertionAdapter<PembelianStok>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `pembelian_stok` (`id_pembelian`,`id_produk`,`id_user`,`tanggal`,`supplier`,`jumlah`,`harga_beli`,`total`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PembelianStok value) {
        stmt.bindLong(1, value.idPembelian);
        stmt.bindLong(2, value.idProduk);
        stmt.bindLong(3, value.idUser);
        if (value.tanggal == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.tanggal);
        }
        if (value.supplier == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.supplier);
        }
        stmt.bindLong(6, value.jumlah);
        stmt.bindDouble(7, value.hargaBeli);
        stmt.bindDouble(8, value.total);
        if (value.keterangan == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.keterangan);
        }
      }
    };
    this.__preparedStmtOfDeleteByProduk = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pembelian_stok WHERE id_produk = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final PembelianStok pembelianStok) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPembelianStok.insertAndReturnId(pembelianStok);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByProduk(final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByProduk.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idProduk);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByProduk.release(_stmt);
    }
  }

  @Override
  public List<PembelianListItem> getAllList() {
    final String _sql = "SELECT b.id_pembelian AS idPembelian, p.nama_produk AS namaProduk, b.tanggal, b.supplier, b.jumlah, b.harga_beli AS hargaBeli, b.total, b.keterangan FROM pembelian_stok b INNER JOIN produk p ON b.id_produk = p.id_produk ORDER BY b.tanggal DESC, b.id_pembelian DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdPembelian = 0;
      final int _cursorIndexOfNamaProduk = 1;
      final int _cursorIndexOfTanggal = 2;
      final int _cursorIndexOfSupplier = 3;
      final int _cursorIndexOfJumlah = 4;
      final int _cursorIndexOfHargaBeli = 5;
      final int _cursorIndexOfTotal = 6;
      final int _cursorIndexOfKeterangan = 7;
      final List<PembelianListItem> _result = new ArrayList<PembelianListItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PembelianListItem _item;
        _item = new PembelianListItem();
        _item.idPembelian = _cursor.getInt(_cursorIndexOfIdPembelian);
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _item.tanggal = null;
        } else {
          _item.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        if (_cursor.isNull(_cursorIndexOfSupplier)) {
          _item.supplier = null;
        } else {
          _item.supplier = _cursor.getString(_cursorIndexOfSupplier);
        }
        _item.jumlah = _cursor.getInt(_cursorIndexOfJumlah);
        _item.hargaBeli = _cursor.getDouble(_cursorIndexOfHargaBeli);
        _item.total = _cursor.getDouble(_cursorIndexOfTotal);
        if (_cursor.isNull(_cursorIndexOfKeterangan)) {
          _item.keterangan = null;
        } else {
          _item.keterangan = _cursor.getString(_cursorIndexOfKeterangan);
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
  public double sumPembelianBulan(final String bulan) {
    final String _sql = "SELECT IFNULL(SUM(total),0) FROM pembelian_stok WHERE substr(tanggal, 1, 7) = ?";
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
  public int countByProduk(final int idProduk) {
    final String _sql = "SELECT COUNT(*) FROM pembelian_stok WHERE id_produk = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idProduk);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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

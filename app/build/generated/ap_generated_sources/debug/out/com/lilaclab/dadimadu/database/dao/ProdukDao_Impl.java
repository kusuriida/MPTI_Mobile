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
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.model.PenjualanProdukItem;
import com.lilaclab.dadimadu.model.StockReportItem;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProdukDao_Impl implements ProdukDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Produk> __insertionAdapterOfProduk;

  private final EntityDeletionOrUpdateAdapter<Produk> __deletionAdapterOfProduk;

  private final EntityDeletionOrUpdateAdapter<Produk> __updateAdapterOfProduk;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfChangeStock;

  private final SharedSQLiteStatement __preparedStmtOfReduceStock;

  private final SharedSQLiteStatement __preparedStmtOfAddStock;

  public ProdukDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduk = new EntityInsertionAdapter<Produk>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `produk` (`id_produk`,`nama_produk`,`jenis_asal`,`ukuran_kemasan`,`harga_jual`,`harga_modal`,`stok`,`minimum_stok`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Produk value) {
        stmt.bindLong(1, value.idProduk);
        if (value.namaProduk == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.namaProduk);
        }
        if (value.jenisAsal == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.jenisAsal);
        }
        if (value.ukuranKemasan == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.ukuranKemasan);
        }
        stmt.bindDouble(5, value.hargaJual);
        stmt.bindDouble(6, value.hargaModal);
        stmt.bindLong(7, value.stok);
        stmt.bindLong(8, value.minimumStok);
      }
    };
    this.__deletionAdapterOfProduk = new EntityDeletionOrUpdateAdapter<Produk>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `produk` WHERE `id_produk` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Produk value) {
        stmt.bindLong(1, value.idProduk);
      }
    };
    this.__updateAdapterOfProduk = new EntityDeletionOrUpdateAdapter<Produk>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `produk` SET `id_produk` = ?,`nama_produk` = ?,`jenis_asal` = ?,`ukuran_kemasan` = ?,`harga_jual` = ?,`harga_modal` = ?,`stok` = ?,`minimum_stok` = ? WHERE `id_produk` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Produk value) {
        stmt.bindLong(1, value.idProduk);
        if (value.namaProduk == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.namaProduk);
        }
        if (value.jenisAsal == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.jenisAsal);
        }
        if (value.ukuranKemasan == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.ukuranKemasan);
        }
        stmt.bindDouble(5, value.hargaJual);
        stmt.bindDouble(6, value.hargaModal);
        stmt.bindLong(7, value.stok);
        stmt.bindLong(8, value.minimumStok);
        stmt.bindLong(9, value.idProduk);
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM produk WHERE id_produk = ?";
        return _query;
      }
    };
    this.__preparedStmtOfChangeStock = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE produk SET stok = MAX(0, stok + ?) WHERE id_produk = ?";
        return _query;
      }
    };
    this.__preparedStmtOfReduceStock = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE produk SET stok = stok - ? WHERE id_produk = ? AND stok >= ?";
        return _query;
      }
    };
    this.__preparedStmtOfAddStock = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE produk SET stok = stok + ? WHERE id_produk = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Produk produk) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfProduk.insertAndReturnId(produk);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Produk produk) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfProduk.handle(produk);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Produk produk) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfProduk.handle(produk);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idProduk);
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
  public void changeStock(final int delta, final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfChangeStock.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, delta);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, idProduk);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfChangeStock.release(_stmt);
    }
  }

  @Override
  public int reduceStock(final int qty, final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfReduceStock.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, qty);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, idProduk);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, qty);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfReduceStock.release(_stmt);
    }
  }

  @Override
  public void addStock(final int qty, final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfAddStock.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, qty);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, idProduk);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfAddStock.release(_stmt);
    }
  }

  @Override
  public List<Produk> getAll() {
    final String _sql = "SELECT * FROM produk ORDER BY nama_produk ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "id_produk");
      final int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_produk");
      final int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow(_cursor, "jenis_asal");
      final int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow(_cursor, "ukuran_kemasan");
      final int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_jual");
      final int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_modal");
      final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
      final int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_stok");
      final List<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Produk _item;
        _item = new Produk();
        _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfJenisAsal)) {
          _item.jenisAsal = null;
        } else {
          _item.jenisAsal = _cursor.getString(_cursorIndexOfJenisAsal);
        }
        if (_cursor.isNull(_cursorIndexOfUkuranKemasan)) {
          _item.ukuranKemasan = null;
        } else {
          _item.ukuranKemasan = _cursor.getString(_cursorIndexOfUkuranKemasan);
        }
        _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
        _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
        _item.stok = _cursor.getInt(_cursorIndexOfStok);
        _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Produk> search(final String keyword) {
    final String _sql = "SELECT * FROM produk WHERE nama_produk LIKE '%' || ? || '%' OR ukuran_kemasan LIKE '%' || ? || '%' ORDER BY nama_produk ASC";
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
      final int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "id_produk");
      final int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_produk");
      final int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow(_cursor, "jenis_asal");
      final int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow(_cursor, "ukuran_kemasan");
      final int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_jual");
      final int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_modal");
      final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
      final int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_stok");
      final List<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Produk _item;
        _item = new Produk();
        _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfJenisAsal)) {
          _item.jenisAsal = null;
        } else {
          _item.jenisAsal = _cursor.getString(_cursorIndexOfJenisAsal);
        }
        if (_cursor.isNull(_cursorIndexOfUkuranKemasan)) {
          _item.ukuranKemasan = null;
        } else {
          _item.ukuranKemasan = _cursor.getString(_cursorIndexOfUkuranKemasan);
        }
        _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
        _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
        _item.stok = _cursor.getInt(_cursorIndexOfStok);
        _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Produk getById(final int idProduk) {
    final String _sql = "SELECT * FROM produk WHERE id_produk = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idProduk);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "id_produk");
      final int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_produk");
      final int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow(_cursor, "jenis_asal");
      final int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow(_cursor, "ukuran_kemasan");
      final int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_jual");
      final int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_modal");
      final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
      final int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_stok");
      final Produk _result;
      if(_cursor.moveToFirst()) {
        _result = new Produk();
        _result.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _result.namaProduk = null;
        } else {
          _result.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfJenisAsal)) {
          _result.jenisAsal = null;
        } else {
          _result.jenisAsal = _cursor.getString(_cursorIndexOfJenisAsal);
        }
        if (_cursor.isNull(_cursorIndexOfUkuranKemasan)) {
          _result.ukuranKemasan = null;
        } else {
          _result.ukuranKemasan = _cursor.getString(_cursorIndexOfUkuranKemasan);
        }
        _result.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
        _result.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
        _result.stok = _cursor.getInt(_cursorIndexOfStok);
        _result.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
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
  public List<Produk> getLowStock() {
    final String _sql = "SELECT * FROM produk WHERE stok <= minimum_stok ORDER BY stok ASC, nama_produk ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "id_produk");
      final int _cursorIndexOfNamaProduk = CursorUtil.getColumnIndexOrThrow(_cursor, "nama_produk");
      final int _cursorIndexOfJenisAsal = CursorUtil.getColumnIndexOrThrow(_cursor, "jenis_asal");
      final int _cursorIndexOfUkuranKemasan = CursorUtil.getColumnIndexOrThrow(_cursor, "ukuran_kemasan");
      final int _cursorIndexOfHargaJual = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_jual");
      final int _cursorIndexOfHargaModal = CursorUtil.getColumnIndexOrThrow(_cursor, "harga_modal");
      final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
      final int _cursorIndexOfMinimumStok = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_stok");
      final List<Produk> _result = new ArrayList<Produk>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Produk _item;
        _item = new Produk();
        _item.idProduk = _cursor.getInt(_cursorIndexOfIdProduk);
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfJenisAsal)) {
          _item.jenisAsal = null;
        } else {
          _item.jenisAsal = _cursor.getString(_cursorIndexOfJenisAsal);
        }
        if (_cursor.isNull(_cursorIndexOfUkuranKemasan)) {
          _item.ukuranKemasan = null;
        } else {
          _item.ukuranKemasan = _cursor.getString(_cursorIndexOfUkuranKemasan);
        }
        _item.hargaJual = _cursor.getDouble(_cursorIndexOfHargaJual);
        _item.hargaModal = _cursor.getDouble(_cursorIndexOfHargaModal);
        _item.stok = _cursor.getInt(_cursorIndexOfStok);
        _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countProduk() {
    final String _sql = "SELECT COUNT(*) FROM produk";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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

  @Override
  public List<StockReportItem> getStockReport() {
    final String _sql = "SELECT nama_produk AS namaProduk, ukuran_kemasan AS ukuranKemasan, stok, minimum_stok AS minimumStok, (stok * harga_modal) AS nilaiStok FROM produk ORDER BY stok ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNamaProduk = 0;
      final int _cursorIndexOfUkuranKemasan = 1;
      final int _cursorIndexOfStok = 2;
      final int _cursorIndexOfMinimumStok = 3;
      final int _cursorIndexOfNilaiStok = 4;
      final List<StockReportItem> _result = new ArrayList<StockReportItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final StockReportItem _item;
        _item = new StockReportItem();
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        if (_cursor.isNull(_cursorIndexOfUkuranKemasan)) {
          _item.ukuranKemasan = null;
        } else {
          _item.ukuranKemasan = _cursor.getString(_cursorIndexOfUkuranKemasan);
        }
        _item.stok = _cursor.getInt(_cursorIndexOfStok);
        _item.minimumStok = _cursor.getInt(_cursorIndexOfMinimumStok);
        _item.nilaiStok = _cursor.getDouble(_cursorIndexOfNilaiStok);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PenjualanProdukItem> getPenjualanProduk(final String bulan) {
    final String _sql = "SELECT p.nama_produk AS namaProduk, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.qty ELSE 0 END),0) AS totalQty, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = ? THEN d.subtotal ELSE 0 END),0) AS totalPenjualan FROM produk p LEFT JOIN detail_transaksi d ON p.id_produk = d.id_produk LEFT JOIN transaksi t ON d.id_transaksi = t.id_transaksi GROUP BY p.id_produk ORDER BY totalPenjualan DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
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
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNamaProduk = 0;
      final int _cursorIndexOfTotalQty = 1;
      final int _cursorIndexOfTotalPenjualan = 2;
      final List<PenjualanProdukItem> _result = new ArrayList<PenjualanProdukItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PenjualanProdukItem _item;
        _item = new PenjualanProdukItem();
        if (_cursor.isNull(_cursorIndexOfNamaProduk)) {
          _item.namaProduk = null;
        } else {
          _item.namaProduk = _cursor.getString(_cursorIndexOfNamaProduk);
        }
        _item.totalQty = _cursor.getInt(_cursorIndexOfTotalQty);
        _item.totalPenjualan = _cursor.getDouble(_cursorIndexOfTotalPenjualan);
        _result.add(_item);
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

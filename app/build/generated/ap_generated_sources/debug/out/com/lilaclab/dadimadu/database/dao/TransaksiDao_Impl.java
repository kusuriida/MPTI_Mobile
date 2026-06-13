package com.lilaclab.dadimadu.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lilaclab.dadimadu.database.entity.DetailTransaksi;
import com.lilaclab.dadimadu.database.entity.Transaksi;
import com.lilaclab.dadimadu.model.DetailTransaksiItem;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TransaksiDao_Impl implements TransaksiDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Transaksi> __insertionAdapterOfTransaksi;

  private final EntityInsertionAdapter<DetailTransaksi> __insertionAdapterOfDetailTransaksi;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDetailsByProduk;

  public TransaksiDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransaksi = new EntityInsertionAdapter<Transaksi>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `transaksi` (`id_transaksi`,`id_pelanggan`,`id_user`,`tanggal`,`ongkir`,`metode_bayar`,`status_bayar`,`subtotal`,`grandtotal`,`create_at`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Transaksi value) {
        if (value.idTransaksi == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.idTransaksi);
        }
        if (value.idPelanggan == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.idPelanggan);
        }
        stmt.bindLong(3, value.idUser);
        if (value.tanggal == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.tanggal);
        }
        stmt.bindDouble(5, value.ongkir);
        if (value.metodeBayar == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.metodeBayar);
        }
        if (value.statusBayar == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.statusBayar);
        }
        stmt.bindDouble(8, value.subtotal);
        stmt.bindDouble(9, value.grandtotal);
        stmt.bindLong(10, value.createAt);
      }
    };
    this.__insertionAdapterOfDetailTransaksi = new EntityInsertionAdapter<DetailTransaksi>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `detail_transaksi` (`id_transaksi`,`id_produk`,`qty`,`harga_saat_transaksi`,`subtotal`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DetailTransaksi value) {
        if (value.idTransaksi == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.idTransaksi);
        }
        stmt.bindLong(2, value.idProduk);
        stmt.bindLong(3, value.qty);
        stmt.bindDouble(4, value.hargaSaatTransaksi);
        stmt.bindDouble(5, value.subtotal);
      }
    };
    this.__preparedStmtOfDeleteDetailsByProduk = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM detail_transaksi WHERE id_produk = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Transaksi transaksi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTransaksi.insert(transaksi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDetails(final List<DetailTransaksi> details) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDetailTransaksi.insert(details);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteDetailsByProduk(final int idProduk) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDetailsByProduk.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, idProduk);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteDetailsByProduk.release(_stmt);
    }
  }

  @Override
  public List<TransaksiListItem> getAllList() {
    final String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan ORDER BY t.create_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdTransaksi = 0;
      final int _cursorIndexOfNamaPelanggan = 1;
      final int _cursorIndexOfTanggal = 2;
      final int _cursorIndexOfMetodeBayar = 3;
      final int _cursorIndexOfStatusBayar = 4;
      final int _cursorIndexOfGrandtotal = 5;
      final List<TransaksiListItem> _result = new ArrayList<TransaksiListItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TransaksiListItem _item;
        _item = new TransaksiListItem();
        if (_cursor.isNull(_cursorIndexOfIdTransaksi)) {
          _item.idTransaksi = null;
        } else {
          _item.idTransaksi = _cursor.getString(_cursorIndexOfIdTransaksi);
        }
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _item.namaPelanggan = null;
        } else {
          _item.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _item.tanggal = null;
        } else {
          _item.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
          _item.metodeBayar = null;
        } else {
          _item.metodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
        }
        if (_cursor.isNull(_cursorIndexOfStatusBayar)) {
          _item.statusBayar = null;
        } else {
          _item.statusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
        }
        _item.grandtotal = _cursor.getDouble(_cursorIndexOfGrandtotal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TransaksiListItem> search(final String keyword) {
    final String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi LIKE '%' || ? || '%' OR p.nama_pelanggan LIKE '%' || ? || '%' ORDER BY t.create_at DESC";
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
      final int _cursorIndexOfIdTransaksi = 0;
      final int _cursorIndexOfNamaPelanggan = 1;
      final int _cursorIndexOfTanggal = 2;
      final int _cursorIndexOfMetodeBayar = 3;
      final int _cursorIndexOfStatusBayar = 4;
      final int _cursorIndexOfGrandtotal = 5;
      final List<TransaksiListItem> _result = new ArrayList<TransaksiListItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TransaksiListItem _item;
        _item = new TransaksiListItem();
        if (_cursor.isNull(_cursorIndexOfIdTransaksi)) {
          _item.idTransaksi = null;
        } else {
          _item.idTransaksi = _cursor.getString(_cursorIndexOfIdTransaksi);
        }
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _item.namaPelanggan = null;
        } else {
          _item.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _item.tanggal = null;
        } else {
          _item.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
          _item.metodeBayar = null;
        } else {
          _item.metodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
        }
        if (_cursor.isNull(_cursorIndexOfStatusBayar)) {
          _item.statusBayar = null;
        } else {
          _item.statusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
        }
        _item.grandtotal = _cursor.getDouble(_cursorIndexOfGrandtotal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TransaksiListItem getListItemById(final String idTransaksi) {
    final String _sql = "SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idTransaksi == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idTransaksi);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdTransaksi = 0;
      final int _cursorIndexOfNamaPelanggan = 1;
      final int _cursorIndexOfTanggal = 2;
      final int _cursorIndexOfMetodeBayar = 3;
      final int _cursorIndexOfStatusBayar = 4;
      final int _cursorIndexOfGrandtotal = 5;
      final TransaksiListItem _result;
      if(_cursor.moveToFirst()) {
        _result = new TransaksiListItem();
        if (_cursor.isNull(_cursorIndexOfIdTransaksi)) {
          _result.idTransaksi = null;
        } else {
          _result.idTransaksi = _cursor.getString(_cursorIndexOfIdTransaksi);
        }
        if (_cursor.isNull(_cursorIndexOfNamaPelanggan)) {
          _result.namaPelanggan = null;
        } else {
          _result.namaPelanggan = _cursor.getString(_cursorIndexOfNamaPelanggan);
        }
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _result.tanggal = null;
        } else {
          _result.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
          _result.metodeBayar = null;
        } else {
          _result.metodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
        }
        if (_cursor.isNull(_cursorIndexOfStatusBayar)) {
          _result.statusBayar = null;
        } else {
          _result.statusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
        }
        _result.grandtotal = _cursor.getDouble(_cursorIndexOfGrandtotal);
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
  public Transaksi getById(final String idTransaksi) {
    final String _sql = "SELECT * FROM transaksi WHERE id_transaksi = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idTransaksi == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idTransaksi);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdTransaksi = CursorUtil.getColumnIndexOrThrow(_cursor, "id_transaksi");
      final int _cursorIndexOfIdPelanggan = CursorUtil.getColumnIndexOrThrow(_cursor, "id_pelanggan");
      final int _cursorIndexOfIdUser = CursorUtil.getColumnIndexOrThrow(_cursor, "id_user");
      final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
      final int _cursorIndexOfOngkir = CursorUtil.getColumnIndexOrThrow(_cursor, "ongkir");
      final int _cursorIndexOfMetodeBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "metode_bayar");
      final int _cursorIndexOfStatusBayar = CursorUtil.getColumnIndexOrThrow(_cursor, "status_bayar");
      final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
      final int _cursorIndexOfGrandtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "grandtotal");
      final int _cursorIndexOfCreateAt = CursorUtil.getColumnIndexOrThrow(_cursor, "create_at");
      final Transaksi _result;
      if(_cursor.moveToFirst()) {
        _result = new Transaksi();
        if (_cursor.isNull(_cursorIndexOfIdTransaksi)) {
          _result.idTransaksi = null;
        } else {
          _result.idTransaksi = _cursor.getString(_cursorIndexOfIdTransaksi);
        }
        if (_cursor.isNull(_cursorIndexOfIdPelanggan)) {
          _result.idPelanggan = null;
        } else {
          _result.idPelanggan = _cursor.getInt(_cursorIndexOfIdPelanggan);
        }
        _result.idUser = _cursor.getInt(_cursorIndexOfIdUser);
        if (_cursor.isNull(_cursorIndexOfTanggal)) {
          _result.tanggal = null;
        } else {
          _result.tanggal = _cursor.getString(_cursorIndexOfTanggal);
        }
        _result.ongkir = _cursor.getDouble(_cursorIndexOfOngkir);
        if (_cursor.isNull(_cursorIndexOfMetodeBayar)) {
          _result.metodeBayar = null;
        } else {
          _result.metodeBayar = _cursor.getString(_cursorIndexOfMetodeBayar);
        }
        if (_cursor.isNull(_cursorIndexOfStatusBayar)) {
          _result.statusBayar = null;
        } else {
          _result.statusBayar = _cursor.getString(_cursorIndexOfStatusBayar);
        }
        _result.subtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
        _result.grandtotal = _cursor.getDouble(_cursorIndexOfGrandtotal);
        _result.createAt = _cursor.getLong(_cursorIndexOfCreateAt);
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
  public List<DetailTransaksiItem> getDetailItems(final String idTransaksi) {
    final String _sql = "SELECT p.nama_produk AS namaProduk, p.ukuran_kemasan AS ukuranKemasan, d.qty, d.harga_saat_transaksi AS hargaSaatTransaksi, d.subtotal FROM detail_transaksi d INNER JOIN produk p ON d.id_produk = p.id_produk WHERE d.id_transaksi = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idTransaksi == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idTransaksi);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNamaProduk = 0;
      final int _cursorIndexOfUkuranKemasan = 1;
      final int _cursorIndexOfQty = 2;
      final int _cursorIndexOfHargaSaatTransaksi = 3;
      final int _cursorIndexOfSubtotal = 4;
      final List<DetailTransaksiItem> _result = new ArrayList<DetailTransaksiItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DetailTransaksiItem _item;
        _item = new DetailTransaksiItem();
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
        _item.qty = _cursor.getInt(_cursorIndexOfQty);
        _item.hargaSaatTransaksi = _cursor.getDouble(_cursorIndexOfHargaSaatTransaksi);
        _item.subtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countByPrefix(final String prefix) {
    final String _sql = "SELECT COUNT(*) FROM transaksi WHERE id_transaksi LIKE ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (prefix == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, prefix);
    }
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
  public int countAll() {
    final String _sql = "SELECT COUNT(*) FROM transaksi";
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
  public double sumPemasukanBulan(final String bulan) {
    final String _sql = "SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas' AND substr(tanggal, 1, 7) = ?";
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
  public double sumPemasukanAll() {
    final String _sql = "SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas'";
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

  @Override
  public int countByMonth(final String bulan) {
    final String _sql = "SELECT COUNT(*) FROM transaksi WHERE substr(tanggal, 1, 7) = ?";
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
  public int countDetailsByProduk(final int idProduk) {
    final String _sql = "SELECT COUNT(*) FROM detail_transaksi WHERE id_produk = ?";
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

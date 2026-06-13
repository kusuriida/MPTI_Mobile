package com.lilaclab.dadimadu.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.lilaclab.dadimadu.database.dao.PelangganDao;
import com.lilaclab.dadimadu.database.dao.PelangganDao_Impl;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao;
import com.lilaclab.dadimadu.database.dao.PembelianStokDao_Impl;
import com.lilaclab.dadimadu.database.dao.PengaturanDao;
import com.lilaclab.dadimadu.database.dao.PengaturanDao_Impl;
import com.lilaclab.dadimadu.database.dao.PengeluaranDao;
import com.lilaclab.dadimadu.database.dao.PengeluaranDao_Impl;
import com.lilaclab.dadimadu.database.dao.ProdukDao;
import com.lilaclab.dadimadu.database.dao.ProdukDao_Impl;
import com.lilaclab.dadimadu.database.dao.TransaksiDao;
import com.lilaclab.dadimadu.database.dao.TransaksiDao_Impl;
import com.lilaclab.dadimadu.database.dao.UserDao;
import com.lilaclab.dadimadu.database.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile ProdukDao _produkDao;

  private volatile PelangganDao _pelangganDao;

  private volatile TransaksiDao _transaksiDao;

  private volatile PembelianStokDao _pembelianStokDao;

  private volatile PengeluaranDao _pengeluaranDao;

  private volatile PengaturanDao _pengaturanDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id_user` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT, `username` TEXT, `password` TEXT, `role` TEXT, `is_logged_in` INTEGER NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_user_username` ON `user` (`username`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `produk` (`id_produk` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama_produk` TEXT, `jenis_asal` TEXT, `ukuran_kemasan` TEXT, `harga_jual` REAL NOT NULL, `harga_modal` REAL NOT NULL, `stok` INTEGER NOT NULL, `minimum_stok` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pelanggan` (`id_pelanggan` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama_pelanggan` TEXT, `no_hp` TEXT, `alamat` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `transaksi` (`id_transaksi` TEXT NOT NULL, `id_pelanggan` INTEGER, `id_user` INTEGER NOT NULL, `tanggal` TEXT, `ongkir` REAL NOT NULL, `metode_bayar` TEXT, `status_bayar` TEXT, `subtotal` REAL NOT NULL, `grandtotal` REAL NOT NULL, `create_at` INTEGER NOT NULL, PRIMARY KEY(`id_transaksi`), FOREIGN KEY(`id_pelanggan`) REFERENCES `pelanggan`(`id_pelanggan`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`id_user`) REFERENCES `user`(`id_user`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_transaksi_id_pelanggan` ON `transaksi` (`id_pelanggan`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_transaksi_id_user` ON `transaksi` (`id_user`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `detail_transaksi` (`id_transaksi` TEXT NOT NULL, `id_produk` INTEGER NOT NULL, `qty` INTEGER NOT NULL, `harga_saat_transaksi` REAL NOT NULL, `subtotal` REAL NOT NULL, PRIMARY KEY(`id_transaksi`, `id_produk`), FOREIGN KEY(`id_transaksi`) REFERENCES `transaksi`(`id_transaksi`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`id_produk`) REFERENCES `produk`(`id_produk`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_detail_transaksi_id_transaksi` ON `detail_transaksi` (`id_transaksi`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_detail_transaksi_id_produk` ON `detail_transaksi` (`id_produk`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pembelian_stok` (`id_pembelian` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_produk` INTEGER NOT NULL, `id_user` INTEGER NOT NULL, `tanggal` TEXT, `supplier` TEXT, `jumlah` INTEGER NOT NULL, `harga_beli` REAL NOT NULL, `total` REAL NOT NULL, `keterangan` TEXT, FOREIGN KEY(`id_produk`) REFERENCES `produk`(`id_produk`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`id_user`) REFERENCES `user`(`id_user`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_pembelian_stok_id_produk` ON `pembelian_stok` (`id_produk`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_pembelian_stok_id_user` ON `pembelian_stok` (`id_user`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pengeluaran` (`id_pengeluaran` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_user` INTEGER NOT NULL, `tanggal` TEXT, `kategori` TEXT, `keterangan` TEXT, `jumlah_pengeluaran` REAL NOT NULL, FOREIGN KEY(`id_user`) REFERENCES `user`(`id_user`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_pengeluaran_id_user` ON `pengeluaran` (`id_user`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pengaturan` (`id` INTEGER NOT NULL, `nama_bisnis` TEXT, `nama_pemilik` TEXT, `no_telepon` TEXT, `alamat` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1a4e574047e92f13e381cd3e5ad9c176')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `user`");
        _db.execSQL("DROP TABLE IF EXISTS `produk`");
        _db.execSQL("DROP TABLE IF EXISTS `pelanggan`");
        _db.execSQL("DROP TABLE IF EXISTS `transaksi`");
        _db.execSQL("DROP TABLE IF EXISTS `detail_transaksi`");
        _db.execSQL("DROP TABLE IF EXISTS `pembelian_stok`");
        _db.execSQL("DROP TABLE IF EXISTS `pengeluaran`");
        _db.execSQL("DROP TABLE IF EXISTS `pengaturan`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(6);
        _columnsUser.put("id_user", new TableInfo.Column("id_user", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("nama", new TableInfo.Column("nama", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("role", new TableInfo.Column("role", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("is_logged_in", new TableInfo.Column("is_logged_in", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(1);
        _indicesUser.add(new TableInfo.Index("index_user_username", true, Arrays.asList("username"), Arrays.asList("ASC")));
        final TableInfo _infoUser = new TableInfo("user", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(_db, "user");
        if (! _infoUser.equals(_existingUser)) {
          return new RoomOpenHelper.ValidationResult(false, "user(com.lilaclab.dadimadu.database.entity.User).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        final HashMap<String, TableInfo.Column> _columnsProduk = new HashMap<String, TableInfo.Column>(8);
        _columnsProduk.put("id_produk", new TableInfo.Column("id_produk", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("nama_produk", new TableInfo.Column("nama_produk", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("jenis_asal", new TableInfo.Column("jenis_asal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("ukuran_kemasan", new TableInfo.Column("ukuran_kemasan", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("harga_jual", new TableInfo.Column("harga_jual", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("harga_modal", new TableInfo.Column("harga_modal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("stok", new TableInfo.Column("stok", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduk.put("minimum_stok", new TableInfo.Column("minimum_stok", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProduk = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProduk = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProduk = new TableInfo("produk", _columnsProduk, _foreignKeysProduk, _indicesProduk);
        final TableInfo _existingProduk = TableInfo.read(_db, "produk");
        if (! _infoProduk.equals(_existingProduk)) {
          return new RoomOpenHelper.ValidationResult(false, "produk(com.lilaclab.dadimadu.database.entity.Produk).\n"
                  + " Expected:\n" + _infoProduk + "\n"
                  + " Found:\n" + _existingProduk);
        }
        final HashMap<String, TableInfo.Column> _columnsPelanggan = new HashMap<String, TableInfo.Column>(4);
        _columnsPelanggan.put("id_pelanggan", new TableInfo.Column("id_pelanggan", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPelanggan.put("nama_pelanggan", new TableInfo.Column("nama_pelanggan", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPelanggan.put("no_hp", new TableInfo.Column("no_hp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPelanggan.put("alamat", new TableInfo.Column("alamat", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPelanggan = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPelanggan = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPelanggan = new TableInfo("pelanggan", _columnsPelanggan, _foreignKeysPelanggan, _indicesPelanggan);
        final TableInfo _existingPelanggan = TableInfo.read(_db, "pelanggan");
        if (! _infoPelanggan.equals(_existingPelanggan)) {
          return new RoomOpenHelper.ValidationResult(false, "pelanggan(com.lilaclab.dadimadu.database.entity.Pelanggan).\n"
                  + " Expected:\n" + _infoPelanggan + "\n"
                  + " Found:\n" + _existingPelanggan);
        }
        final HashMap<String, TableInfo.Column> _columnsTransaksi = new HashMap<String, TableInfo.Column>(10);
        _columnsTransaksi.put("id_transaksi", new TableInfo.Column("id_transaksi", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("id_pelanggan", new TableInfo.Column("id_pelanggan", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("id_user", new TableInfo.Column("id_user", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("tanggal", new TableInfo.Column("tanggal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("ongkir", new TableInfo.Column("ongkir", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("metode_bayar", new TableInfo.Column("metode_bayar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("status_bayar", new TableInfo.Column("status_bayar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("subtotal", new TableInfo.Column("subtotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("grandtotal", new TableInfo.Column("grandtotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransaksi.put("create_at", new TableInfo.Column("create_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransaksi = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTransaksi.add(new TableInfo.ForeignKey("pelanggan", "SET NULL", "NO ACTION",Arrays.asList("id_pelanggan"), Arrays.asList("id_pelanggan")));
        _foreignKeysTransaksi.add(new TableInfo.ForeignKey("user", "NO ACTION", "NO ACTION",Arrays.asList("id_user"), Arrays.asList("id_user")));
        final HashSet<TableInfo.Index> _indicesTransaksi = new HashSet<TableInfo.Index>(2);
        _indicesTransaksi.add(new TableInfo.Index("index_transaksi_id_pelanggan", false, Arrays.asList("id_pelanggan"), Arrays.asList("ASC")));
        _indicesTransaksi.add(new TableInfo.Index("index_transaksi_id_user", false, Arrays.asList("id_user"), Arrays.asList("ASC")));
        final TableInfo _infoTransaksi = new TableInfo("transaksi", _columnsTransaksi, _foreignKeysTransaksi, _indicesTransaksi);
        final TableInfo _existingTransaksi = TableInfo.read(_db, "transaksi");
        if (! _infoTransaksi.equals(_existingTransaksi)) {
          return new RoomOpenHelper.ValidationResult(false, "transaksi(com.lilaclab.dadimadu.database.entity.Transaksi).\n"
                  + " Expected:\n" + _infoTransaksi + "\n"
                  + " Found:\n" + _existingTransaksi);
        }
        final HashMap<String, TableInfo.Column> _columnsDetailTransaksi = new HashMap<String, TableInfo.Column>(5);
        _columnsDetailTransaksi.put("id_transaksi", new TableInfo.Column("id_transaksi", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailTransaksi.put("id_produk", new TableInfo.Column("id_produk", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailTransaksi.put("qty", new TableInfo.Column("qty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailTransaksi.put("harga_saat_transaksi", new TableInfo.Column("harga_saat_transaksi", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetailTransaksi.put("subtotal", new TableInfo.Column("subtotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDetailTransaksi = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysDetailTransaksi.add(new TableInfo.ForeignKey("transaksi", "CASCADE", "NO ACTION",Arrays.asList("id_transaksi"), Arrays.asList("id_transaksi")));
        _foreignKeysDetailTransaksi.add(new TableInfo.ForeignKey("produk", "NO ACTION", "NO ACTION",Arrays.asList("id_produk"), Arrays.asList("id_produk")));
        final HashSet<TableInfo.Index> _indicesDetailTransaksi = new HashSet<TableInfo.Index>(2);
        _indicesDetailTransaksi.add(new TableInfo.Index("index_detail_transaksi_id_transaksi", false, Arrays.asList("id_transaksi"), Arrays.asList("ASC")));
        _indicesDetailTransaksi.add(new TableInfo.Index("index_detail_transaksi_id_produk", false, Arrays.asList("id_produk"), Arrays.asList("ASC")));
        final TableInfo _infoDetailTransaksi = new TableInfo("detail_transaksi", _columnsDetailTransaksi, _foreignKeysDetailTransaksi, _indicesDetailTransaksi);
        final TableInfo _existingDetailTransaksi = TableInfo.read(_db, "detail_transaksi");
        if (! _infoDetailTransaksi.equals(_existingDetailTransaksi)) {
          return new RoomOpenHelper.ValidationResult(false, "detail_transaksi(com.lilaclab.dadimadu.database.entity.DetailTransaksi).\n"
                  + " Expected:\n" + _infoDetailTransaksi + "\n"
                  + " Found:\n" + _existingDetailTransaksi);
        }
        final HashMap<String, TableInfo.Column> _columnsPembelianStok = new HashMap<String, TableInfo.Column>(9);
        _columnsPembelianStok.put("id_pembelian", new TableInfo.Column("id_pembelian", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("id_produk", new TableInfo.Column("id_produk", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("id_user", new TableInfo.Column("id_user", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("tanggal", new TableInfo.Column("tanggal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("supplier", new TableInfo.Column("supplier", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("harga_beli", new TableInfo.Column("harga_beli", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPembelianStok.put("keterangan", new TableInfo.Column("keterangan", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPembelianStok = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysPembelianStok.add(new TableInfo.ForeignKey("produk", "NO ACTION", "NO ACTION",Arrays.asList("id_produk"), Arrays.asList("id_produk")));
        _foreignKeysPembelianStok.add(new TableInfo.ForeignKey("user", "NO ACTION", "NO ACTION",Arrays.asList("id_user"), Arrays.asList("id_user")));
        final HashSet<TableInfo.Index> _indicesPembelianStok = new HashSet<TableInfo.Index>(2);
        _indicesPembelianStok.add(new TableInfo.Index("index_pembelian_stok_id_produk", false, Arrays.asList("id_produk"), Arrays.asList("ASC")));
        _indicesPembelianStok.add(new TableInfo.Index("index_pembelian_stok_id_user", false, Arrays.asList("id_user"), Arrays.asList("ASC")));
        final TableInfo _infoPembelianStok = new TableInfo("pembelian_stok", _columnsPembelianStok, _foreignKeysPembelianStok, _indicesPembelianStok);
        final TableInfo _existingPembelianStok = TableInfo.read(_db, "pembelian_stok");
        if (! _infoPembelianStok.equals(_existingPembelianStok)) {
          return new RoomOpenHelper.ValidationResult(false, "pembelian_stok(com.lilaclab.dadimadu.database.entity.PembelianStok).\n"
                  + " Expected:\n" + _infoPembelianStok + "\n"
                  + " Found:\n" + _existingPembelianStok);
        }
        final HashMap<String, TableInfo.Column> _columnsPengeluaran = new HashMap<String, TableInfo.Column>(6);
        _columnsPengeluaran.put("id_pengeluaran", new TableInfo.Column("id_pengeluaran", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengeluaran.put("id_user", new TableInfo.Column("id_user", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengeluaran.put("tanggal", new TableInfo.Column("tanggal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengeluaran.put("kategori", new TableInfo.Column("kategori", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengeluaran.put("keterangan", new TableInfo.Column("keterangan", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengeluaran.put("jumlah_pengeluaran", new TableInfo.Column("jumlah_pengeluaran", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPengeluaran = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPengeluaran.add(new TableInfo.ForeignKey("user", "NO ACTION", "NO ACTION",Arrays.asList("id_user"), Arrays.asList("id_user")));
        final HashSet<TableInfo.Index> _indicesPengeluaran = new HashSet<TableInfo.Index>(1);
        _indicesPengeluaran.add(new TableInfo.Index("index_pengeluaran_id_user", false, Arrays.asList("id_user"), Arrays.asList("ASC")));
        final TableInfo _infoPengeluaran = new TableInfo("pengeluaran", _columnsPengeluaran, _foreignKeysPengeluaran, _indicesPengeluaran);
        final TableInfo _existingPengeluaran = TableInfo.read(_db, "pengeluaran");
        if (! _infoPengeluaran.equals(_existingPengeluaran)) {
          return new RoomOpenHelper.ValidationResult(false, "pengeluaran(com.lilaclab.dadimadu.database.entity.Pengeluaran).\n"
                  + " Expected:\n" + _infoPengeluaran + "\n"
                  + " Found:\n" + _existingPengeluaran);
        }
        final HashMap<String, TableInfo.Column> _columnsPengaturan = new HashMap<String, TableInfo.Column>(5);
        _columnsPengaturan.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengaturan.put("nama_bisnis", new TableInfo.Column("nama_bisnis", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengaturan.put("nama_pemilik", new TableInfo.Column("nama_pemilik", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengaturan.put("no_telepon", new TableInfo.Column("no_telepon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengaturan.put("alamat", new TableInfo.Column("alamat", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPengaturan = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPengaturan = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPengaturan = new TableInfo("pengaturan", _columnsPengaturan, _foreignKeysPengaturan, _indicesPengaturan);
        final TableInfo _existingPengaturan = TableInfo.read(_db, "pengaturan");
        if (! _infoPengaturan.equals(_existingPengaturan)) {
          return new RoomOpenHelper.ValidationResult(false, "pengaturan(com.lilaclab.dadimadu.database.entity.Pengaturan).\n"
                  + " Expected:\n" + _infoPengaturan + "\n"
                  + " Found:\n" + _existingPengaturan);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1a4e574047e92f13e381cd3e5ad9c176", "115a41b6325164e2219732226bb4b52d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "user","produk","pelanggan","transaksi","detail_transaksi","pembelian_stok","pengeluaran","pengaturan");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `user`");
      _db.execSQL("DELETE FROM `produk`");
      _db.execSQL("DELETE FROM `pelanggan`");
      _db.execSQL("DELETE FROM `transaksi`");
      _db.execSQL("DELETE FROM `detail_transaksi`");
      _db.execSQL("DELETE FROM `pembelian_stok`");
      _db.execSQL("DELETE FROM `pengeluaran`");
      _db.execSQL("DELETE FROM `pengaturan`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProdukDao.class, ProdukDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PelangganDao.class, PelangganDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TransaksiDao.class, TransaksiDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PembelianStokDao.class, PembelianStokDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PengeluaranDao.class, PengeluaranDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PengaturanDao.class, PengaturanDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public ProdukDao produkDao() {
    if (_produkDao != null) {
      return _produkDao;
    } else {
      synchronized(this) {
        if(_produkDao == null) {
          _produkDao = new ProdukDao_Impl(this);
        }
        return _produkDao;
      }
    }
  }

  @Override
  public PelangganDao pelangganDao() {
    if (_pelangganDao != null) {
      return _pelangganDao;
    } else {
      synchronized(this) {
        if(_pelangganDao == null) {
          _pelangganDao = new PelangganDao_Impl(this);
        }
        return _pelangganDao;
      }
    }
  }

  @Override
  public TransaksiDao transaksiDao() {
    if (_transaksiDao != null) {
      return _transaksiDao;
    } else {
      synchronized(this) {
        if(_transaksiDao == null) {
          _transaksiDao = new TransaksiDao_Impl(this);
        }
        return _transaksiDao;
      }
    }
  }

  @Override
  public PembelianStokDao pembelianStokDao() {
    if (_pembelianStokDao != null) {
      return _pembelianStokDao;
    } else {
      synchronized(this) {
        if(_pembelianStokDao == null) {
          _pembelianStokDao = new PembelianStokDao_Impl(this);
        }
        return _pembelianStokDao;
      }
    }
  }

  @Override
  public PengeluaranDao pengeluaranDao() {
    if (_pengeluaranDao != null) {
      return _pengeluaranDao;
    } else {
      synchronized(this) {
        if(_pengeluaranDao == null) {
          _pengeluaranDao = new PengeluaranDao_Impl(this);
        }
        return _pengeluaranDao;
      }
    }
  }

  @Override
  public PengaturanDao pengaturanDao() {
    if (_pengaturanDao != null) {
      return _pengaturanDao;
    } else {
      synchronized(this) {
        if(_pengaturanDao == null) {
          _pengaturanDao = new PengaturanDao_Impl(this);
        }
        return _pengaturanDao;
      }
    }
  }
}

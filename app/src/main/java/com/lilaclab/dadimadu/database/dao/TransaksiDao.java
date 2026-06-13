package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.DetailTransaksi;
import com.lilaclab.dadimadu.database.entity.Transaksi;
import com.lilaclab.dadimadu.model.DetailTransaksiItem;
import com.lilaclab.dadimadu.model.TransaksiListItem;
import java.util.List;

@Dao
public interface TransaksiDao {
    @Insert(onConflict=3)
    public void insert(Transaksi transaksi);

    @Insert(onConflict=3)
    public void insertDetails(List<DetailTransaksi> details);

    @Query(value="SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan ORDER BY t.create_at DESC")
    public List<TransaksiListItem> getAllList();

    @Query(value="SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi LIKE '%' || :keyword || '%' OR p.nama_pelanggan LIKE '%' || :keyword || '%' ORDER BY t.create_at DESC")
    public List<TransaksiListItem> search(String keyword);

    @Query(value="SELECT t.id_transaksi AS idTransaksi, IFNULL(p.nama_pelanggan, 'Umum') AS namaPelanggan, t.tanggal, t.metode_bayar AS metodeBayar, t.status_bayar AS statusBayar, t.grandtotal FROM transaksi t LEFT JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi = :idTransaksi LIMIT 1")
    public TransaksiListItem getListItemById(String idTransaksi);

    @Query(value="SELECT * FROM transaksi WHERE id_transaksi = :idTransaksi LIMIT 1")
    public Transaksi getById(String idTransaksi);

    @Query(value="SELECT p.nama_produk AS namaProduk, p.ukuran_kemasan AS ukuranKemasan, d.qty, d.harga_saat_transaksi AS hargaSaatTransaksi, d.subtotal FROM detail_transaksi d INNER JOIN produk p ON d.id_produk = p.id_produk WHERE d.id_transaksi = :idTransaksi")
    public List<DetailTransaksiItem> getDetailItems(String idTransaksi);

    @Query(value="SELECT COUNT(*) FROM transaksi WHERE id_transaksi LIKE :prefix || '%'")
    public int countByPrefix(String prefix);

    @Query(value="SELECT COUNT(*) FROM transaksi")
    public int countAll();

    @Query(value="SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas' AND substr(tanggal, 1, 7) = :bulan")
    public double sumPemasukanBulan(String bulan);

    @Query(value="SELECT IFNULL(SUM(grandtotal),0) FROM transaksi WHERE status_bayar = 'Lunas'")
    public double sumPemasukanAll();

    @Query(value="SELECT COUNT(*) FROM transaksi WHERE substr(tanggal, 1, 7) = :bulan")
    public int countByMonth(String bulan);

    @Query(value="SELECT COUNT(*) FROM detail_transaksi WHERE id_produk = :idProduk")
    public int countDetailsByProduk(int idProduk);

    @Query(value="DELETE FROM detail_transaksi WHERE id_produk = :idProduk")
    public void deleteDetailsByProduk(int idProduk);
}
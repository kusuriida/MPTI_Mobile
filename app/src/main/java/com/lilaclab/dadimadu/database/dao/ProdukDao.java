package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.model.PenjualanProdukItem;
import com.lilaclab.dadimadu.model.StockReportItem;
import java.util.List;

@Dao
public interface ProdukDao {
    @Insert(onConflict=1)
    public long insert(Produk produk);

    @Update
    public void update(Produk produk);

    @Delete
    public void delete(Produk produk);

    @Query(value="DELETE FROM produk WHERE id_produk = :idProduk")
    public void deleteById(int idProduk);

    @Query(value="SELECT * FROM produk ORDER BY nama_produk ASC")
    public List<Produk> getAll();

    @Query(value="SELECT * FROM produk WHERE nama_produk LIKE '%' || :keyword || '%' OR ukuran_kemasan LIKE '%' || :keyword || '%' ORDER BY nama_produk ASC")
    public List<Produk> search(String keyword);

    @Query(value="SELECT * FROM produk WHERE id_produk = :idProduk LIMIT 1")
    public Produk getById(int idProduk);

    @Query(value="SELECT * FROM produk WHERE stok <= minimum_stok ORDER BY stok ASC, nama_produk ASC")
    public List<Produk> getLowStock();

    @Query(value="UPDATE produk SET stok = MAX(0, stok + :delta) WHERE id_produk = :idProduk")
    public void changeStock(int delta, int idProduk);

    @Query(value="UPDATE produk SET stok = stok - :qty WHERE id_produk = :idProduk AND stok >= :qty")
    public int reduceStock(int qty, int idProduk);

    @Query(value="UPDATE produk SET stok = stok + :qty WHERE id_produk = :idProduk")
    public void addStock(int qty, int idProduk);

    @Query(value="SELECT COUNT(*) FROM produk")
    public int countProduk();

    @Query(value="SELECT nama_produk AS namaProduk, ukuran_kemasan AS ukuranKemasan, stok, minimum_stok AS minimumStok, (stok * harga_modal) AS nilaiStok FROM produk ORDER BY stok ASC")
    public List<StockReportItem> getStockReport();

    @Query(value="SELECT p.nama_produk AS namaProduk, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = :bulan THEN d.qty ELSE 0 END),0) AS totalQty, IFNULL(SUM(CASE WHEN substr(t.tanggal, 1, 7) = :bulan THEN d.subtotal ELSE 0 END),0) AS totalPenjualan FROM produk p LEFT JOIN detail_transaksi d ON p.id_produk = d.id_produk LEFT JOIN transaksi t ON d.id_transaksi = t.id_transaksi GROUP BY p.id_produk ORDER BY totalPenjualan DESC")
    public List<PenjualanProdukItem> getPenjualanProduk(String bulan);
}
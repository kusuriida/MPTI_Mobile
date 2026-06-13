/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.Dao
 *  androidx.room.Insert
 *  androidx.room.Query
 *  com.lilaclab.dadimadu.database.dao.PembelianStokDao
 *  com.lilaclab.dadimadu.database.entity.PembelianStok
 *  com.lilaclab.dadimadu.model.PembelianListItem
 */
package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.PembelianStok;
import com.lilaclab.dadimadu.model.PembelianListItem;
import java.util.List;

@Dao
public interface PembelianStokDao {
    @Insert(onConflict=1)
    public long insert(PembelianStok var1);

    @Query(value="SELECT b.id_pembelian AS idPembelian, p.nama_produk AS namaProduk, b.tanggal, b.supplier, b.jumlah, b.harga_beli AS hargaBeli, b.total, b.keterangan FROM pembelian_stok b INNER JOIN produk p ON b.id_produk = p.id_produk ORDER BY b.tanggal DESC, b.id_pembelian DESC")
    public List<PembelianListItem> getAllList();

    @Query(value="SELECT IFNULL(SUM(total),0) FROM pembelian_stok WHERE substr(tanggal, 1, 7) = :bulan")
    public double sumPembelianBulan(String var1);

    @Query(value="SELECT COUNT(*) FROM pembelian_stok WHERE id_produk = :idProduk")
    public int countByProduk(int var1);

    @Query(value="DELETE FROM pembelian_stok WHERE id_produk = :idProduk")
    public void deleteByProduk(int var1);
}


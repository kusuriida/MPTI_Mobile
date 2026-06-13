/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.Dao
 *  androidx.room.Insert
 *  androidx.room.Query
 *  com.lilaclab.dadimadu.database.dao.PengeluaranDao
 *  com.lilaclab.dadimadu.database.entity.Pengeluaran
 */
package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import java.util.List;

@Dao
public interface PengeluaranDao {
    @Insert(onConflict=1)
    public long insert(Pengeluaran var1);

    @Query(value="DELETE FROM pengeluaran WHERE id_pengeluaran = :idPengeluaran")
    public void deleteById(int var1);

    @Query(value="SELECT * FROM pengeluaran ORDER BY tanggal DESC, id_pengeluaran DESC")
    public List<Pengeluaran> getAll();

    @Query(value="SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran WHERE substr(tanggal, 1, 7) = :bulan")
    public double sumPengeluaranBulan(String var1);

    @Query(value="SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran")
    public double sumPengeluaranAll();
}


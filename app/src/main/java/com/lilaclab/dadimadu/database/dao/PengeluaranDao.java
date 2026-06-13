package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.Pengeluaran;
import java.util.List;

@Dao
public interface PengeluaranDao {
    @Insert(onConflict=1)
    public long insert(Pengeluaran pengeluaran);

    @Query(value="DELETE FROM pengeluaran WHERE id_pengeluaran = :idPengeluaran")
    public void deleteById(int idPengeluaran);

    @Query(value="SELECT * FROM pengeluaran ORDER BY tanggal DESC, id_pengeluaran DESC")
    public List<Pengeluaran> getAll();

    @Query(value="SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran WHERE substr(tanggal, 1, 7) = :bulan")
    public double sumPengeluaranBulan(String bulan);

    @Query(value="SELECT IFNULL(SUM(jumlah_pengeluaran),0) FROM pengeluaran")
    public double sumPengeluaranAll();
}
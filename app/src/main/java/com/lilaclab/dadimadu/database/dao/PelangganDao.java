package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import java.util.List;

@Dao
public interface PelangganDao {
    @Insert(onConflict=1)
    public long insert(Pelanggan pelanggan);

    @Update
    public void update(Pelanggan pelanggan);

    @Query(value="DELETE FROM pelanggan WHERE id_pelanggan = :idPelanggan")
    public void deleteById(int idPelanggan);

    @Query(value="SELECT * FROM pelanggan ORDER BY nama_pelanggan ASC")
    public List<Pelanggan> getAll();

    @Query(value="SELECT * FROM pelanggan WHERE nama_pelanggan LIKE '%' || :keyword || '%' OR no_hp LIKE '%' || :keyword || '%' ORDER BY nama_pelanggan ASC")
    public List<Pelanggan> search(String keyword);

    @Query(value="SELECT * FROM pelanggan WHERE id_pelanggan = :idPelanggan LIMIT 1")
    public Pelanggan getById(int idPelanggan);

    @Query(value="SELECT * FROM pelanggan WHERE nama_pelanggan = :nama LIMIT 1")
    public Pelanggan findByName(String nama);
}
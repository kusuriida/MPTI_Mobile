/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.Dao
 *  androidx.room.Insert
 *  androidx.room.Query
 *  androidx.room.Update
 *  com.lilaclab.dadimadu.database.dao.PelangganDao
 *  com.lilaclab.dadimadu.database.entity.Pelanggan
 */
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
    public long insert(Pelanggan var1);

    @Update
    public void update(Pelanggan var1);

    @Query(value="DELETE FROM pelanggan WHERE id_pelanggan = :idPelanggan")
    public void deleteById(int var1);

    @Query(value="SELECT * FROM pelanggan ORDER BY nama_pelanggan ASC")
    public List<Pelanggan> getAll();

    @Query(value="SELECT * FROM pelanggan WHERE nama_pelanggan LIKE '%' || :keyword || '%' OR no_hp LIKE '%' || :keyword || '%' ORDER BY nama_pelanggan ASC")
    public List<Pelanggan> search(String var1);

    @Query(value="SELECT * FROM pelanggan WHERE id_pelanggan = :idPelanggan LIMIT 1")
    public Pelanggan getById(int var1);

    @Query(value="SELECT * FROM pelanggan WHERE nama_pelanggan = :nama LIMIT 1")
    public Pelanggan findByName(String var1);
}


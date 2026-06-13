package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.Pengaturan;

@Dao
public interface PengaturanDao {
    @Insert(onConflict=1)
    public void upsert(Pengaturan pengaturan);

    @Query(value="SELECT * FROM pengaturan WHERE id = 1 LIMIT 1")
    public Pengaturan get();
}
/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.Dao
 *  androidx.room.Insert
 *  androidx.room.Query
 *  com.lilaclab.dadimadu.database.dao.PengaturanDao
 *  com.lilaclab.dadimadu.database.entity.Pengaturan
 */
package com.lilaclab.dadimadu.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.lilaclab.dadimadu.database.entity.Pengaturan;

@Dao
public interface PengaturanDao {
    @Insert(onConflict=1)
    public void upsert(Pengaturan var1);

    @Query(value="SELECT * FROM pengaturan WHERE id = 1 LIMIT 1")
    public Pengaturan get();
}


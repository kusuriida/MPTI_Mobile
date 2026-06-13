/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.ColumnInfo
 *  androidx.room.Entity
 *  androidx.room.Ignore
 *  androidx.room.PrimaryKey
 *  com.lilaclab.dadimadu.database.entity.Pengaturan
 */
package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="pengaturan")
public class Pengaturan {
    @PrimaryKey
    public int id = 1;
    @ColumnInfo(name="nama_bisnis")
    public String namaBisnis;
    @ColumnInfo(name="nama_pemilik")
    public String namaPemilik;
    @ColumnInfo(name="no_telepon")
    public String noTelepon;
    public String alamat;

    public Pengaturan() {
    }

    @Ignore
    public Pengaturan(String namaBisnis, String namaPemilik, String noTelepon, String alamat) {
        this.id = 1;
        this.namaBisnis = namaBisnis;
        this.namaPemilik = namaPemilik;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }
}


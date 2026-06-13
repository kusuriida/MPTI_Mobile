package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="pelanggan")
public class Pelanggan {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id_pelanggan")
    public int idPelanggan;
    @ColumnInfo(name="nama_pelanggan")
    public String namaPelanggan;
    @ColumnInfo(name="no_hp")
    public String noHp;
    public String alamat;

    public Pelanggan() {
    }

    @Ignore
    public Pelanggan(String namaPelanggan, String noHp, String alamat) {
        this.namaPelanggan = namaPelanggan;
        this.noHp = noHp;
        this.alamat = alamat;
    }
}


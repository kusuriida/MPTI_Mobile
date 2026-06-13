/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.ColumnInfo
 *  androidx.room.Entity
 *  androidx.room.Ignore
 *  androidx.room.PrimaryKey
 *  com.lilaclab.dadimadu.database.entity.Produk
 */
package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="produk")
public class Produk {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id_produk")
    public int idProduk;
    @ColumnInfo(name="nama_produk")
    public String namaProduk;
    @ColumnInfo(name="jenis_asal")
    public String jenisAsal;
    @ColumnInfo(name="ukuran_kemasan")
    public String ukuranKemasan;
    @ColumnInfo(name="harga_jual")
    public double hargaJual;
    @ColumnInfo(name="harga_modal")
    public double hargaModal;
    public int stok;
    @ColumnInfo(name="minimum_stok")
    public int minimumStok;

    public Produk() {
    }

    @Ignore
    public Produk(String namaProduk, String jenisAsal, String ukuranKemasan, double hargaJual, double hargaModal, int stok, int minimumStok) {
        this.namaProduk = namaProduk;
        this.jenisAsal = jenisAsal;
        this.ukuranKemasan = ukuranKemasan;
        this.hargaJual = hargaJual;
        this.hargaModal = hargaModal;
        this.stok = stok;
        this.minimumStok = minimumStok;
    }

    public String statusStok() {
        if (this.stok <= 0) {
            return "habis";
        }
        if (this.stok <= this.minimumStok) {
            return "menipis";
        }
        return "aman";
    }
}


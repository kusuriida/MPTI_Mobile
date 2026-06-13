/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.annotation.NonNull
 *  androidx.room.ColumnInfo
 *  androidx.room.Entity
 *  androidx.room.ForeignKey
 *  androidx.room.Index
 *  androidx.room.PrimaryKey
 *  com.lilaclab.dadimadu.database.entity.Pelanggan
 *  com.lilaclab.dadimadu.database.entity.Transaksi
 *  com.lilaclab.dadimadu.database.entity.User
 */
package com.lilaclab.dadimadu.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.lilaclab.dadimadu.database.entity.Pelanggan;
import com.lilaclab.dadimadu.database.entity.User;

@Entity(tableName="transaksi", foreignKeys={@ForeignKey(entity=Pelanggan.class, parentColumns={"id_pelanggan"}, childColumns={"id_pelanggan"}, onDelete=3), @ForeignKey(entity=User.class, parentColumns={"id_user"}, childColumns={"id_user"}, onDelete=1)}, indices={@Index(value={"id_pelanggan"}), @Index(value={"id_user"})})
public class Transaksi {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_transaksi")
    public String idTransaksi = "";
    @ColumnInfo(name="id_pelanggan")
    public Integer idPelanggan;
    @ColumnInfo(name="id_user")
    public int idUser;
    public String tanggal;
    public double ongkir;
    @ColumnInfo(name="metode_bayar")
    public String metodeBayar;
    @ColumnInfo(name="status_bayar")
    public String statusBayar;
    public double subtotal;
    public double grandtotal;
    @ColumnInfo(name="create_at")
    public long createAt;
}


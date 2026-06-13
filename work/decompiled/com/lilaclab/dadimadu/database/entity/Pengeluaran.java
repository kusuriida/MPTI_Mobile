/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  androidx.room.ColumnInfo
 *  androidx.room.Entity
 *  androidx.room.ForeignKey
 *  androidx.room.Index
 *  androidx.room.PrimaryKey
 *  com.lilaclab.dadimadu.database.entity.Pengeluaran
 *  com.lilaclab.dadimadu.database.entity.User
 */
package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.lilaclab.dadimadu.database.entity.User;

@Entity(tableName="pengeluaran", foreignKeys={@ForeignKey(entity=User.class, parentColumns={"id_user"}, childColumns={"id_user"}, onDelete=1)}, indices={@Index(value={"id_user"})})
public class Pengeluaran {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id_pengeluaran")
    public int idPengeluaran;
    @ColumnInfo(name="id_user")
    public int idUser;
    public String tanggal;
    public String kategori;
    public String keterangan;
    @ColumnInfo(name="jumlah_pengeluaran")
    public double jumlahPengeluaran;
}


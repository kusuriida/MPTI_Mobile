package com.lilaclab.dadimadu.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.database.entity.User;

@Entity(tableName="pembelian_stok", foreignKeys={@ForeignKey(entity=Produk.class, parentColumns={"id_produk"}, childColumns={"id_produk"}, onDelete=1), @ForeignKey(entity=User.class, parentColumns={"id_user"}, childColumns={"id_user"}, onDelete=1)}, indices={@Index(value={"id_produk"}), @Index(value={"id_user"})})
public class PembelianStok {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id_pembelian")
    public int idPembelian;
    @ColumnInfo(name="id_produk")
    public int idProduk;
    @ColumnInfo(name="id_user")
    public int idUser;
    public String tanggal;
    public String supplier;
    public int jumlah;
    @ColumnInfo(name="harga_beli")
    public double hargaBeli;
    public double total;
    public String keterangan;
}


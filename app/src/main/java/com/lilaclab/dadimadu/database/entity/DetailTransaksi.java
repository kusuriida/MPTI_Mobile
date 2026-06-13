package com.lilaclab.dadimadu.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import com.lilaclab.dadimadu.database.entity.Produk;
import com.lilaclab.dadimadu.database.entity.Transaksi;

@Entity(tableName="detail_transaksi", primaryKeys={"id_transaksi", "id_produk"}, foreignKeys={@ForeignKey(entity=Transaksi.class, parentColumns={"id_transaksi"}, childColumns={"id_transaksi"}, onDelete=5), @ForeignKey(entity=Produk.class, parentColumns={"id_produk"}, childColumns={"id_produk"}, onDelete=1)}, indices={@Index(value={"id_transaksi"}), @Index(value={"id_produk"})})
public class DetailTransaksi {
    @NonNull
    @ColumnInfo(name="id_transaksi")
    public String idTransaksi = "";
    @ColumnInfo(name="id_produk")
    public int idProduk;
    public int qty;
    @ColumnInfo(name="harga_saat_transaksi")
    public double hargaSaatTransaksi;
    public double subtotal;
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lilaclab.dadimadu.model.DashboardSummary
 */
package com.lilaclab.dadimadu.model;

public class DashboardSummary {
    public final double pemasukan;
    public final double pengeluaran;
    public final double laba;
    public final int transaksi;
    public final int produk;

    public DashboardSummary(double pemasukan, double pengeluaran, int transaksi, int produk) {
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.laba = pemasukan - pengeluaran;
        this.transaksi = transaksi;
        this.produk = produk;
    }
}


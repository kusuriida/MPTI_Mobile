<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class DetailTransaksi extends Model
{
    protected $table      = 'detail_transaksi';
    public    $timestamps = false;
    public    $incrementing = false;

    protected $fillable = [
        'id_transaksi', 'id_produk', 'qty',
        'harga_saat_transaksi', 'subtotal',
    ];

    public function produk()
    {
        return $this->belongsTo(Produk::class, 'id_produk', 'id_produk');
    }

    public function transaksi()
    {
        return $this->belongsTo(Transaksi::class, 'id_transaksi', 'id_transaksi');
    }
}

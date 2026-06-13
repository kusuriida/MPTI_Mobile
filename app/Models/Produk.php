<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Produk extends Model
{
    protected $table      = 'produk';
    protected $primaryKey = 'id_produk';
    public    $timestamps = false;

    protected $fillable = [
        'nama_produk', 'jenis_asal', 'ukuran_kemasan',
        'harga_jual', 'harga_modal', 'stok', 'minimum_stok',
    ];

    public function pembelianStok()
    {
        return $this->hasMany(PembelianStok::class, 'id_produk', 'id_produk');
    }

    public function detailTransaksi()
    {
        return $this->hasMany(DetailTransaksi::class, 'id_produk', 'id_produk');
    }

    public function getStatusStokAttribute(): string
    {
        if ($this->stok == 0) return 'habis';
        if ($this->stok <= $this->minimum_stok) return 'menipis';
        return 'aman';
    }
}

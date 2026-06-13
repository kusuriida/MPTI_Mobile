<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class PembelianStok extends Model
{
    protected $table      = 'pembelian_stok';
    protected $primaryKey = 'id_pembelian';
    public    $timestamps = false;

    const CREATED_AT = 'create_at';
    const UPDATED_AT = null;

    protected $fillable = [
        'id_produk', 'id_user', 'tanggal', 'supplier',
        'jumlah', 'harga_beli', 'total', 'keterangan',
    ];

    protected $casts = [
        'tanggal' => 'date',
    ];

    public function produk()
    {
        return $this->belongsTo(Produk::class, 'id_produk', 'id_produk');
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'id_user', 'id_user');
    }
}

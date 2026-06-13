<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Transaksi extends Model
{
    protected $table      = 'transaksi';
    protected $primaryKey = 'id_transaksi';
    public    $incrementing = false;
    protected $keyType    = 'string';
    public    $timestamps = false;

    const CREATED_AT = 'create_at';
    const UPDATED_AT = null;

    protected $fillable = [
        'id_transaksi', 'id_pelanggan', 'id_user',
        'tanggal_transaksi', 'ongkir', 'metode_bayar',
        'status_bayar', 'subtotal', 'grandtotal',
    ];

    protected $casts = [
        'tanggal_transaksi' => 'date',
    ];

    public function pelanggan()
    {
        return $this->belongsTo(Pelanggan::class, 'id_pelanggan', 'id_pelanggan');
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'id_user', 'id_user');
    }

    public function detail()
    {
        return $this->hasMany(DetailTransaksi::class, 'id_transaksi', 'id_transaksi');
    }
}

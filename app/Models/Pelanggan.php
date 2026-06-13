<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Pelanggan extends Model
{
    protected $table      = 'pelanggan';
    protected $primaryKey = 'id_pelanggan';
    public    $timestamps = false;

    const CREATED_AT = 'create_at';
    const UPDATED_AT = null;

    protected $fillable = ['nama_pelanggan', 'no_hp', 'alamat'];

    public function transaksi()
    {
        return $this->hasMany(Transaksi::class, 'id_pelanggan', 'id_pelanggan');
    }
}

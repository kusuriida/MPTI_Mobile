<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Pengaturan extends Model
{
    protected $table    = 'pengaturan';
    protected $primaryKey = 'id';
    public    $timestamps = false;

    protected $fillable = [
        'nama_bisnis', 'nama_pemilik',
        'nomor_telepon', 'alamat',
    ];
}

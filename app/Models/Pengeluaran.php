<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Pengeluaran extends Model
{
    protected $table      = 'pengeluaran';
    protected $primaryKey = 'id_pengeluaran';
    public    $timestamps = false;

    const CREATED_AT = 'create_at';
    const UPDATED_AT = null;

    protected $fillable = [
        'id_user', 'tanggal', 'kategori',
        'keterangan', 'jumlah_pengeluaran',
    ];

    protected $casts = [
        'tanggal' => 'date',
    ];

    // Kategori enum yang tersedia
    public static array $kategoriList = ['Botol', 'Label', 'Segel', 'Madu'];

    public function user()
    {
        return $this->belongsTo(User::class, 'id_user', 'id_user');
    }
}

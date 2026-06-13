<?php

namespace App\Http\Controllers;

use App\Models\Produk;
use App\Models\Transaksi;
use App\Models\Pengeluaran;
use App\Models\PembelianStok;
use Illuminate\Support\Facades\DB;

class DashboardController extends Controller
{
    public function index()
    {
        $penjualanBulanIni = Transaksi::whereMonth('tanggal_transaksi', now()->month)
            ->whereYear('tanggal_transaksi', now()->year)
            ->where('status_bayar', 'Lunas')
            ->sum('grandtotal');

        $pengeluaranBulan = Pengeluaran::whereMonth('tanggal', now()->month)
            ->whereYear('tanggal', now()->year)
            ->sum('jumlah_pengeluaran');

        $modalBulan = PembelianStok::whereMonth('tanggal', now()->month)
            ->whereYear('tanggal', now()->year)
            ->sum('total');

        $labaBersihBulan = $penjualanBulanIni - $modalBulan - $pengeluaranBulan;

        $peringatanStok = Produk::whereColumn('stok', '<=', 'minimum_stok')
            ->orderBy('stok')->take(5)->get();

        return view('dashboard.index', compact(
            'penjualanBulanIni', 'pengeluaranBulan',
            'labaBersihBulan', 'peringatanStok'
        ));
    }
}

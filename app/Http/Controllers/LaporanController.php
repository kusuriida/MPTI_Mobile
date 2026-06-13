<?php

namespace App\Http\Controllers;

use App\Models\Transaksi;
use App\Models\Pengeluaran;
use App\Models\PembelianStok;
use App\Models\Produk;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class LaporanController extends Controller
{
    // Halaman utama laporan
    public function index(Request $request)
    {
        $bulan = $request->bulan ?? now()->format('Y-m');
        [$tahun, $bln] = explode('-', $bulan);

        $pendapatan = Transaksi::whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)
            ->where('status_bayar', 'Lunas')->sum('grandtotal');

        $modal = PembelianStok::whereMonth('tanggal', $bln)
            ->whereYear('tanggal', $tahun)->sum('total');

        $totalPengeluaran = Pengeluaran::whereMonth('tanggal', $bln)
            ->whereYear('tanggal', $tahun)->sum('jumlah_pengeluaran');

        $labaBersih = $pendapatan - $modal - $totalPengeluaran;

        return view('laporan.index', compact(
            'bulan', 'pendapatan', 'modal',
            'totalPengeluaran', 'labaBersih'
        ));
    }

    public function labaRugi(Request $request)
    {
        $bulan = $request->bulan ?? now()->format('Y-m');
        [$tahun, $bln] = explode('-', $bulan);

        $pendapatan = Transaksi::whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)
            ->where('status_bayar', 'Lunas')->sum('grandtotal');

        $modal = PembelianStok::whereMonth('tanggal', $bln)
            ->whereYear('tanggal', $tahun)->sum('total');

        $pengeluaran = Pengeluaran::whereMonth('tanggal', $bln)
            ->whereYear('tanggal', $tahun)->sum('jumlah_pengeluaran');

        $labaKotor  = $pendapatan - $modal;
        $labaBersih = $labaKotor - $pengeluaran;

        $rincianPengeluaran = Pengeluaran::whereMonth('tanggal', $bln)
            ->whereYear('tanggal', $tahun)
            ->selectRaw('kategori, SUM(jumlah_pengeluaran) as total')
            ->groupBy('kategori')->get();

        $transaksi = Transaksi::with('pelanggan')
            ->whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)
            ->orderBy('tanggal_transaksi')->get();

        return view('laporan.laba-rugi', compact(
            'bulan', 'pendapatan', 'modal', 'labaKotor',
            'pengeluaran', 'labaBersih', 'rincianPengeluaran', 'transaksi'
        ));
    }

    public function stok()
    {
        $produk = Produk::orderByRaw("
            CASE WHEN stok = 0 THEN 0
                 WHEN stok <= minimum_stok THEN 1
                 ELSE 2 END
        ")->orderBy('nama_produk')->get();

        $riwayatPembelian = PembelianStok::with(['produk', 'user'])
            ->orderBy('tanggal', 'desc')->take(20)->get();

        return view('laporan.stok', compact('produk', 'riwayatPembelian'));
    }

    public function penjualan(Request $request)
    {
        $bulan = $request->bulan ?? now()->format('Y-m');
        [$tahun, $bln] = explode('-', $bulan);

        $transaksi = Transaksi::with(['pelanggan', 'detail.produk'])
            ->whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)
            ->orderBy('tanggal_transaksi', 'desc')->get();

        $produkTerlaris = DB::table('detail_transaksi as dt')
            ->join('produk as p', 'p.id_produk', '=', 'dt.id_produk')
            ->join('transaksi as t', 't.id_transaksi', '=', 'dt.id_transaksi')
            ->whereMonth('t.tanggal_transaksi', $bln)
            ->whereYear('t.tanggal_transaksi', $tahun)
            ->selectRaw('p.nama_produk, p.ukuran_kemasan, SUM(dt.qty) as total_terjual, SUM(dt.subtotal) as total_pendapatan')
            ->groupBy('p.id_produk', 'p.nama_produk', 'p.ukuran_kemasan')
            ->orderBy('total_terjual', 'desc')->get();

        return view('laporan.penjualan', compact('bulan', 'transaksi', 'produkTerlaris'));
    }
}

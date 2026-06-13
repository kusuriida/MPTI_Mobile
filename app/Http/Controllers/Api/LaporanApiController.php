<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Transaksi;
use App\Models\Pengeluaran;
use App\Models\PembelianStok;
use App\Models\Produk;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class LaporanApiController extends Controller
{
    // GET /api/laporan/dashboard
    public function dashboard()
    {
        $penjualanHariIni = Transaksi::whereDate('tanggal_transaksi', today())
            ->where('status_bayar', 'Lunas')->sum('grandtotal');

        $totalTransaksi = Transaksi::whereDate('tanggal_transaksi', today())->count();

        $pengeluaranHariIni = Pengeluaran::whereDate('tanggal', today())
            ->sum('jumlah_pengeluaran');

        $penjualanBulanIni = Transaksi::whereMonth('tanggal_transaksi', now()->month)
            ->whereYear('tanggal_transaksi', now()->year)
            ->where('status_bayar', 'Lunas')->sum('grandtotal');

        $modalBulanIni = PembelianStok::whereMonth('tanggal', now()->month)
            ->whereYear('tanggal', now()->year)->sum('total');

        $pengeluaranBulan = Pengeluaran::whereMonth('tanggal', now()->month)
            ->whereYear('tanggal', now()->year)->sum('jumlah_pengeluaran');

        $stokMenipis = Produk::whereColumn('stok', '<=', 'minimum_stok')
            ->orderBy('stok')->get()->map(fn($p) => [
                'nama_produk'  => $p->nama_produk,
                'stok'         => $p->stok,
                'minimum_stok' => $p->minimum_stok,
                'status_stok'  => $p->status_stok,
            ]);

        return response()->json([
            'status' => true,
            'data'   => [
                'hari_ini' => [
                    'penjualan'   => (float) $penjualanHariIni,
                    'transaksi'   => (int) $totalTransaksi,
                    'pengeluaran' => (float) $pengeluaranHariIni,
                ],
                'bulan_ini' => [
                    'penjualan'    => (float) $penjualanBulanIni,
                    'modal'        => (float) $modalBulanIni,
                    'pengeluaran'  => (float) $pengeluaranBulan,
                    'laba_bersih'  => (float) ($penjualanBulanIni - $modalBulanIni - $pengeluaranBulan),
                ],
                'stok_perlu_restok' => $stokMenipis,
            ],
        ]);
    }

    // GET /api/laporan/laba-rugi?bulan=2026-05
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

        return response()->json([
            'status' => true,
            'data'   => [
                'bulan'      => $bulan,
                'pendapatan' => (float) $pendapatan,
                'modal'      => (float) $modal,
                'laba_kotor' => (float) $labaKotor,
                'pengeluaran'=> (float) $pengeluaran,
                'laba_bersih'=> (float) $labaBersih,
                'status'     => $labaBersih >= 0 ? 'untung' : 'rugi',
                'rincian_pengeluaran' => $rincianPengeluaran->map(fn($r) => [
                    'kategori' => $r->kategori,
                    'total'    => (float) $r->total,
                ]),
            ],
        ]);
    }

    // GET /api/laporan/stok
    public function stok()
    {
        $produk = Produk::orderByRaw("
            CASE WHEN stok = 0 THEN 0
                 WHEN stok <= minimum_stok THEN 1
                 ELSE 2 END
        ")->orderBy('nama_produk')->get();

        return response()->json([
            'status' => true,
            'data'   => $produk->map(fn($p) => [
                'id_produk'      => $p->id_produk,
                'nama_produk'    => $p->nama_produk,
                'ukuran_kemasan' => $p->ukuran_kemasan,
                'stok'           => (int) $p->stok,
                'minimum_stok'   => (int) $p->minimum_stok,
                'nilai_stok'     => (float) ($p->stok * $p->harga_modal),
                'status_stok'    => $p->status_stok,
            ]),
        ]);
    }

    // GET /api/laporan/penjualan?bulan=2026-05
    public function penjualan(Request $request)
    {
        $bulan = $request->bulan ?? now()->format('Y-m');
        [$tahun, $bln] = explode('-', $bulan);

        $totalPenjualan = Transaksi::whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)
            ->where('status_bayar', 'Lunas')->sum('grandtotal');

        $totalTransaksi = Transaksi::whereMonth('tanggal_transaksi', $bln)
            ->whereYear('tanggal_transaksi', $tahun)->count();

        $produkTerlaris = DB::table('detail_transaksi as dt')
            ->join('produk as p', 'p.id_produk', '=', 'dt.id_produk')
            ->join('transaksi as t', 't.id_transaksi', '=', 'dt.id_transaksi')
            ->whereMonth('t.tanggal_transaksi', $bln)
            ->whereYear('t.tanggal_transaksi', $tahun)
            ->selectRaw('p.nama_produk, p.ukuran_kemasan, SUM(dt.qty) as total_terjual, SUM(dt.subtotal) as total_pendapatan')
            ->groupBy('p.id_produk', 'p.nama_produk', 'p.ukuran_kemasan')
            ->orderBy('total_terjual', 'desc')
            ->get();

        return response()->json([
            'status' => true,
            'data'   => [
                'bulan'           => $bulan,
                'total_penjualan' => (float) $totalPenjualan,
                'total_transaksi' => (int) $totalTransaksi,
                'produk_terlaris' => $produkTerlaris,
            ],
        ]);
    }
}

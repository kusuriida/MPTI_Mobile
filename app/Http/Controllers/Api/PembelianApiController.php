<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\PembelianStok;
use App\Models\Produk;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PembelianApiController extends Controller
{
    // GET /api/pembelian
    public function index(Request $request)
    {
        $query = PembelianStok::with(['produk', 'user'])
            ->orderBy('tanggal', 'desc');

        if ($request->bulan) {
            [$tahun, $bln] = explode('-', $request->bulan);
            $query->whereMonth('tanggal', $bln)->whereYear('tanggal', $tahun);
        }

        $data = $query->paginate(15);

        return response()->json([
            'status' => true,
            'data'   => $data->map(fn($p) => [
                'id_pembelian' => $p->id_pembelian,
                'tanggal'      => $p->tanggal?->format('Y-m-d'),
                'produk'       => $p->produk->nama_produk,
                'supplier'     => $p->supplier,
                'jumlah'       => (int) $p->jumlah,
                'harga_beli'   => (float) $p->harga_beli,
                'total'        => (float) $p->total,
                'keterangan'   => $p->keterangan,
                'dicatat_oleh' => $p->user->nama,
            ]),
            'total'        => $data->total(),
            'current_page' => $data->currentPage(),
            'last_page'    => $data->lastPage(),
        ]);
    }

    // POST /api/pembelian
    public function store(Request $request)
    {
        $request->validate([
            'id_produk'  => 'required|exists:produk,id_produk',
            'tanggal'    => 'required|date',
            'jumlah'     => 'required|integer|min:1',
            'harga_beli' => 'required|numeric|min:0',
            'supplier'   => 'nullable|string|max:100',
            'keterangan' => 'nullable|string',
        ]);

        $total = $request->jumlah * $request->harga_beli;

        DB::transaction(function () use ($request, $total) {
            PembelianStok::create([
                'id_produk'  => $request->id_produk,
                'id_user'    => $request->user()->id_user,
                'tanggal'    => $request->tanggal,
                'supplier'   => $request->supplier,
                'jumlah'     => $request->jumlah,
                'harga_beli' => $request->harga_beli,
                'total'      => $total,
                'keterangan' => $request->keterangan,
            ]);
            Produk::where('id_produk', $request->id_produk)
                ->increment('stok', $request->jumlah);
        });

        $produk = Produk::find($request->id_produk);

        return response()->json([
            'status'    => true,
            'message'   => 'Pembelian stok berhasil dicatat.',
            'data'      => [
                'total'      => $total,
                'stok_baru'  => $produk->stok,
            ],
        ], 201);
    }
}

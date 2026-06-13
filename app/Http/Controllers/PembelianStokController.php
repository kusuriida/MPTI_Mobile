<?php

namespace App\Http\Controllers;

use App\Models\PembelianStok;
use App\Models\Produk;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PembelianStokController extends Controller
{
    public function index()
    {
        $pembelian = PembelianStok::with(['produk', 'user'])
            ->orderBy('tanggal', 'desc')
            ->paginate(15);
        return view('pembelian.index', compact('pembelian'));
    }

    public function create()
    {
        $produk = Produk::orderBy('nama_produk')->get();
        return view('pembelian.create', compact('produk'));
    }

    public function store(Request $request)
    {
        $request->validate([
            'id_produk'  => 'required|exists:produk,id_produk',
            'tanggal'    => 'required|date',
            'supplier'   => 'nullable|string|max:100',
            'jumlah'     => 'required|integer|min:1',
            'harga_beli' => 'required|numeric|min:0',
            'keterangan' => 'nullable|string',
        ]);

        $total = $request->jumlah * $request->harga_beli;

        DB::transaction(function () use ($request, $total) {
            PembelianStok::create([
                'id_produk'  => $request->id_produk,
                'id_user'    => auth()->user()->id_user,
                'tanggal'    => $request->tanggal,
                'supplier'   => $request->supplier,
                'jumlah'     => $request->jumlah,
                'harga_beli' => $request->harga_beli,
                'total'      => $total,
                'keterangan' => $request->keterangan,
            ]);

            // Tambah stok produk
            Produk::where('id_produk', $request->id_produk)
                ->increment('stok', $request->jumlah);
        });

        return redirect()->route('pembelian.index')
            ->with('success', 'Pembelian stok berhasil dicatat.');
    }
}

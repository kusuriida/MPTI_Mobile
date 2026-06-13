<?php

namespace App\Http\Controllers;

use App\Models\Produk;
use Illuminate\Http\Request;

class ProdukController extends Controller
{
    public function index()
    {
        $produk = Produk::orderBy('nama_produk')->get();
        return view('produk.index', compact('produk'));
    }

    public function create()
    {
        return view('produk.create');
    }

    public function store(Request $request)
    {
        $request->validate([
            'nama_produk'    => 'required|string|max:100',
            'jenis_asal'     => 'required|in:Supplier,Ternak',
            'ukuran_kemasan' => 'required|string|max:50',
            'harga_jual'     => 'required|numeric|min:0',
            'harga_modal'    => 'required|numeric|min:0',
            'minimum_stok'   => 'required|integer|min:0',
            'stok_awal'      => 'nullable|integer|min:0',
        ]);

        Produk::create([
            'nama_produk'    => $request->nama_produk,
            'jenis_asal'     => $request->jenis_asal,
            'ukuran_kemasan' => $request->ukuran_kemasan,
            'harga_jual'     => $request->harga_jual,
            'harga_modal'    => $request->harga_modal,
            'minimum_stok'   => $request->minimum_stok,
            'stok'           => $request->stok_awal ?? 0,
        ]);

        return redirect()->route('produk.index')
            ->with('success', 'Produk berhasil ditambahkan.');
    }

    public function edit(Produk $produk)
    {
        return view('produk.edit', compact('produk'));
    }

    public function update(Request $request, Produk $produk)
    {
        $request->validate([
            'nama_produk'    => 'required|string|max:100',
            'jenis_asal'     => 'required|in:Supplier,Ternak',
            'ukuran_kemasan' => 'required|string|max:50',
            'harga_jual'     => 'required|numeric|min:0',
            'harga_modal'    => 'required|numeric|min:0',
            'minimum_stok'   => 'required|integer|min:0',
        ]);

        $produk->update($request->only([
            'nama_produk', 'jenis_asal', 'ukuran_kemasan',
            'harga_jual', 'harga_modal', 'minimum_stok',
        ]));

        return redirect()->route('produk.index')
            ->with('success', 'Produk berhasil diperbarui.');
    }

    public function destroy(Produk $produk)
    {
        $produk->delete();
        return redirect()->route('produk.index')
            ->with('success', 'Produk berhasil dihapus.');
    }
}

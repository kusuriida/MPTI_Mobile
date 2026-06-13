<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Produk;
use Illuminate\Http\Request;

class ProdukApiController extends Controller
{
    // GET /api/produk
    public function index()
    {
        $produk = Produk::orderBy('nama_produk')->get()->map(function ($p) {
            return [
                'id_produk'      => $p->id_produk,
                'nama_produk'    => $p->nama_produk,
                'jenis_asal'     => $p->jenis_asal,
                'ukuran_kemasan' => $p->ukuran_kemasan,
                'harga_jual'     => (float) $p->harga_jual,
                'harga_modal'    => (float) $p->harga_modal,
                'margin'         => (float) ($p->harga_jual - $p->harga_modal),
                'stok'           => (int) $p->stok,
                'minimum_stok'   => (int) $p->minimum_stok,
                'status_stok'    => $p->status_stok,
            ];
        });

        return response()->json(['status' => true, 'data' => $produk]);
    }

    // GET /api/produk/{id}
    public function show($id)
    {
        $p = Produk::find($id);
        if (!$p) {
            return response()->json(['status' => false, 'message' => 'Produk tidak ditemukan.'], 404);
        }

        return response()->json([
            'status' => true,
            'data'   => [
                'id_produk'      => $p->id_produk,
                'nama_produk'    => $p->nama_produk,
                'jenis_asal'     => $p->jenis_asal,
                'ukuran_kemasan' => $p->ukuran_kemasan,
                'harga_jual'     => (float) $p->harga_jual,
                'harga_modal'    => (float) $p->harga_modal,
                'margin'         => (float) ($p->harga_jual - $p->harga_modal),
                'stok'           => (int) $p->stok,
                'minimum_stok'   => (int) $p->minimum_stok,
                'status_stok'    => $p->status_stok,
            ],
        ]);
    }

    // POST /api/produk
    public function store(Request $request)
    {
        $request->validate([
            'nama_produk'    => 'required|string|max:100',
            'jenis_asal'     => 'required|in:Supplier,Ternak',
            'ukuran_kemasan' => 'required|string|max:50',
            'harga_jual'     => 'required|numeric|min:0',
            'harga_modal'    => 'required|numeric|min:0',
            'minimum_stok'   => 'required|integer|min:0',
        ]);

        $produk = Produk::create(array_merge($request->all(), ['stok' => 0]));

        return response()->json([
            'status'  => true,
            'message' => 'Produk berhasil ditambahkan.',
            'data'    => $produk,
        ], 201);
    }

    // PUT /api/produk/{id}
    public function update(Request $request, $id)
    {
        $produk = Produk::find($id);
        if (!$produk) {
            return response()->json(['status' => false, 'message' => 'Produk tidak ditemukan.'], 404);
        }

        $request->validate([
            'nama_produk'    => 'sometimes|string|max:100',
            'jenis_asal'     => 'sometimes|in:Supplier,Ternak',
            'ukuran_kemasan' => 'sometimes|string|max:50',
            'harga_jual'     => 'sometimes|numeric|min:0',
            'harga_modal'    => 'sometimes|numeric|min:0',
            'minimum_stok'   => 'sometimes|integer|min:0',
        ]);

        $produk->update($request->all());

        return response()->json([
            'status'  => true,
            'message' => 'Produk berhasil diperbarui.',
            'data'    => $produk,
        ]);
    }

    // DELETE /api/produk/{id}
    public function destroy($id)
    {
        $produk = Produk::find($id);
        if (!$produk) {
            return response()->json(['status' => false, 'message' => 'Produk tidak ditemukan.'], 404);
        }

        $produk->delete();
        return response()->json(['status' => true, 'message' => 'Produk berhasil dihapus.']);
    }
}

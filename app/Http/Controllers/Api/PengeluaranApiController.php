<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Pengeluaran;
use Illuminate\Http\Request;

class PengeluaranApiController extends Controller
{
    // GET /api/pengeluaran
    public function index(Request $request)
    {
        $query = Pengeluaran::with('user')->orderBy('tanggal', 'desc');

        if ($request->bulan) {
            [$tahun, $bln] = explode('-', $request->bulan);
            $query->whereMonth('tanggal', $bln)->whereYear('tanggal', $tahun);
        }

        if ($request->kategori) {
            $query->where('kategori', $request->kategori);
        }

        $data = $query->paginate(15);

        return response()->json([
            'status' => true,
            'data'   => $data->map(fn($p) => [
                'id_pengeluaran'     => $p->id_pengeluaran,
                'tanggal'            => $p->tanggal?->format('Y-m-d'),
                'kategori'           => $p->kategori,
                'keterangan'         => $p->keterangan,
                'jumlah_pengeluaran' => (float) $p->jumlah_pengeluaran,
                'dicatat_oleh'       => $p->user->nama,
            ]),
            'total'        => $data->total(),
            'current_page' => $data->currentPage(),
            'last_page'    => $data->lastPage(),
        ]);
    }

    // POST /api/pengeluaran
    public function store(Request $request)
    {
        $request->validate([
            'tanggal'            => 'required|date',
            'kategori'           => 'required|in:Botol,Label,Segel,Madu',
            'jumlah_pengeluaran' => 'required|numeric|min:1',
            'keterangan'         => 'required|string',
        ]);

        $pengeluaran = Pengeluaran::create([
            'id_user'            => $request->user()->id_user,
            'tanggal'            => $request->tanggal,
            'kategori'           => $request->kategori,
            'jumlah_pengeluaran' => $request->jumlah_pengeluaran,
            'keterangan'         => $request->keterangan,
        ]);

        return response()->json([
            'status'  => true,
            'message' => 'Pengeluaran berhasil dicatat.',
            'data'    => $pengeluaran,
        ], 201);
    }

    // DELETE /api/pengeluaran/{id}
    public function destroy(Request $request, $id)
    {
        $pengeluaran = Pengeluaran::find($id);

        if (!$pengeluaran) {
            return response()->json(['status' => false, 'message' => 'Data tidak ditemukan.'], 404);
        }

        $pengeluaran->delete();
        return response()->json(['status' => true, 'message' => 'Pengeluaran berhasil dihapus.']);
    }
}

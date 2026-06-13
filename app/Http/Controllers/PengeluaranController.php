<?php

namespace App\Http\Controllers;

use App\Models\Pengeluaran;
use Illuminate\Http\Request;

class PengeluaranController extends Controller
{
    public function index()
    {
        $pengeluaran = Pengeluaran::with('user')
            ->orderBy('tanggal', 'desc')
            ->paginate(15);
        return view('pengeluaran.index', compact('pengeluaran'));
    }

    public function create()
    {
        $kategoriList = Pengeluaran::$kategoriList;
        return view('pengeluaran.create', compact('kategoriList'));
    }

    public function store(Request $request)
    {
        $request->validate([
            'tanggal'           => 'required|date',
            'kategori'          => 'required|in:Botol,Label,Segel,Madu',
            'jumlah_pengeluaran'=> 'required|numeric|min:1',
            'keterangan'        => 'required|string',
        ]);

        Pengeluaran::create([
            'id_user'           => auth()->user()->id_user,
            'tanggal'           => $request->tanggal,
            'kategori'          => $request->kategori,
            'jumlah_pengeluaran'=> $request->jumlah_pengeluaran,
            'keterangan'        => $request->keterangan,
        ]);

        return redirect()->route('pengeluaran.index')
            ->with('success', 'Pengeluaran berhasil dicatat.');
    }

    public function destroy(Pengeluaran $pengeluaran)
    {
        $pengeluaran->delete();
        return redirect()->route('pengeluaran.index')
            ->with('success', 'Data pengeluaran dihapus.');
    }
}

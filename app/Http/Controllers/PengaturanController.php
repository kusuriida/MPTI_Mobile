<?php

namespace App\Http\Controllers;

use App\Models\Pengaturan;
use App\Models\Produk;
use App\Models\Pelanggan;
use App\Models\Transaksi;
use App\Models\User;
use Illuminate\Http\Request;

class PengaturanController extends Controller
{
    public function index()
    {
        $pengaturan   = Pengaturan::first();
        $totalProduk  = Produk::count();
        $totalPelanggan = Pelanggan::count();
        $totalTransaksi = Transaksi::count();
        $users        = User::all();

        return view('pengaturan.index', compact(
            'pengaturan', 'totalProduk',
            'totalPelanggan', 'totalTransaksi', 'users'
        ));
    }

    public function edit()
    {
        $pengaturan = Pengaturan::first();
        return view('pengaturan.edit', compact('pengaturan'));
    }

    public function update(Request $request)
    {
        $request->validate([
            'nama_bisnis'    => 'required|string|max:100',
            'nama_pemilik'   => 'nullable|string|max:100',
            'nomor_telepon'  => 'nullable|string|max:20',
            'alamat'         => 'nullable|string',
        ]);

        $pengaturan = Pengaturan::first();

        if ($pengaturan) {
            $pengaturan->update($request->all());
        } else {
            Pengaturan::create($request->all());
        }

        return redirect()->route('pengaturan.index')
            ->with('success', 'Pengaturan berhasil disimpan.');
    }
}

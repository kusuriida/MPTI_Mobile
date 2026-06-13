<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Transaksi;
use App\Models\DetailTransaksi;
use App\Models\Pelanggan;
use App\Models\Produk;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class TransaksiApiController extends Controller
{
    // GET /api/transaksi
    public function index(Request $request)
    {
        $query = Transaksi::with('pelanggan')
            ->orderBy('create_at', 'desc');

        // Filter by bulan jika ada
        if ($request->bulan) {
            [$tahun, $bln] = explode('-', $request->bulan);
            $query->whereMonth('tanggal_transaksi', $bln)
                  ->whereYear('tanggal_transaksi', $tahun);
        }

        // Filter by status_bayar
        if ($request->status_bayar) {
            $query->where('status_bayar', $request->status_bayar);
        }

        $transaksi = $query->paginate(15);

        return response()->json([
            'status' => true,
            'data'   => $transaksi->map(function ($t) {
                return [
                    'id_transaksi'      => $t->id_transaksi,
                    'tanggal'           => $t->tanggal_transaksi?->format('Y-m-d'),
                    'pelanggan'         => $t->pelanggan?->nama_pelanggan ?? 'Umum',
                    'subtotal'          => (float) $t->subtotal,
                    'ongkir'            => (float) $t->ongkir,
                    'grandtotal'        => (float) $t->grandtotal,
                    'metode_bayar'      => $t->metode_bayar,
                    'status_bayar'      => $t->status_bayar,
                ];
            }),
            'total'        => $transaksi->total(),
            'current_page' => $transaksi->currentPage(),
            'last_page'    => $transaksi->lastPage(),
        ]);
    }

    // GET /api/transaksi/{id}
    public function show($id)
    {
        $t = Transaksi::with(['pelanggan', 'detail.produk'])->find($id);

        if (!$t) {
            return response()->json(['status' => false, 'message' => 'Transaksi tidak ditemukan.'], 404);
        }

        return response()->json([
            'status' => true,
            'data'   => [
                'id_transaksi' => $t->id_transaksi,
                'tanggal'      => $t->tanggal_transaksi?->format('Y-m-d'),
                'pelanggan'    => $t->pelanggan ? [
                    'id'   => $t->pelanggan->id_pelanggan,
                    'nama' => $t->pelanggan->nama_pelanggan,
                    'no_hp'=> $t->pelanggan->no_hp,
                ] : null,
                'detail' => $t->detail->map(fn($d) => [
                    'produk'              => $d->produk->nama_produk,
                    'ukuran'              => $d->produk->ukuran_kemasan,
                    'qty'                 => (int) $d->qty,
                    'harga_saat_transaksi'=> (float) $d->harga_saat_transaksi,
                    'subtotal'            => (float) $d->subtotal,
                ]),
                'subtotal'     => (float) $t->subtotal,
                'ongkir'       => (float) $t->ongkir,
                'grandtotal'   => (float) $t->grandtotal,
                'metode_bayar' => $t->metode_bayar,
                'status_bayar' => $t->status_bayar,
            ],
        ]);
    }

    // POST /api/transaksi
    public function store(Request $request)
    {
        $request->validate([
            'tanggal_transaksi' => 'required|date',
            'status_bayar'      => 'required|string',
            'metode_bayar'      => 'required|string',
            'items'             => 'required|array|min:1',
            'items.*.id_produk' => 'required|exists:produk,id_produk',
            'items.*.qty'       => 'required|integer|min:1',
        ]);

        try {
            DB::transaction(function () use ($request, &$idTransaksi) {
                // Buat atau ambil pelanggan
                $pelangganId = null;
                if ($request->nama_pelanggan) {
                    $pelanggan   = Pelanggan::firstOrCreate(
                        ['nama_pelanggan' => $request->nama_pelanggan],
                        ['no_hp'          => $request->no_hp ?? null]
                    );
                    $pelangganId = $pelanggan->id_pelanggan;
                }

                // Hitung total
                $subtotal = 0;
                $items    = [];

                foreach ($request->items as $item) {
                    $produk   = Produk::findOrFail($item['id_produk']);

                    if ($produk->stok < $item['qty']) {
                        throw new \Exception("Stok {$produk->nama_produk} tidak cukup. Stok tersedia: {$produk->stok}");
                    }

                    $sub      = $produk->harga_jual * $item['qty'];
                    $subtotal += $sub;
                    $items[]  = [
                        'id_produk'            => $item['id_produk'],
                        'qty'                  => $item['qty'],
                        'harga_saat_transaksi' => $produk->harga_jual,
                        'subtotal'             => $sub,
                    ];
                    $produk->decrement('stok', $item['qty']);
                }

                $ongkir     = $request->ongkir ?? 0;
                $grandtotal = $subtotal + $ongkir;

                Transaksi::create([
                    'id_transaksi'      => '',
                    'id_pelanggan'      => $pelangganId,
                    'id_user'           => $request->user()->id_user,
                    'tanggal_transaksi' => $request->tanggal_transaksi,
                    'ongkir'            => $ongkir,
                    'metode_bayar'      => $request->metode_bayar,
                    'status_bayar'      => $request->status_bayar,
                    'subtotal'          => $subtotal,
                    'grandtotal'        => $grandtotal,
                ]);

                $idTransaksi = DB::table('transaksi')
                    ->where('id_user', $request->user()->id_user)
                    ->orderBy('create_at', 'desc')
                    ->value('id_transaksi');

                foreach ($items as $item) {
                    DetailTransaksi::create(array_merge($item, ['id_transaksi' => $idTransaksi]));
                }
            });

            $transaksi = Transaksi::with('detail.produk')->find($idTransaksi);

            return response()->json([
                'status'  => true,
                'message' => 'Transaksi berhasil disimpan.',
                'data'    => ['id_transaksi' => $idTransaksi, 'grandtotal' => (float) $transaksi->grandtotal],
            ], 201);

        } catch (\Exception $e) {
            return response()->json(['status' => false, 'message' => $e->getMessage()], 422);
        }
    }
}

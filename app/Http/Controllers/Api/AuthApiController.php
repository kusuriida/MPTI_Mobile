<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AuthApiController extends Controller
{
    // POST /api/login
    public function login(Request $request)
    {
        $request->validate([
            'username' => 'required|string',
            'password' => 'required|string',
        ]);

        $user = User::where('username', $request->username)->first();

        if (!$user || !Hash::check($request->password, $user->password)) {
            return response()->json([
                'status'  => false,
                'message' => 'Username atau password salah.',
            ], 401);
        }

        $user->tokens()->delete();
        $token = $user->createToken('mobile-app')->plainTextToken;

        return response()->json([
            'status'  => true,
            'message' => 'Login berhasil.',
            'data'    => [
                'token' => $token,
                'user'  => [
                    'id_user'  => $user->id_user,
                    'nama'     => $user->nama,
                    'username' => $user->username,
                    'role'     => $user->role,
                ],
            ],
        ]);
    }

    // POST /api/logout
    public function logout(Request $request)
    {
        $request->user()->currentAccessToken()->delete();
        return response()->json(['status' => true, 'message' => 'Logout berhasil.']);
    }

    // GET /api/profile
    public function profile(Request $request)
    {
        $user = $request->user();
        return response()->json([
            'status' => true,
            'data'   => [
                'id_user'  => $user->id_user,
                'nama'     => $user->nama,
                'username' => $user->username,
                'role'     => $user->role,
            ],
        ]);
    }
}

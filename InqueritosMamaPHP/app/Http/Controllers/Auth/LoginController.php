<?php

namespace App\Http\Controllers\Auth;

use Auth;
use App\User;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class LoginController extends Controller
{
	public function __construct()
    {
        $this->middleware('guest', ['except' => 'logout']);
    }

    public function showLoginForm() {
    	return view('auth.login');
    }

	public function login(User $admin, Request $request) {
		$admin = $admin->verifyUser($request->input('id_admin'), $request->input('password'), true);
		if ($admin) {
			 if ($admin->isAdmin()) {
				Auth::loginUsingId($admin->id);
				return redirect('/');
			}
		}
		return redirect()->back()->withErrors(['msg' => 'O login não foi realizado com sucesso. Ou as suas credenciais estão incorretas ou você não é um administrador.']);
	}
}

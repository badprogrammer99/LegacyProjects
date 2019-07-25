<?php

namespace App\Http\Controllers\Auth;

use App\User; 
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class ApiLoginController extends Controller
{
    public function login(User $user, Request $request) {
    	if ($this->isValidRequest($request)) {
			$patientId = $request->input('patient_id');
    		$password = $request->input('password');
			
    		$patient = $user->verifyUser($patientId, $password, true);

    		if ($patient) {
                if ($patient->isAdmin()) {
					return $this->failedAuthentication();
                }
    			$token = $this->generateTokenForUser($patient, 'Personal Token Access');
    			if ($token) {
    				return response()->json(['token' => $token], 200);
    			}
    			return $this->unexpectedError();
    		}
    		return $this->failedAuthentication();
    	}
    	return $this->requestIsMalformed();
	}

	private function isValidRequest($request) {
		if ($request->filled('patient_id') && $request->filled('password')) {
			return true;
		}
		return false;
	}

	private function generateTokenForUser($user, $tokenName) {
		return $user->createToken($tokenName)->accessToken;
	}

    private function requestIsMalformed() {
        return $this->generateJsonErrorMessage('Malformed request.', 500);
    }

    private function failedAuthentication() {
        return $this->generateJsonErrorMessage('O login falhou. Verifique o utilizador e a palavra-passe introduzidos e tente outra vez.', 401);
    }

    private function unexpectedError() {
        return $this->generateJsonErrorMessage('An unexpected error has occurred.', 500);
    }

    private function generateJsonErrorMessage($message, $status) {
        return response()->json(['error' => $message], $status);
    }
}

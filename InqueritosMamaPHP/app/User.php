<?php

namespace App;

use Auth;
use App\Inquiry;
use Laravel\Passport\HasApiTokens;
use Illuminate\Notifications\Notifiable;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use HasApiTokens, Notifiable;
    
    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token', 'created_at', 'updated_at', 'is_admin'
    ];

    public function inquiry() {
        return $this->belongsTo('App\Inquiry');
    }
	
	public function createNewUser($inquiryId, $patientId, $patientName, $isAdmin) {
		if ($inquiryId != null) {
			if ($this->where(['id' => $inquiryId])->count() > 0) {
				$this->inquiry_id = $inquiryId;
			} else {
				return false;
			}
		} else {
			$this->inquiry_id = null;
		}
		
		$this->patient_id = $patientId;
		$this->patient_name = $patientName;
		$this->password = encrypt(substr(md5(rand()), 0, 5));
		$this->is_admin = $isAdmin;
		
		return $this->save();
	}
	
	public function checkForDuplicateUserIds($userId) {
		return $this->where(['patient_id' => $userId])->get()->count() > 0;
	}
	
    public function getUserPassword() {
        return decrypt($this->password);
    }
	
	public function setUserPassword($userId, $newPassword) {
		$user = $this->checkIfUserExistsAndFetch($userId);
		
		if ($user == null) {
			abort(404);
		}
		
		$user->password = encrypt($newPassword);
		
		return $user->save();
	}

    public function checkIfUserExistsAndFetch($userId) {
        $user = $this->where('patient_id', '=', $userId)->first();
		
        if ($user) {
            return $user;
        }
        return null;
    }

    public function verifyUser($userId, $password, $returnInstance = false) {
        $user = $this->checkIfUserExistsAndFetch($userId);
		
		if ($user != null && $password == $user->getUserPassword()) {
            if ($returnInstance) {
                return $user;
            }
            return true;
        }
        return false;
    }

    public function isAdmin() {
		return $this->is_admin == 1;
    }
}

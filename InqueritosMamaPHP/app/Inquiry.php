<?php

namespace App;

use Auth;
use Illuminate\Database\Eloquent\Model;

class Inquiry extends Model
{
    protected $table = 'inquiries';

    protected $hidden = ['created_at', 'updated_at'];

    public function questionnaries() {
    	return $this->hasMany('App\Questionnarie');
    }

    public function getQuestionnariesUserHasAnsweredOnInquiry() {
		$questionnariesUserHasAnswered = $this->with('questionnaries.questions.answers')->whereHas('questionnaries.questions.answers', function ($q) {
 			    $q->where('user_id', Auth::user()->id);
		})->where('id', '=', Auth::user()->inquiry_id)->get();
		return $questionnariesUserHasAnswered;
	}

    public function getUserInquiries() {
        return $this->with('questionnaries')->where(['id' => Auth::user()->inquiry_id])->get();
    }
}

<?php

namespace App;

use Auth;
use CoenJacobs\EloquentCompositePrimaryKeys\HasCompositePrimaryKey;
use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{
	use HasCompositePrimaryKey;

	protected $primaryKey = array('user_id', 'question_id');

    protected $hidden = ['user_id', 'created_at', 'updated_at'];

    public function user() {
    	return $this->belongsTo('App\User');
    }

    public function question() {
    	return $this->belongsTo('App\Question');
    }

    public function getQuestionAnswerForUserId($questionnarieId, $questionId) {
    	$userAnswer = $this->whereHas('question.questionnarie', function ($q) use ($questionnarieId) {
    		$q->where('id', '=', $questionnarieId)->whereHas('inquiry', function ($q) {
                $q->where('id', '=', Auth::user()->inquiry_id);
            });
    	})->where([
    		'user_id' => Auth::user()->id,
    		'question_id' => $questionId
    	])->firstOrFail();
    	return $userAnswer;
    }
}

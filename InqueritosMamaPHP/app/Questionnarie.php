<?php

namespace App;

use Auth;
use App\Answer;
use Illuminate\Database\Eloquent\Model;

class Questionnarie extends Model
{
    protected $hidden = ['inquiry_id', 'created_at', 'updated_at'];

    public function inquiry() {
    	return $this->belongsTo('App\Inquiry');
    }
	
		public function questions() {
    	return $this->hasMany('App\Question')->orderBy('name');
    }

		public function getQuestionsUserHasAnsweredOnQuestionnarie($id) {
			return $this->with('questions.answers')->whereHas('questions', function ($q) use ($id) {
				$q->where('questionnarie_id', '=', $id)->whereHas('answers', function ($q) {
					$q->where('user_id', '=', Auth::user()->id);
				});
			})->where('inquiry_id', '=', Auth::user()->inquiry_id)->first();
		}

		public function getUserQuestionnaries() {
			return $this->with('questions.PossibleAnswerNames')->where([
				'inquiry_id' => Auth::user()->inquiry_id
			])->get();
		}

		public function getUserQuestionnarie($id) {
			return $this->with('questions.PossibleAnswerNames')->where([
				'id' => $id,
				'inquiry_id' => Auth::user()->inquiry_id
			])->first();
		}
		
		public function questionnaireNameAlreadyExistsWithinInquiry($inquiryId, $name) {
			return $this->where([
				'inquiry_id' => $inquiryId, 
				'name' => $name
			])->get()->count() > 0;
		}

		public static function questionnaireExistsWithinInquiry($id, $inquiryId) {
			return (new static)->where([
				'id' => $id,
				'inquiry_id' => $inquiryId
			])->exists();
		}

		public static function fetchQuestionnaireRelatedToInquiry($id, $inquiryId) {
			return (new static)::with('inquiry', 'questions.PossibleAnswerNames')
				->where(['inquiry_id' => $inquiryId])->findOrFail($id);
		}
}

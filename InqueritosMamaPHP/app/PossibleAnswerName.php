<?php

namespace App;

use DB;
use App\Question;
use App\Traits\DecodeJsonFields;
use Illuminate\Database\Eloquent\Model;

class PossibleAnswerName extends Model
{	
    protected $table = 'possible_answer_names';
	
	protected $casts = [
		'possible_answer_names' => 'array'
	];

    protected $hidden = ['created_at', 'updated_at'];
	
	public function doesPossibleAnswerNameObjectExist($possibleAnswerNamesInput) {
		return $this->possibleAnswerNamesQuery($possibleAnswerNamesInput)->count() > 0;
	}
	
	public function getPossibleAnswerNameObject($possibleAnswerNamesInput) {
		if ($this->doesPossibleAnswerNameObjectExist($possibleAnswerNamesInput)) {
			return $this->possibleAnswerNamesQuery($possibleAnswerNamesInput)->first();
		}
	}
	
	public function insertPossibleAnswerNameObject($possibleAnswerNamesInput) {
		$this->possible_answer_names = json_decode($this->parseRequestInput($possibleAnswerNamesInput));
		
		if ($this->save()) {
			return $this;
		}
	}
	
	private function possibleAnswerNamesQuery($possibleAnswerNamesInput) {
		return $this->whereRaw("possible_answer_names = TO_JSONB(ARRAY[" . "'" . implode("','", $this->parseRequestInput($possibleAnswerNamesInput, false)) . "'" . "])");
	}
	
	private function parseRequestInput($possibleAnswerNamesInput, $json = true) {
		if ($json) {
			return json_encode(explode(',', preg_replace('/\s+/', '', $possibleAnswerNamesInput)));
		}
		return explode(',', preg_replace('/\s+/', '', $possibleAnswerNamesInput));
	}
}

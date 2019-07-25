<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    protected $hidden = ['questionnarie_id', 'possible_answer_names_id', 'created_at', 'updated_at'];

    public function questionnarie() {
    	return $this->belongsTo('App\Questionnarie');
    }

    public function PossibleAnswerNames() {
    	return $this->belongsTo('App\PossibleAnswerName');
    }

    public function answers() {
    	return $this->hasMany('App\Answer');
    }
	
	public static function questionNameAlreadyExistsWithinQuestionnaire($questionnaireId, $name) {
		return (new static)->where([
            'questionnarie_id' => $questionnaireId, 
            'name' => $name
        ])->exists();
    }
    
    public static function findQuestionWithinQuestionnaire($id, $questionnaireId) {
        return (new static)->with('questionnarie.inquiry', 'PossibleAnswerNames')->where([
            'id' => $id, 
            'questionnarie_id' => $questionnaireId
        ])->firstOrFail();
    }
}

<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class QuestionResource extends Resource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'type' => 'Question',
            'id' => $this->id,
			'name' => $this->name,
			'description' => $this->description,
			'possible-answers' => $this->possible_answers,
            'possible-answer-names' => $this->possibleAnswerNames,
            'questionnaire' => $this->questionnarie
		];
    }
}

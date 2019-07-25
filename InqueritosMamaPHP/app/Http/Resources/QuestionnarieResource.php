<?php

namespace App\Http\Resources;

use App\Http\Resources\InquiryResource;
use Illuminate\Http\Resources\Json\Resource;

class QuestionnarieResource extends Resource
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
            'type' => 'Questionnarie',
            'id' => $this->id,
            'name' => $this->name,
			'inquiry' => $this->inquiry->name,
            'questions' => $this->questions
        ];
    }
}

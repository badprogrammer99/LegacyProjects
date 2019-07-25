<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class InquiryResource extends Resource
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
			'type' => 'Inquiry',
            'id' => $this->id,
			'name' => $this->name,
            'questionnaries' => $this->questionnaries
		];
    }
}

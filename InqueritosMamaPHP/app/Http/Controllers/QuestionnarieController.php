<?php

namespace App\Http\Controllers;

use App\Questionnarie;
use App\Http\Resources\QuestionnarieCollection;
use App\Http\Resources\QuestionnarieResource;
use Illuminate\Http\Request;

class QuestionnarieController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Questionnarie $questionnarie)
    {
        return new QuestionnarieCollection($questionnarie->with('inquiry')->orderBy('name')->get());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
		$questionnarie = new Questionnarie();
		
		$questionnarie->inquiry_id = $request->input('inquiry_id');
        $questionnarie->name = $request->input('questionnarie_name');

        if ($questionnarie->save()) {
            return response()->json($questionnarie, 200);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int $inquiryId
     * @param  int $questionnarieId
     * @return \Illuminate\Http\Response
     */
    public function show($inquiryId, $questionnarieId)
    {
        return new QuestionnarieResource(
            Questionnarie::fetchQuestionnaireRelatedToInquiry($questionnarieId, $inquiryId));
    }
	
	/**
     * Check duplicate questionnaire names contained within the same inquiry.
     *
     * @param  App\Questionnarie  $questionnaire
	 * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
	public function checkForDuplicateQuestionnaireNames(Questionnarie $questionnaire, Request $request) {
		$inquiryId = $request->input('inquiry_id');
		$questionnaireName = $request->input('questionnaire_name');
		
		if ($questionnaire->questionnaireNameAlreadyExistsWithinInquiry($inquiryId, $questionnaireName)) { 
			return response()->json([
				'valid' => false,
				'message' => 'Este questionário já existe na base de dados.'
			]);
		}
		return response()->json([
			'valid' => true,
			'message' => ''
		]);
	}

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $questionnarie = Questionnarie::findOrFail($id);
		
        if ($questionnarie->delete()) {
			return response()->json(['msg' => 'Record deleted succesfully'], 200);
		}
    }
}

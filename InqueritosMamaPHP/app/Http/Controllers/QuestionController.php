<?php

namespace App\Http\Controllers;

use App\Questionnarie;
use App\PossibleAnswerName;
use App\Question;
use App\Http\Resources\QuestionCollection;
use App\Http\Resources\QuestionResource;
use Illuminate\Http\Request;

class QuestionController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Question $question)
    {
        return new QuestionCollection($question->with('questionnarie', 'PossibleAnswerNames')->orderBy('name', 'asc')->get());
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
    public function store(PossibleAnswerName $possibleAnswerName, Request $request)
    {
		$questionnaireId = $request->input('questionnaire_id');
        $questionName = $request->input('question_name');
		$questionDescription = $request->input('question_description');
		$possibleAnswers = $request->input('possible_answers');
		$possibleAnswerNamesInput = $request->input('possible_answer_names');
		
		if ($possibleAnswerName->doesPossibleAnswerNameObjectExist($possibleAnswerNamesInput) > 0) {
			$possibleAnswerNameId = $possibleAnswerName->getPossibleAnswerNameObject($possibleAnswerNamesInput)->id;
		} else {
			$possibleAnswerNameId = $possibleAnswerName->insertPossibleAnswerNameObject($possibleAnswerNamesInput)->id;
		}
		
		$question = new Question();
		
		$question->questionnarie_id = $questionnaireId;
		$question->possible_answer_names_id = $possibleAnswerNameId;
		$question->name = $questionName;
		$question->description = $questionDescription;
		$question->possible_answers = $possibleAnswers;
		
		if ($question->save()) {
			return response()->json($question->load('PossibleAnswerNames'), 200);
		}
    }
	
	/**
     * Check duplicate question names contained within the same questionnaire.
     *
     * @param  App\Question  $question
	 * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
	public function checkForDuplicateQuestionNames(Question $question, Request $request) {
		$questionnaireId = $request->input('questionnaire_id');
		$questionName = $request->input('question_name');
		
		if ($question->questionNameAlreadyExistsWithinQuestionnaire($questionnaireId, $questionName)) {
			return response()->json([
				'valid' => false,
				'message' => 'Esta questão já existe na base de dados.'
			]);
		}
		return response()->json([
			'valid' => true,
			'message' => ''
		]);
	}

    /**
     * Display the specified resource.
     *
     * @param  int  $inquiryId
     * @param  int  $questionnarieId
     * @param  int  $questionId
     * @return \Illuminate\Http\Response
     */
    public function show($inquiryId, $questionnaireId, $questionId)
    {
        $questionnaireExistsWithinInquiry = 
            Questionnarie::questionnaireExistsWithinInquiry($questionnaireId, $inquiryId);

        if ($questionnaireExistsWithinInquiry) {
            return new QuestionResource(Question::findQuestionWithinQuestionnaire($questionId, $questionnaireId)); 
        } else {
            abort(500);
        }
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
        return response()->json($request, $id);

        $questionnaireExistsWithinInquiry = Questionnarie::questionnaireExistsWithinInquiry(
            $request->input('questionnaire_id'), $request->input('inquiry_id'));

        if ($questionnaireExistsWithinInquiry) {
            $question = Question::findQuestionWithinQuestionnaire($id, $request->input('questionnaire_id'));
            
            $question->name = $request->input('name');
            $question->description = $request->input('description');
            $question->possible_answer_names = $request->input('possible_answer_names');
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
		$question = Question::findOrFail($id);
		
        if ($question->delete()) {
			return response()->json(['msg' => 'Record deleted succesfully'], 200);
		}
    }
}

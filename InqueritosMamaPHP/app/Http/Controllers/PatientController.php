<?php

namespace App\Http\Controllers;

use Auth;
use Mail;
use App\Answer;
use App\Questionnarie;
use App\Inquiry;
use App\User;
use App\Http\Resources\AnswerResource;
use App\Http\Resources\PatientCollection;
use App\Http\Resources\QuestionCollection;
use App\Http\Resources\QuestionnarieCollection;
use App\Http\Resources\QuestionnarieResource;
use App\Http\Resources\InquiryCollection;
use App\Mail\EnviarPasswordMail;
use Illuminate\Http\Request;

class PatientController extends Controller
{	
	public function getUserId() {
		return response()->json(['user_id' => Auth::user()->patient_id]);
	}
	
	public function createNewPatient(User $user, Request $request) {
		$inquiryId = $request->input('inquiry_id');
		$userId = $request->input('patient_id');
		$userName = $request->input('patient_name');
				
		if ($user->createNewUser($inquiryId, $userId, $userName, false)) {
			return response()->json(['status' => true], 200);
		}
		return response()->json(['status' => false], 200);
	}	
	
	public function createNewAdministrator(User $user, Request $request) {
		$userId = $request->input('admin_id');
		$userName = $request->input('admin_name');
				
		if ($user->createNewUser(null, $userId, $userName, true)) {
			return response()->json(['status' => true], 200);
		}
		return response()->json(['status' => false], 200);
	}
	
	public function checkForDuplicateUserIds(User $user, Request $request) {
		$userId = $request->input('user_id');
		
		if ($user->checkForDuplicateUserIds($userId)) {
			return response()->json([
				'valid' => false,
				'message' => 'Este número já existe na base de dados.'
			]);
		}
		return response()->json([
			'valid' => true,
			'message' => ''
		]);
	}
	
	public function sendUserPasswordMail(User $user, Request $request) {
		$user = $user->checkIfUserExistsAndFetch($request->input('user_id'));
		$email = $request->input('user_email');
		
		if ($user == null) {
			abort(404);
		}
		
		Mail::to($email)->send(new EnviarPasswordMail($user->patient_id, decrypt($user->password)));
		
		return response()->json(['status' => true], 200);
	}
	
	public function setUserPassword(User $user, Request $request) {
		if ($request->filled('password')) {
			$password = $request->input('password');
		
			if ($user->setUserPassword(Auth::user()->patient_id, $password)) {
				return response()->json(['status' => true], 200);
			}
			return response()->json(['status' => false], 200);
		} else {
			return response()->json(['Malformed request.'], 500);
		}
	}
	
	public function setUserAnswers(Request $request) {
		foreach ($request->data as $data) {
			$answer = new Answer();
			$answer->user_id = Auth::user()->id;
			$answer->question_id = $data['questionId'];
			if ($data['answer'] == '') {
				$answer->answer = null;
			} else {
				$answer->answer = json_encode($data['answer']);
			}
			$answer->observations = $data['observations'];
			$answer->save();
		}
		return response()->json(["status" => true], 200);
	}
	
	public function showUserInquiries(Inquiry $inquiry) {
		return new InquiryCollection($inquiry->getUserInquiries());
	}

	public function getQuestionnariesUserHasAnsweredOnInquiry(Inquiry $inquiry) {
		return new InquiryCollection($inquiry->getQuestionnariesUserHasAnsweredOnInquiry());
	}

	public function getQuestionsUserHasAnsweredOnQuestionnarie(Questionnarie $questionnarie, $questionnarieId) {
		return new QuestionnarieResource($questionnarie->getQuestionsUserHasAnsweredOnQuestionnarie($questionnarieId));
	}

	public function showUserQuestionnarie(Questionnarie $questionnarie, $questionnarieId) {
		return new QuestionnarieResource($questionnarie->getUserQuestionnarie($questionnarieId));
	}

	public function showUserQuestionnaries(Questionnarie $questionnarie) {
		return new QuestionnarieCollection($questionnarie->getUserQuestionnaries());
	}

    public function getQuestionAnswerForUserId(Answer $answer, $questionnarieId, $questionId) {
    	return new AnswerResource($answer->getQuestionAnswerForUserId($questionnarieId, $questionId));
	}
}
<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('login', ['as' => 'login', 'uses' => 'Auth\LoginController@showLoginForm']);
Route::post('login', ['as' => 'login.post', 'uses' => 'Auth\LoginController@login']);
Route::group(['middleware' => ['admin']], function () {
	Route::post('logout', function () {
		Auth::logout();
	});
	Route::group(['prefix' => 'api/admin'], function() {
		Route::get('get-authenticated-admin-id', function () {
			return response()->json(["user_id" => Auth::user()->patient_id]);
		});
		Route::get('inquiries/generate-inquiry-pdf/{id}', 'InquiryController@generateInquiryPdf');
		Route::resource('inquiries', 'InquiryController');
		Route::resource('questionnaries', 'QuestionnarieController');
		Route::resource('inquiries.questionnaries', 'QuestionnarieController')->parameters([
			'inquiries' => 'inquiryId',
			'questionnaries' => 'questionnarieId'
		]);
		Route::resource('questions', 'QuestionController');
		Route::resource('inquiries.questionnaries.questions', 'QuestionController')->parameters([
			'inquiries' => 'inquiryId',
			'questionnaries' => 'questionnarieId',
			'question' => 'questionId'
		]);
		Route::post('create-new-patient', 'PatientController@createNewPatient');
		Route::post('create-new-administrator', 'PatientController@createNewAdministrator');
		Route::post('check-duplicate-user-ids', 'PatientController@checkForDuplicateUserIds');
		Route::post('send-email-with-password', 'PatientController@sendUserPasswordMail');
		Route::post('check-duplicate-questionnaire-names', 'QuestionnarieController@checkForDuplicateQuestionnaireNames');
		Route::post('check-duplicate-question-names', 'QuestionController@checkForDuplicateQuestionNames');
	});
	Route::get('/{any}', function () {
		return view('layouts.app');	
	})->where('any', '.*');
});
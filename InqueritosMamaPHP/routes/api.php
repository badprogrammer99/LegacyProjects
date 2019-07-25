<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::group(['prefix' => 'user'], function () {
	Route::post('login', 'Auth\ApiLoginController@login');
	Route::group(['middleware' => ['auth:api']], function () {
		Route::get('get-user-id', 'PatientController@getUserId');
		Route::get('inquiries', 'PatientController@showUserInquiries');
		Route::get('questionnaries', 'PatientController@showUserQuestionnaries');
		Route::get('questionnaries/{questionnarieId}', 'PatientController@showUserQuestionnarie');
		Route::get('inquiry/questionnaries/questions/answers', 'PatientController@getQuestionnariesUserHasAnsweredOnInquiry');
		Route::get('inquiry/questionnarie/{questionnarieId}/questions/answers', 'PatientController@getQuestionsUserHasAnsweredOnQuestionnarie');
		Route::get('inquiry/questionnarie/{questionnarieId}/question/{questionId}/answer', 'PatientController@getQuestionAnswerForUserId');
		Route::post('change-password', 'PatientController@setUserPassword');
		Route::post('send-user-answers', 'PatientController@setUserAnswers');
	});
});
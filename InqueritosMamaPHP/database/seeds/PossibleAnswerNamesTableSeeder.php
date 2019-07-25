<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class PossibleAnswerNamesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
		$booleanPossibleAnswerNames = json_encode([
			"true",
			"false"
		]);
		
		$stringPossibleAnswerNames = json_encode([
			"A",
			"B",
			"C",
			"D",
			"E"
		]);
		
		$compositeAnswerNames = json_encode([
			"A" => [
				"A",
				"B"
			],
			"B" => [
				"A",
				"B"
			],
			"C" => [
				"A",
				"B"
			]
		]);
		
		DB::table('possible_answer_names')->insert([
			'possible_answer_names' => $booleanPossibleAnswerNames
		]);
	
		DB::table('possible_answer_names')->insert([
			'possible_answer_names' => $stringPossibleAnswerNames
		]);	
		
		DB::table('possible_answer_names')->insert([
			'possible_answer_names' => $compositeAnswerNames
		]);	
    }
}


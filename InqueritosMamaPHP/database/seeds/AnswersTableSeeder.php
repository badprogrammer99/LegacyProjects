<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class AnswersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
		$user_id = 2;
		
        $answersArray = [
			$user_id => [
				[
					1,
					'A'
				],
				[
					2,
					'A'
				],
				[
					3,
					'B'
				],
				[
					4,
					'C'
					
				],
				[
					5,
					'D'
				]
			]
		];
		
		foreach ($answersArray as $userId => $answerArray) {
			foreach ($answerArray as $answerDetails) {
				DB::table('answers')->insert([
					'user_id' => $userId,
					'question_id' => $answerDetails[0],
					'answer' => $answerDetails[1],
					'created_at' => new \DateTime(),
					'updated_at' => new \DateTime()
				]);
			}
		}
    }
}

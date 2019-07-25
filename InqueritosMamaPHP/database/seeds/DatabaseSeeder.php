<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
		$this->call(InquiriesTableSeeder::class);
		$this->call(UsersTableSeeder::class);
		$this->call(QuestionnariesTableSeeder::class);
		$this->call(PossibleAnswerNamesTableSeeder::class);
		$this->call(QuestionsTableSeeder::class);
		$this->call(AnswersTableSeeder::class);
    }
}

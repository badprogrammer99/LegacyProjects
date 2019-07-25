<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class QuestionnariesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $questionnarieNames = ["Funcionários", "Estabelecimento", "Satisfação"];
		
		foreach ($questionnarieNames as $questionnarieName) {
			if ($questionnarieName == 'Satisfação') {
				DB::table('questionnaries')->insert([
					'inquiry_id' => 2,
					'name' => $questionnarieName,
					'created_at' => new \DateTime(),
					'updated_at' => new \DateTime()
				]);
				continue;
			}
			DB::table('questionnaries')->insert([
				'inquiry_id' => 1,
				'name' => $questionnarieName,
				'created_at' => new \DateTime(),
				'updated_at' => new \DateTime()
			]);
		}
    }
}

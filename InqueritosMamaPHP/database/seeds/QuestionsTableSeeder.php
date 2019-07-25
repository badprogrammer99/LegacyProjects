<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class QuestionsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $questionNames = [
			1 => [
				[
					2,
					'Avaliação do atendimento',
					'Avalie o atendimento que lhe foi dado no nosso hospital',
					1
				],
				[
					2,
					'Avaliação da hospitalidade',
					'Avalie a hospitalidade com que foi atendido pelos vosso funcionários',
					1
				]
			],
			2 => [
				[
					2,
					'Avaliação do estacionamento',
					'Avalie o estacionamento do nosso hospital',
					1
				],
				[
					2,
					'Avaliação da casa-de-banho',
					'Avalie as instalações sanitárias',
					1
				],
				[
					2,
					'Avaliação da cantina',
					'Avalie a cantina e a qualidade da sua comida',
					1
				]
			],
			3 => [
				[
					2,
					'Avaliação da satisfação do tratamento',
					'Avalie a qualidade e a satisfação que teve durante o seu tratamento',
					2
				],
				[
					3,
					'Avaliação da satisfação com o serviço geral',
					'Avalie a satisfação geral que tem com o hospital',
					2
				]
			]
		];
		
		foreach ($questionNames as $questionnarieId => $questionObjects) {
			foreach ($questionObjects as $questionObject) {
				DB::table('questions')->insert([
					'questionnarie_id' => $questionnarieId,
					'possible_answer_names_id' => $questionObject[0],
					'name' => $questionObject[1],
					'description' => $questionObject[2],
					'possible_answers' => $questionObject[3],
					'created_at' => new \DateTime(),
					'updated_at' => new \DateTime()
				]);
			}
		}
    }
}

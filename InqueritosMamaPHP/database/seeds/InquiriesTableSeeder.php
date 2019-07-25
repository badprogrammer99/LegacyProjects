<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class InquiriesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $inquiryNames = ['Pós-Parto', 'Serviço de Oncologia'];
		
		foreach ($inquiryNames as $inquiryName) {
			DB::table('inquiries')->insert([
				'name' => $inquiryName,
				'created_at' => new \DateTime(),
				'updated_at' => new \DateTime()
			]);
		}
    }
}

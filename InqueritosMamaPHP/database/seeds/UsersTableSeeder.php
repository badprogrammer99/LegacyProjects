<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->insert([
			[
				'patient_id' => 170100228,
				'patient_name' => 'José Simões',
				'password' => encrypt('123456'),
				'created_at' => new \DateTime(),
				'updated_at' => new \DateTime(),
				'is_admin' => 1
			],
			[
				'patient_id' => 169696969,
				'patient_name' => 'Ruben Feliciano',
				'inquiry_id' => 1,
				'password' => encrypt('abcdef'),
				'created_at' => new \DateTime(),
				'updated_at' => new \DateTime()
			]
			]);
    }
}

<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
			$table->engine = 'InnoDB';
            $table->increments('id');
            $table->integer('patient_id')->unique()->unsigned();
            $table->integer('inquiry_id')->unsigned()->nullable();
			$table->string('patient_name');
            $table->string('password');
            $table->rememberToken();
            $table->foreign('inquiry_id')->references('id')->on('inquiries')
                                                           ->onUpdate('cascade')
                                                           ->onDelete('cascade');
			$table->tinyInteger('is_admin')->default(0);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}

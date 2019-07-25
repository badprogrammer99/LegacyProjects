<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateQuestionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('questions', function (Blueprint $table) {
			$table->engine = 'InnoDB';
            $table->increments('id');
			$table->integer('questionnarie_id')->unsigned();
			$table->integer('possible_answer_names_id')->unsigned();
			$table->string('name');
			$table->text('description');
			$table->integer('possible_answers');
			$table->foreign('questionnarie_id')->references('id')->on('questionnaries')
																->onUpdate('cascade')
																->onDelete('cascade');
			$table->foreign('possible_answer_names_id')->references('id')->on('possible_answer_names')
																		 ->onUpdate('cascade')
																		 ->onDelete('cascade');
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
        Schema::dropIfExists('questions');
    }
}

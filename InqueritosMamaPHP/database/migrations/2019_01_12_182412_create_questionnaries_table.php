<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateQuestionnariesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('questionnaries', function (Blueprint $table) {
			$table->engine = 'InnoDB';
            $table->increments('id');
			$table->integer('inquiry_id')->unsigned();
			$table->string('name');
			$table->foreign('inquiry_id')->references('id')->on('inquiries')
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
        Schema::dropIfExists('questionnaries');
    }
}

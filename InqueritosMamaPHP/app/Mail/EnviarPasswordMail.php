<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Contracts\Queue\ShouldQueue;

class EnviarPasswordMail extends Mailable
{
    use Queueable, SerializesModels;
	
	public $patient_id;
	public $password;

    /**
     * Create a new message instance.
     *
     * @return void
     */
    public function __construct($patient_id, $password)
    {
		$this->patient_id = $patient_id;
        $this->password = $password;
		$this->subject('Foi inscrito no Inquérito da Mamã do Hospital de Santarém');
    }

    /**
     * Build the message.
     *
     * @return $this
     */
    public function build()
    {
        return $this->view('emails.password_mail');
    }
}

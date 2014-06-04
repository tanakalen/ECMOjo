<?php

// Include mailing stuff
require_once($root.'lib/common/mail.inc.'.$phpEx);

class MailLogic {

  static function send($to, $subject, $message) {
    // add credentials
    $message .= "\n"
              . "ECMOjo\n"
              . "\n"
              . "You receive this message based on your registration with the Manikin Study. "
              . "\n"
              . "Do NOT reply to this message, use telemed@hawaii.edu "
              . "for questions and comments. This message is NOT spam. If you "
              . "received this message in error please state your complain and "
              . "allow 72h for processing your request. We apologize for any " 
              . "inconveniences this might cause you.";

    // and send!
    Mailer::send($to, $subject, $message);
  }
}

?>
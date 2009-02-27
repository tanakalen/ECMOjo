<?php

require_once($root.'lib/common/mail/class.phpmailer.php');

class Mailer {

  static function send($to, $subject, $message) {
    global $email_full_name;
    global $email_address;
    $to_name    = self::parseName($to);
    $to_address = self::parseAddress($to);
    return self::sendEmail($email_full_name, $email_address, $to_name, $to_address, $subject, $message);
  }
  
  #-- private functions
  
  function parseName($email) {
    $pos0 = strpos($email, "<");
    if ($pos0) {
      return substr($email, 0, $pos0);
    }
    else {
      return "";
    }
  }
  
  function parseAddress($email) {
    $pos0 = strpos($email, "<");
    if ($pos0) {
      $pos1 = strpos($email, ">");
      return substr($email, $pos0 + 1, $pos1 - $pos0);     
    }
    else {
      return $email;
    }
  }
  
  function sendEmail($from_name, $from_address, $to_name, $to_address, $subject, $message) {
    global $mail_sender;
    if ($mail_sender == 'mail') {
      $headers = 'From: ' . $from_name . ' <' . $from_address . ">\r\n"
               . 'Reply-To: ' . $from_address . "\r\n"
               . 'X-Mailer: PHP/' . phpversion();
      mail($to_address, $subject, $message, $headers);
    }
    else if ($mail_sender == 'smtp') {  
      global $root;
      global $smtp_host;
      global $smtp_auth;
      global $smtp_username;
      global $smtp_password;
      global $smtp_port;
      
      $phpMailer = new PHPMailer();
      $phpMailer->Mailer    = "smtp";
      $phpMailer->PluginDir = $root.'lib/common/mail/';
      $phpMailer->Host      = $smtp_host;
      $phpMailer->SMTPAuth  = $smtp_auth;
      $phpMailer->Username  = $smtp_username;
      $phpMailer->Password  = $smtp_password;
      $phpMailer->Port      = $smtp_port;
      $phpMailer->AddAddress($to_address, $to_name);    
      $phpMailer->FromName = $from_name;
      $phpMailer->From     = $from_address;
      $phpMailer->Subject  = $subject;
      $phpMailer->Body     = $message;
      return $phpMailer->Send();
    }
  }
}

?>
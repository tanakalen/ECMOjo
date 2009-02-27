<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);
require_once($root.'lib/logic/mail_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {

  function PageController() {
    parent::Controller();
    
    // parse input parameters
    $action = NULL;
    if (isset($_REQUEST['cmd'])) {
      $action = $_REQUEST['cmd'];
    }
    
    if ($action == 'retrieve') {
      $email = $_REQUEST['email'];
      if (!eregi('^[a-zA-Z0-9._-]{1,50}@[a-zA-Z0-9-]{1,40}\.[a-zA-Z.]{2,10}$', $email)) {
        $this->errors[] = "Email address is invalid or empty.";
      }
      else {
        $user = UserLogic::getUserByEmail($email);
        if ($user == NULL) {
          $this->errors[] = "Invalid Account.";
        }
        else {
          $to = $email;
          $subject = "ECMOjo - Password Request";
          $message = "Dear $user->first_name $user->last_name,\n"
                   . "\n"
                   . "Find below username and password to login to the ECMOjo:\n"
                   . "  Username: $user->username\n"
                   . "  Password: $user->password\n";
          MailLogic::send($to, $subject, $message);
          $this->message = "Password sent!";
        }
      }
    }
  }
}

?>
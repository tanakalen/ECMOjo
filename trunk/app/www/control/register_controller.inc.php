<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);
require_once($root.'lib/logic/mail_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {
  var $user;

  function PageController() {
    parent::Controller();

    // parse input parameters
    $action = NULL;
    if (isset($_REQUEST['cmd'])) {
      $action = $_REQUEST['cmd'];
    }

    $this->user = new User();

    if ($action == 'register') {
      if (!eregi('^[a-zA-Z0-9_]{1,50}$', $_REQUEST['username'])) {
        $this->errors[] = "Username is invalid or empty.";
      }
      else if (UserLogic::getUserByUsername($_REQUEST['username']) != NULL) {
        $this->errors[] = "Username already taken.";
      }

      if (!eregi('^.{1,50}$', $_REQUEST['password'])) {
        $this->errors[] = "Password is invalid or empty.";
      }

      if (!eregi('^[a-zA-Z0-9 ]{1,50}$', $_REQUEST['first_name'])) {
        $this->errors[] = "First name is invalid or empty.";
      }

      if (!eregi('^[a-zA-Z0-9 ]{1,50}$', $_REQUEST['last_name'])) {
        $this->errors[] = "Last name is invalid or empty.";
      }

      if (!eregi('^[a-zA-Z0-9._-]{1,50}@[a-zA-Z0-9-]{1,40}\.[a-zA-Z.]{2,10}$', $_REQUEST['email'])) {
        $this->errors[] = "Email address is invalid or empty.";
      }
      else if (UserLogic::getUserByEmail($_REQUEST['email']) != NULL) {
        $this->errors[] = "Email address already taken.";
      }

      $this->parseForm();
      if (sizeof($this->errors) == 0) {
        // store user
        UserLogic::saveUser($this->user);

        // send confirmation email
        $user = $this->user;
        $to = $user->email;
        $subject = "ECMOjo - Successfully Registered";
        $message = "Dear $user->first_name $user->last_name,\n"
                 . "\n"
                 . "You successfully registered on the ECMOjo:\n"
                 . "  Username: $user->username\n"
                 . "  Email: $user->email\n"
                 . "\n";
        MailLogic::send($to, $subject, $message);

        // continue
        header('Location: login.php?cmd=register');
        exit;
      }
    }
  }

  private function parseForm() {
    $this->user->username = $_REQUEST['username'];
    $this->user->password = $_REQUEST['password'];
    $this->user->first_name = $_REQUEST['first_name'];
    $this->user->last_name = $_REQUEST['last_name'];
    $this->user->email = $_REQUEST['email'];
  }
}

?>
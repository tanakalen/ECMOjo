<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {
  var $user;
  var $number_of_updates;
  
  function PageController() {
    parent::Controller();
  
    // allows access to only logged in users
    Security::check();

    $action = NULL;
    if (isset($_REQUEST['cmd'])) {
      $action = $_REQUEST['cmd'];
    }
    $this->user = Security::user();

    if ($action == 'update') {
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
      else if ($this->user->email != $_REQUEST['email'] && UserLogic::getUserByEmail($_REQUEST['email']) != NULL) {
        $this->errors[] = "Email address already taken.";
      }

      if (sizeof($this->errors) == 0) {
        $this->parseForm();
        UserLogic::saveUser($this->user);
        $this->message = "Account updated.";
      }
    }
  }

  private function parseForm() {
    $this->user->password = $_REQUEST['password'];
    $this->user->first_name = $_REQUEST['first_name'];
    $this->user->last_name = $_REQUEST['last_name'];
    $this->user->email = $_REQUEST['email'];
  }
}

?>
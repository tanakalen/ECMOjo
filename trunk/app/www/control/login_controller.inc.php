<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {
  var $user;
  var $continue;

  function PageController() {
    parent::Controller();

    // parse input parameters
    $action = NULL;
    if (isset($_REQUEST['cmd'])) {
	    $action = $_REQUEST['cmd'];
    }
    $this->user = new User();

    $this->continue = "account.php";
    if (isset($_REQUEST['continue'])) {
	    $this->continue = $_REQUEST['continue'];
    }

    if ($action == 'login') {
      $this->parseForm();

      if (UserLogic::login($this->user)) {
        // user exists: make login permanent (add to security)
        Security::login($this->user);

        // check if auto login
        $auto_login_enabled = isset($_REQUEST['auto_login']);
        AutoLogin::setEnabled($auto_login_enabled, $this->user->username, $this->user->password);

        // continue
        header('Location: ' . $this->continue);
        exit;
      }
      else {
        // error
        $this->errors[] = "Invalid username or password.";
      }
    }
    else if ($action == 'register') {
      $this->message = "Registration successful. Please login.";
    }
    else if ($action == 'redirect') {
      $this->errors[] = "Login required.";
    }
  }

  private function parseForm() {
    $this->user->username = $_REQUEST['username'];
    $this->user->password = $_REQUEST['password'];
  }
}

?>
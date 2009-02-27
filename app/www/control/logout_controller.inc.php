<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {

  function PageController() {
    parent::Controller();

    // execute the logout
    Security::logout();

    // disable auto login
    AutoLogin::setEnabled(false);

    // go to main page
    header('Location: index.php');
    exit;
  }
}

?>
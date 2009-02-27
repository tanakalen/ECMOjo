<?php

// Include the base stuff
require_once($root.'lib/base.inc.'.$phpEx);

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);

// The controller
class PageController extends Controller {
  var $total_users;

  function PageController() {
    parent::Controller();
  
    // load stats
    $this->total_users = UserLogic::getSize();
  }
}

?>
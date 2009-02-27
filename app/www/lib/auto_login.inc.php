<?php

// The logic stuff
require_once($root.'lib/logic/user_logic.inc.'.$phpEx);

class AutoLogin {

  static function execute() {
    if (!Security::user()) {
      if (AutoLogin::isEnabled()) {
        $user = new User();
        $user->username = AutoLogin::getUsername();
        $user->password = AutoLogin::getPassword();
        if (UserLogic::login($user)) {
          Security::login($user);
          AutoLogin::setEnabled(true, $user->username, $user->password);
        }
        else {
          AutoLogin::setEnabled(false);
        }
      }
    }
  }

  static function isEnabled() {
    if (isset($_COOKIE["x_auto_login_enabled"])) {
      return $_COOKIE["x_auto_login_enabled"] == "true";
    }
    else {
      return false;
    }
  }

  static function getUsername() {
    return $_COOKIE["x_auto_login_username"];
  }
 
  static function getPassword() {
    return $_COOKIE["x_auto_login_password"];
  }

  static function setEnabled($enabled, $username = '', $password = '') {
    if ($enabled) {
      $enabled = "true";
    }
    else {
      $enabled = "false";
    }
    $expire = time() + 5184000;  // expire in 60 days
    setCookie("x_auto_login_enabled", $enabled, $expire); 
    setCookie("x_auto_login_username", $username, $expire); 
    setCookie("x_auto_login_password", $password, $expire); 
  }
}

?>
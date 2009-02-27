<?php

class Security {

  static function check($permission = 0xffff, $redirect = NULL) {
    global $root;
    if (!Security::user()) {
      if ($redirect == NULL) {
        global $PHP_SELF;
        $redirect = htmlspecialchars($PHP_SELF).'?'.$_SERVER['QUERY_STRING'];
      }
      header('Location: '.$root.'login.php?cmd=redirect&continue='.$redirect);
      exit;
    }
    else if (!Security::hasAccess($permission)) {
      header('Location: '.$root.'noaccess.php');
      exit;
    }
  }
  
  static function hasAccess($permission = 0xffff) {
    if ((!Security::user()->isEnabled()) || ((Security::user()->permission & $permission) == 0)) {
      return false;
    }
    else {
      return true;
    }
  }
  
  static function user() {
    if (isset($_SESSION['user'])) {
      return $_SESSION['user'];
    }
    else {
      return NULL;
    }
  }
   static function login($user) {
    $_SESSION['user'] = $user;
  }
  
  static function logout() {
    $_SESSION['user'] = NULL;
  }
}

?>
<?php

// Enable output errors on the page
ini_set('display_errors', '1');

// disable magic quotes if they are turned on
if (get_magic_quotes_gpc()) {
  $in = array(&$_GET, &$_POST, &$_COOKIE, &$_REQUEST);
  while (list($k,$v) = each($in)) {
    foreach ($v as $key => $val) {
      if (!is_array($val)) {
        $in[$k][$key] = stripslashes($val);
        continue;
      }
      $in[] =& $in[$k][$key];
    }
  }
  unset($in); 
}

// Include the version number
require_once($root.'lib/version.inc');

// Include the model classes
require_once($root.'lib/model/user.inc.'.$phpEx);

// Include the setup data
require_once($root.'conf/setup.inc');

// Mailing 
//require_once($root.'lib/common/mail.inc.'.$phpEx);

// Include the DB classes
//require_once($root.'lib/common/db.inc.'.$phpEx);
//require_once($root.'lib/dao/dao_factory.inc.'.$phpEx);

// Include the security
session_start();
require_once($root.'lib/security.inc.'.$phpEx);

// Include and execute atuo login if applicable
require_once($root.'lib/auto_login.inc.'.$phpEx);
AutoLogin::execute();

// Include the Controller super class
require_once($root.'lib/controller.inc.'.$phpEx);

?>
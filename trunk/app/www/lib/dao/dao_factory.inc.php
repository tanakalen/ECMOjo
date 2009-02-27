<?php

require_once($root.'lib/dao/sql_user_dao.inc.'.$phpEx);

class DAOFactory {

  static function getDAO($vo_class) {
    global $db;
    switch ($vo_class) {
      case "User":    return new SQLUserDAO($db);
    }
  }
}

?>
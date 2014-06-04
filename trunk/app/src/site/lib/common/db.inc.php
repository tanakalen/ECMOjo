<?php

// include correct database
switch($dbms) {
	case 'mysql':
		require_once($root.'lib/common/db/mysql.'.$phpEx);
		break;

	case 'mysql4':
		require_once($root.'lib/common/db/mysql4.'.$phpEx);
		break;

	case 'postgres':
		require_once($root.'lib/common/db/postgres7.'.$phpEx);
		break;

	case 'mssql':
		require_once($root.'lib/common/db/mssql.'.$phpEx);
		break;

	case 'oracle':
		require_once($root.'lib/common/db/oracle.'.$phpEx);
		break;

	case 'msaccess':
		require_once($root.'lib/common/db/msaccess.'.$phpEx);
		break;

	case 'mssql-odbc':
		require_once($root.'lib/common/db/mssql-odbc.'.$phpEx);
		break;
}

// the global db variable
$db = NULL;

// the connection class
class DBConnection {

  static function connect() {
    global $db;
    global $dbhost;
    global $dbuser;
    global $dbpasswd;
    global $dbname;
    $db = new sql_db($dbhost, $dbuser, $dbpasswd, $dbname, false);
    if(!$db->db_connect_id) {
    	message_die(CRITICAL_ERROR, "Could not connect to the database");
    }
    
    // ensure we are using UTF8...
    $db->sql_query('SET NAMES \'utf8\'');
  }
  
  static function reconnect() {
    DBConnection::disconnect();
    DBConnection::connect();
  }
  
  static function disconnect() {
    global $db;
    $db->sql_close();
  }
}

// make the database connection.
DBConnection::connect();

?>
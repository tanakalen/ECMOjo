<?php

class SQLUserDAO {
  var $db;

  function SQLUserDAO(&$db) {
    $this->db =& $db;
  }

  function login(&$user) {
    $sql = "SELECT * FROM users WHERE `username` = '" . mysql_real_escape_string($user->username) . "' "
                               . "AND `password` = '" . mysql_real_escape_string($user->password) . "'";
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      $this->populate($user, $row);
      return true;
    }
    else {
      return false;
    }
  }

  function getUser($id) {
    $sql = "SELECT * FROM users WHERE `user_id` = " . mysql_real_escape_string($id);
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      $user = new User();
      $this->populate($user, $row);
      return $user;
    }
    else {
      return NULL;
    }
  }

  function saveUser(&$user) {
    if ($user->id == 0) {
      $this->insert($user);
    }
    else {
      $this->update($user);
    }
  }

  function deleteUser(&$user) {
    $sql = "DELETE FROM users WHERE `user_id` = " . mysql_real_escape_string($user->id);
    $this->db->sql_query($sql);
    $user->id = 0;
  }

  function getUsers($start, $limit) {
    $sql = "SELECT * FROM users LIMIT " . mysql_real_escape_string($start) . "," . mysql_real_escape_string($limit);
    $result = $this->db->sql_query($sql);
    $user_list = array();
    while ($row = $this->db->sql_fetchrow($result)) {
      $user = new User();
      $this->populate($user, $row);
      $user_list[] = $user;
    }
    return $user_list;
  }

  function getSize() {
    $sql = "SELECT count(*) as size FROM users";
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      return $row['size'];
    }
    else {
      return -1;
    }
  }

  function findUsers($search_string, $start, $limit) {
    $escaped_search_string = mysql_real_escape_string($search_string);
    $sql = "SELECT * FROM users WHERE `username` LIKE '%$escaped_search_string%' "
                                . "OR `first_name` LIKE '%$escaped_search_string%' "
                                . "OR `last_name` LIKE '%$escaped_search_string%' "
                                . "OR `email` LIKE '%$escaped_search_string%' "
         . "LIMIT " . mysql_real_escape_string($start) . "," . mysql_real_escape_string($limit);
    $result = $this->db->sql_query($sql);
    $user_list = array();
    while ($row = $this->db->sql_fetchrow($result)) {
      $user = new User();
      $this->populate($user, $row);
      $user_list[] = $user;
    }
    return $user_list;
  }

  function countUsers($search_string) {
    $escaped_search_string = mysql_real_escape_string($search_string);
    $sql = "SELECT count(*) as size FROM users WHERE `username` LIKE '%$escaped_search_string%' "
                                               . "OR `first_name` LIKE '%$escaped_search_string%' "
                                               . "OR `last_name` LIKE '%$escaped_search_string%' "
                                               . "OR `email` LIKE '%$escaped_search_string%'";
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      return $row['size'];
    }
    else {
      return -1;
    }
  }

  function getUserByEmail($email) {
    $sql = "SELECT * FROM users WHERE `email` = '" . mysql_real_escape_string($email) . "'";
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      $user = new User();
      $this->populate($user, $row);
      return $user;
    }
    else {
      return NULL;
    }
  }

  function getUserByUsername($username) {
    $sql = "SELECT * FROM users WHERE `username` = '" . mysql_real_escape_string($username) . "'";
    $result = $this->db->sql_query($sql);
    if ($row = $this->db->sql_fetchrow($result)) {
      $user = new User();
      $this->populate($user, $row);
      return $user;
    }
    else {
      return NULL;
    }
  }

  #-- private functions

  function populate(&$user, $row) {
    $user->id = $row['user_id'];
    $user->username = $row['username'];
    $user->password = $row['password'];
    $user->first_name = $row['first_name'];
    $user->last_name = $row['last_name'];
    $user->email = $row['email'];
    $user->permission = $row['permission'];
  }

  function update(&$user) {
    // IMPORTANT: do NOT update the permission (security risk!)
    $sql = "UPDATE users SET "
           . "`username`='" . mysql_real_escape_string($user->username) . "', "
           . "`password`='" . mysql_real_escape_string($user->password) . "', "
           . "`first_name`='" . mysql_real_escape_string($user->first_name) . "', "
           . "`last_name`='" . mysql_real_escape_string($user->last_name) . "', "
           . "`email`='" . mysql_real_escape_string($user->email) . "' "
         . "WHERE `user_id` = " . mysql_real_escape_string($user->id);
    $this->db->sql_query($sql);
  }

  function insert(&$user) {
    // IMPORTANT: do NOT update the permission (security risk!)
    $sql = "INSERT INTO users ("
           . "`user_id`, "
           . "`username`, "
           . "`password`, "
           . "`first_name`, "
           . "`last_name`, "
           . "`email`"
         . ") VALUES ("
           . "NULL, "
           . "'" . mysql_real_escape_string($user->username) . "', "
           . "'" . mysql_real_escape_string($user->password) . "', "
           . "'" . mysql_real_escape_string($user->first_name) . "', "
           . "'" . mysql_real_escape_string($user->last_name) . "', "
           . "'" . mysql_real_escape_string($user->email) . "'"
         . ")";
    $this->db->sql_query($sql);
    $user->id = $this->db->sql_nextid();
  }
}

?>
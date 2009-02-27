<?php

class User {
  var $id;
  var $username;
  var $password;
  var $first_name;
  var $last_name;
  var $email;
  var $permission;
  var $score;
  
  # Functions to check as well as enable/disable permissions
  
  const PERMISSION_ADMIN          = 0x0002;
  const PERMISSION_MODERATOR      = 0x0004;
  
  function isEnabled() {
    return ($this->permission & 0x0001) > 0;
  }
  function setEnabled($enabled) {
    if ($enabled) {
      $this->permission = $this->permission | 0x0001;
    }
    else {
      $this->permission = $this->permission & (0xffff - 0x0001);
    }
  }
  
  function isAdmin() {
    return ($this->permission & self::PERMISSION_ADMIN) > 0;
  }
  function setAdmin($admin) {
    if ($admin) {
      $this->permission = $this->permission | self::PERMISSION_ADMIN;
    }
    else {
      $this->permission = $this->permission & (0xffff - self::PERMISSION_ADMIN);
    }
  }
  
  function isModerator() {
    return ($this->permission & self::PERMISSION_MODERATOR) > 0;
  }
  function setModerator($moderator) {
    if ($moderator) {
      $this->permission = $this->permission | self::PERMISSION_MODERATOR;
    }
    else {
      $this->permission = $this->permission & (0xffff - self::PERMISSION_MODERATOR);
    }
  }
}

?>
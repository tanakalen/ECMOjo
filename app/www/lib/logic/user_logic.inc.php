<?php

class UserLogic {

  static function login(&$user) {
    $dao = DAOFactory::getDAO("User");
    return $dao->login($user);
  }
  
  static function getUser($id) {
    $dao = DAOFactory::getDAO("User");
    return $dao->getUser($id);
  }
  
  static function saveUser(&$user) {
    $dao = DAOFactory::getDAO("User");
    return $dao->saveUser($user);
  }
  
  static function deleteUser(&$user) {
    $dao = DAOFactory::getDAO("User");
    return $dao->deleteUser($user);
  }
  
  static function getUsers($start = 0, $limit = 10) {
    $dao = DAOFactory::getDAO("User");
    return $dao->getUsers($start, $limit);
  }
  
  static function getSize() {
    $dao = DAOFactory::getDAO("User");
    return $dao->getSize();
  }
  
  static function findUsers($searchString, $start = 0, $limit = 10) {
    $dao = DAOFactory::getDAO("User");
    return $dao->findUsers($searchString, $start, $limit);
  }
  
  static function countUsers($searchString) {
    $dao = DAOFactory::getDAO("User");
    return $dao->countUsers($searchString);
  }
  
  static function getUserByEmail($email) {
    $dao = DAOFactory::getDAO("User");
    return $dao->getUserByEmail($email);
  }

  static function getUserByUsername($username) {
    $dao = DAOFactory::getDAO("User");
    return $dao->getUserByUsername($username);
  }
}

?>
<?php

class Controller {
  // informational messages
  var $errors;
  var $message;
  
  function Controller() {
    // init variables for error and information messages
    $this->errors = array();
    $this->message = NULL;
    
  }
}

?>
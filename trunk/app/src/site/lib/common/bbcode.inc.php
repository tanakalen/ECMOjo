<?php

// the BBCode utility class (by Christoph Aschwanden)
class BBCode {

  static function input2bb($str) {
    // convert all line breaks to [br]
    $str = str_replace("\r\n", "[br]", $str);
    $str = str_replace("\n", "[br]", $str);
    
    // and return fixed code
    return $str;
  }
  
  // converts BBCode to HTML
  static function bb2html($str) {
    $str = htmlspecialchars($str);   // convert to HTML (e.g. <, >)
    
    // search and replace strings
    $simple_search = array(
        '/\[br\]/is',                               
        '/\[b\](.*?)\[\/b\]/is',                               
        '/\[i\](.*?)\[\/i\]/is',                               
        '/\[u\](.*?)\[\/u\]/is',
        '/\[url\=(.*?)\](.*?)\[\/url\]/is',                         
        '/\[url\](.*?)\[\/url\]/is',                             
        '/\[align\=(left|center|right)\](.*?)\[\/align\]/is',    
        '/\[img\](.*?)\[\/img\]/is',                            
        '/\[mail\=(.*?)\](.*?)\[\/mail\]/is',                    
        '/\[mail\](.*?)\[\/mail\]/is',                            
        '/\[size\=(.*?)\](.*?)\[\/size\]/is',                    
        '/\[color\=(.*?)\](.*?)\[\/color\]/is',        
    );
    $simple_replace = array(
        '<br>',
        '<b>$1</b>',
        '<i>$1</i>',
        '<u>$1</u>',
        '<a href="$1">$2</a>',
        '<a href="$1">$1</a>',
        '<div style="text-align: $1;">$2</div>',
        '<a href="$1"><img src="$1" width="100" style="border: 2px solid #0078C2;"></a>',
        '<a href="mailto:$1">$2</a>',
        '<a href="mailto:$1">$1</a>',
        '<span style="font-size: $1;">$2</span>',
        '<span style="color: $1;">$2</span>',
    );

    // do simple BBCode's
    $str = preg_replace ($simple_search, $simple_replace, $str);

    // and returned HTML code
    return $str;
  }
  
  // converts BBCode to plain text
  static function bb2plain($str) {
    $str = htmlspecialchars($str);   // convert to HTML (e.g. <, >)
    
    // search and replace strings
    $simple_search = array(
        '/\[br\]/is',                               
        '/\[b\](.*?)\[\/b\]/is',                               
        '/\[i\](.*?)\[\/i\]/is',                               
        '/\[u\](.*?)\[\/u\]/is',
        '/\[url\=(.*?)\](.*?)\[\/url\]/is',                         
        '/\[url\](.*?)\[\/url\]/is',                             
        '/\[align\=(left|center|right)\](.*?)\[\/align\]/is',    
        '/\[img\](.*?)\[\/img\]/is',                            
        '/\[mail\=(.*?)\](.*?)\[\/mail\]/is',                    
        '/\[mail\](.*?)\[\/mail\]/is',                            
        '/\[size\=(.*?)\](.*?)\[\/size\]/is',                    
        '/\[color\=(.*?)\](.*?)\[\/color\]/is',        
    );
    $simple_replace = array(
        ' ',
        '$1',
        '$1',
        '$1',
        '$2',
        '$1',
        '$2',
        '',
        '$2',
        '$1',
        '$2',
        '$2',
    );

    // do simple BBCode's
    $str = preg_replace ($simple_search, $simple_replace, $str);

    // and returned HTML code
    return $str;
  }
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>ECMOjo</title>

  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <script type="text/javascript" src="script/jquery/jquery.js"></script>
  <link href="style/stylesheet.css" type="text/css" rel="stylesheet">
</head>
<body>

<center>
<table border="0" cellspacing="0" cellpadding="0" height="100%" class="content">
<tr height="100%">
  <td width="9" valign="top" style="background-image: url(image/interface/b2_left.gif); background-repeat: repeat-y; background-position: top;"><img src="image/interface/spacer.gif" width="9" height="32"></td>
  <td>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" class="contentheader" height="75"><table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="2" width="983" height="75"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              </td>
              <td>
              </td>
            </tr>
            <tr>
              <td colspan="2">
              </td>
            </tr>
          </table></td>
        </tr>
        <tr style="background: #000000 url(image/interface/menu.gif) top center repeat-x;">
          <td class="contentnavbar" width="983" height="20" valign="top">
            &nbsp;&nbsp;&nbsp;
            <a href="index.php">Home</a> |
            <a href="webstart/ECMOjo.jnlp">Online Demo</a> |
            <a href="about.php">About</a>
          </td>
        </tr>
      </table></td>
  </tr>

  <tr>

<td width="160" valign="top">

<!-- MENU start -->
<div class="contentmenu">
  <br><br><br>
  <div class="arrowlistmenu">
    <h3>&divide; Menu</h3>
    <ul>
      <li><a href="index.php">Home</a>
      <li><a href="demo.php">Demo</a>
      <li><a href="screenshot.php">Screenshots</a>
      <li><a href="video.php">Videos</a>
      <li><a href="component.php">Components</a>
      <li><a href="download.php">Download</a>
      <li><a href="faq.php">FAQ</a>            
    </ul>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>       
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>    
    <br>
    <br>       
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
  </div>
</div>
<!-- MENU end -->

</td><td width="832" valign="top" class="contentfull"><table border="0" cellspacing="0" cellpadding="0"><tr><td width="100%" valign="top">

<!-- CONTENT start -->
<div class="contentmain">

<!-- ERROR + MESSAGE OUTPUT start -->
<?php
  if ($controller->message != NULL) {
    echo '<p class="success">';
    echo $controller->message . "<br>";
    echo '</p>';
  }

  $count = count($controller->errors);
  if ($count > 0) {
    echo '<p class="alert">';
    for ($i = 0; $i < $count; $i++) {
      echo $controller->errors[$i] . "<br>";
    }
    echo '</p>';
  }
?>
<!-- ERROR + MESSAGE OUTPUT end -->

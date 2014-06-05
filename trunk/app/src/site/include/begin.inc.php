<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>ECMOjo Simulator and Trainer - <?php echo $pageTitle; ?></title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link href="style/stylesheet.css" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="script/jquery/jquery.js"></script>
  <link rel="icon" href="favicon.ico" type="image/x-icon">
  <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>

<center>
<table border="0" cellspacing="0" cellpadding="0" height="100%" class="content">
  <tr height="85"> 
    <td style="background-image: url(image/interface/banner.jpg);" width="960" height="85">
  
<!-- BANNER start -->
<div class="banner" style="margin-left: 680; margin-top: 28">
  
  <a href="http://www.hanuola.com">Hanuola</a> &nbsp;&nbsp;&amp;&nbsp;&nbsp; <a href="http://www.tri.jabsom.hawaii.edu">Telehealth Research Institute</a><br>
  <a href="http://simtiki.org">SimTiki</a> &nbsp;&nbsp;&amp;&nbsp;&nbsp; <a href="http://www.simtikinihon.org/">シムチキ日本</a> &nbsp;&nbsp;&amp;&nbsp;&nbsp; <a href="http://www.noblemaster.com">Noble Master</a><br>
  <a href="http://www.jabsom.hawaii.edu">John A. Burns School of Medicine</a>

</div>
<!-- BANNER end -->
  
  </td>
  </tr>
  <tr> 
    <td style="line-height:38px; margin:0px; padding:0px; background-image: url(image/interface/menu-horizontal.gif);" width="960" height="38">
  
<!-- H-MENU start -->
<table border="0" cellspacing="0" cellpadding="0" height="100%" height="38"><tr height="38">
  <td width="224">&nbsp;</td>
  <td><a href="index.php" class="menubutton">Home</a></td>
  <td><a href="webstart/ECMOjo-Infant.jnlp" class="menubutton">Online Demo</a></td>
  <td><a href="about.php" class="menubutton">About</a></td>
</tr></table>
<!-- H-MENU end -->

  </td>
  </tr>
  
  <tr height="100%"><td><table border="0" cellspacing="0" cellpadding="0" width="960" height="100%"><tr>
    <td style="background: #EEEEEE" width="5"><img src="image/interface/fade-left-top.gif" width="5" height="240"></td>
    <td width="949">
      <table id="content_table" border="0" cellspacing="0" cellpadding="0" width="949" height="100%">
        <tr height="5"><td style="background-image: url(image/interface/body-shade.gif);" width="949" height="5"></td></tr>
        <tr><td style="background-image: url(image/interface/body-repeat.gif);">
          <table border="0" cellspacing="0" cellpadding="0" width="949" height="100%"><tr>
        <td width="219" valign="top">
          <table width="219"><tr width="219"><td width="219">
          
<!-- V-MENU start -->
<div class="contentmenu">
  <div class="arrowlistmenu">
    <div class="contentmenutitle">Navigation</div>
    <ul>
      <li><a href="index.php" class="home">home &there4;</a>
      <!-- <li><a href="demo.php" class="play">Applet</a> -->
      <li><a href="screenshot.php">Screenshots</a>
      <li><a href="video.php">Media</a>
      <li><a href="download.php" class="download">Download</a>
      <li><a href="faq.php" class="help">FAQ</a>            
      <li><a href="about.php">About</a>
    </ul>
  </div>
</div>
<div class="contentmenu">
  <div class="arrowlistmenu">
    <div class="contentmenutitle">Development</div>
    <ul>
      <li><a href="http://sourceforge.net/projects/ecmojo/" class="target" style="color: #505050; font-weight: normal;">Source Forge</a>
      <li><a href="https://sourceforge.net/projects/ecmojo/forums/forum/874361" class="support" style="color: #505050; font-weight: normal;">Open Discussions</a>
      <li><a href="https://sourceforge.net/tracker/?func=browse&group_id=241657&atid=1116905" class="support" style="color: #505050; font-weight: normal;">Bug Tracker</a>
    </ul>
  </div>
</div>
<!-- V-MENU end -->       
           </td></tr></table>
        </td>
        <td width="730" valign="top" style="padding-left: 8px; padding-right: 8px; margin-bottom: 4px">
          <table width="714"><tr width="714"><td width="714">
        
<!-- SPOTLIGHT start -->
<div id="spotlight">
  <div style="width: 420px;">
    <br>
    <div style="font-size: 14px; border-bottom: 1px solid #b0b0b0">Spotlight</div>
    <div class="spotlight" style="margin-top: 5px">
      <a href="webstart/ECMOjo-Infant.jnlp" class="spotlight">&raquo; PLAY ONLINE</a>
      <a href="download.php" class="spotlight">&raquo; DOWNLOAD</a>
    </div>
    <br>
    Please note ECMOjo is an <strong>Open Source</strong> project. <a href="about.php">Contact us</a> to 
    participate in the effort.
  </div>
</div>
<!-- SPOTLIGHT end -->
      
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

<!-- BODY start -->
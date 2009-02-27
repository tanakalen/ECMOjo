<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/download_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Download";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Download</h2></div></div></div>
<p>You can either run ECMOjo via online demo or download the installer.  The source code is also provided for download.</p>
<br>
<strong>Online Demo</strong><br>
<p>To run ECMOjo, you need at least <a href="http://www.java.com/getjava/" target="_blank">Java 1.5 or higher</a> installed on your system.<br>
<p>
  <ul>
    <li><a href="webstart/ECMOjo.jnlp">Online Demo</a> (Java Webstart):<br>&nbsp;&nbsp;&nbsp; 
    <i>Run ECMOjo directly with a single click from the link.</i></li>
    <li><a href="demo.php">Demo page</a> (Applet):<br>&nbsp;&nbsp;&nbsp; 
    <i>Run ECMOjo inside a browser window.</i></li>
  </ul>
</p>
<br>
<strong>Binary Files</strong><br>
<p>Download the platform dependant version for your operating system. </p>
<ul>
  <li><a name="windows" style="color: rgb(0, 0, 0);">(Windows)</a>:<br>&nbsp;&nbsp;&nbsp; 
    <img src="image/OS/WINDOWS-ICON.png"><i>Windows Installer.</i></li>
  <li><a name="macintosh" style="color: rgb(0, 0, 0);">(Mac OS 10.4.2+)</a>:<br>&nbsp;&nbsp;&nbsp; 
    <img src="image/OS/MACINTOSH-ICON.png"><i>Mac OS X Installer.</i></li>
  <li><a name="linux" style="color: rgb(0, 0, 0);"></a><a name="unix" style="color: rgb(0, 0, 0);">(Linux/Unix)</a>:<br>&nbsp;&nbsp;&nbsp;
    <img src="image/OS/LINUX-ICON.png"><i>Linux/Unix Installer.</i></li>
 </ul>
<br>
<strong>Source Code</strong><br>
<p>Download the source code via <a href="http://sourceforge.net/project/showfiles.php?group_id=241657" target="_blank">Sourceforge</a>.</p>    
<br>
<?php
  include($root.'include/end.inc.'.$phpEx);
?>
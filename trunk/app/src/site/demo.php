<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/demo_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Applet";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Applet</h1>

<p>To run ECMOjo Applet, you need at least <a href="http://www.java.com/getjava/" target="_blank">Java 1.5 or higher</a> installed on your system.</p>
<img src="applet-placeholder.gif" width="700" style="border: 1px solid #b0b0b0; cursor: pointer;" onClick="window.open('applet.php','appletwindow','width=800,height=600');"><br>
<br>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>
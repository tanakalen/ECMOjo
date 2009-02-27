<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/demo_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Download";
  include($root.'include/begin.inc.'.$phpEx);
?>
<div class="titlebar"><div><div><h2>Demo</h2></div></div></div>
<p>To run ECMOjo Applet, you need at least <a href="http://www.java.com/getjava/" target="_blank">Java 1.5 or higher</a> installed on your system.</p>
 <center>
    <div id="applet_location" style="width: 800px; border: 1px solid #000000;"><img id="applet_placeholder" src="applet-placeholder.gif" style="cursor: pointer;"></div>
    <script type="text/javascript">
      $("#applet_placeholder").click(function() {
        $("#applet_placeholder").attr("src", "applet-loading.gif");
        $("#applet_location").load("applet.inc");
      });
    </script>
  </center>
<?php
  include($root.'include/end.inc.'.$phpEx);
?>
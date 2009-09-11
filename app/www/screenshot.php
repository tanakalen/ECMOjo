<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/screenshot_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Screenshot";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Screenshots</h1>

<div align="center">
  <table>
    <tr>
      <td style="padding: 8px">&nbsp;<strong>Tutorial Menu</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot001-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot001.jpg','newwindow1','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot001.jpg','newwindow1','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Scenario Menu</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot002-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot002.jpg','newwindow2','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot002.jpg','newwindow2','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Silicon Oxigenator</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot003-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot003.jpg','newwindow3','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot003.jpg','newwindow3','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>PMP Oxigenator</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot004-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot004.jpg','newwindow4','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot004.jpg','newwindow4','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Pump Panel</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot005-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot005.jpg','newwindow5','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot005.jpg','newwindow5','width=880,height=680');"></a></div></td>
    </tr>
    <tr>
      <td style="padding: 8px">&nbsp;<strong>Intervention Panel</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot006-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot006.jpg','newwindow6','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot006.jpg','newwindow6','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Warning Dialog</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot007-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot007.jpg','newwindow7','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot007.jpg','newwindow7','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Lab Panel</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot008-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot008.jpg','newwindow8','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot008.jpg','newwindow8','width=880,height=680');"></a></div></td>
      <td style="padding: 8px">&nbsp;<strong>Patient Panel</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(January 14, 2009) <div class="thumbnail"><a href="javascript:;"><img src="image/screenshots/screenshot009-127x100.jpg" width="127" height="100" class="main" onClick="window.open('image/screenshots/screenshot008.jpg','newwindow8','width=880,height=680');" /><img class="frame" src="image/interface/frame.gif" onClick="window.open('image/screenshots/screenshot009.jpg','newwindow8','width=880,height=680');"></a></div></td>
      <td>&nbsp;</td>
    </tr>
  </table>
</div>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>
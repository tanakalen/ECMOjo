<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/video_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Screenshot";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Videos</h2></div></div></div>
<div align="center">
  <table class="screenshot" cellspacing="13" cellpadding="13">
    <tr>
      <td>&nbsp;<strong>ECMOjo Tutorial 1</strong><br>&nbsp;Version 1.0.0:<br>&nbsp;(May 1, 2009)<br>
        &nbsp;<object width="425" height="344">
        <param name="movie" value="http://www.youtube.com/v/J7FDPmfSEtU&hl=en&fs=1">
        </param><param name="allowFullScreen" value="true">
        </param><param name="allowscriptaccess" value="always">
        </param><embed src=http://www.youtube.com/v/J7FDPmfSEtU&hl=en&fs=1
        type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344">
        </embed></object> 
      </td>
      <td width="425" height="344">&nbsp;</td>
    </tr>

  </table>
</div>
<?php
  include($root.'include/end.inc.'.$phpEx);
?>
<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/video_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Video and Media";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Videos</h1>

<table>
  <tr>
    <td width="277" style="padding-right: 5px">
      <strong>ECMOjo Tutorial 1</strong> - Version 1.0.0 (May 1, 2009)<br>
      <br>
      This video is also available on <a href="http://www.youtube.com/watch?v=J7FDPmfSEtU">YouTube</a>. Please rate and comment. Thanks.
    </td>
    <td width="425">
			<object width="425" height="344">
				<param name="movie" value="http://www.youtube.com/v/J7FDPmfSEtU&hl=en&fs=1"></param>
				<param name="allowFullScreen" value="true"></param>
				<param name="allowscriptaccess" value="always"></param>
				<embed src=http://www.youtube.com/v/J7FDPmfSEtU&hl=en&fs=1 type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed>
			</object> 
    </td>
  </tr>
</table>
<div class="hr"/>
<br>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>
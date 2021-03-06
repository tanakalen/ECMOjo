<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/download_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Download";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Download</h1>
<p>
  You can either run ECMOjo online via a Java Applet or Webstart. You can also download the ECMOjo installer.  
  The source code is provided for download via SourceForge.net.
</p>
<br>

<h2>Online Demo</h2>
<p>To run ECMOjo, you need at least <a href="http://www.java.com/getjava/" target="_blank">Java 1.5 or higher</a> installed on your system.<br>
<p>
  <ul>
    <li><a href="webstart/ECMOjo-Infant.jnlp">Online Demo</a> (Java Webstart): <i>Run ECMOjo directly with a single click from the link.</i></li>
	<!-- <li><a href="demo.php">Applet Page</a> (Applet): <i>Run ECMOjo inside a browser window.</i></li> -->
  </ul>
</p>
<br>

<h2>Binary Files</h2>
<p>Download the platform dependant version for your operating system. </p>
<ul>
  <li>
    <a name="windows" href="https://sourceforge.net/projects/ecmojo/files/">
      <img src="image/OS/WINDOWS-ICON.png"> Windows (XP, Vista, Win 7, Win 8)
    </a>
  </li>
  <li>
    <a name="macintosh" href="https://sourceforge.net/projects/ecmojo/files/">
      <img src="image/OS/MACINTOSH-ICON.png"> Mac OS X Disk Image (10.4.2+)
    </a>
  </li>
  <li>
    <a name="linux" href="https://sourceforge.net/projects/ecmojo/files/">
      <img src="image/OS/LINUX-ICON.png"> Soure/Linux/UNIX Distribution
    </a>
  </li>
</ul>
<br>

<h2>Source Code</h2>
<p>Download the source code via <a href="http://sourceforge.net/project/showfiles.php?group_id=241657" target="_blank">SourceForge.net</a>.</p>    
<br>

<h2>Support &amp; Development</h2>
<p>Please visit our discussion forum and bug tracker for support and development.</p>
<ul>
  <li><a href="https://sourceforge.net/projects/ecmojo/forums/forum/874361">Open Discussions</a>
  <li><a href="https://sourceforge.net/tracker/?func=browse&group_id=241657&atid=1116905">Bug Tracker</a>
</ul>
<br>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>
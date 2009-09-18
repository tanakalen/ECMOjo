<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/index_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Home";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Information</h1>
<p>
  <img src="image/interface/jostracart.png" style="float: right;">
  ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation. <strong>ECMOjo is a computer
  application</strong> implemented in Java. ECMOjo is available for all major operating systems including <b>Windows</b>, <b>Macintosh</b>,
  <b>Linux</b> and <b>UNIX</b>. The application consists of a graphical user interface to teach physicians, nurses and students
  the basics of extracorporeal membrane oxygenation.
</p>
<p>
	In intensive care medicine, <b>extracorporeal membrane oxygenation (ECMO)</b> is an extracorporeal technique of providing both cardiac and
	respiratory support oxygen to patients whose heart and lungs are so severely diseased or damaged that they can no longer serve their
	function.  An ECMO machine is similar to a heart-lung machine. To initiate ECMO, cannulae are placed in large blood vessels to provide
	access to the patient's blood. Anticoagulant drugs are given to prevent blood clotting.  The ECMO machine continuously pumps blood
	from the patient through a "membrane oxygenator" that imitates the gas exchange process of the lungs. Oxygenated blood is then
	returned to the patient.
</p>
<p>
	ECMOjo has been developed by the Telehealth Research Institute (TRI), John A. Burns School of 
  Medicine (JABSOM) of the University of Hawaii. 
</p>
<br>

<h1>News & Updates</h1>
<ul>
  <li><strong>September 10, 2009</strong> - Updated the ECMOjo website.</li>
  <li><strong>August 31, 2009</strong> - Usability evaluation completed.</li>
  <li><strong>May 6, 2009</strong> - Added ECMOjo Tutorial 1 video.</li>
  <li><strong>January 14, 2009</strong> - The ECMOjo website opens its doors to the public!</li>
  <li><strong>October 6, 2008</strong> - Registered ECMOjo to Source Forge.</li>
</ul>
<br>
<br>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>


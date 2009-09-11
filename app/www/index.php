<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/index_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "ECMOjo";
  include($root.'include/begin.inc.'.$phpEx);
?>

<!-- SPOTLIGHT start -->
<div id="spotlight">
  <br>
  <div style="width: 420px; font-size: 14px; border-bottom: 1px solid #b0b0b0">Spotlight</div>
  <div class="spotlight" style="margin-top: 5px">
    <a href="webstart/ECMOjo.jnlp" class="spotlight">&raquo; PLAY ONLINE</a>
    <a href="download.html" class="spotlight">&raquo; DOWNLOAD</a>
  </div>
</div>
<!-- SPOTLIGHT end -->


<h1>Information</h1>
<p>
  <img src="image/interface/jostracart.png" style="float: right;">
	In intensive care medicine, extracorporeal membrane oxygenation (ECMO) is an extracorporeal technique of providing both cardiac and
	respiratory support oxygen to patients whose heart and lungs are so severely diseased or damaged that they can no longer serve their
	function.  An ECMO machine is similar to a heart-lung machine.  To initiate ECMO, cannulae are placed in large blood vessels to provide
	access to the patient's blood. Anticoagulant drugs are given to prevent blood clotting.  The ECMO machine continuously pumps blood
	from the patient through a "membrane oxygenator" that imitates the gas exchange process of the lungs. Oxygenated blood is then
	returned to the patient.
</p>
<p>
	ECMOjo contains the word ECMO and mojo (magic charm). <strong>ECMOjo is a simulator and trainer for ECMO</strong>.  ECMOjo is an
	application running in a computer.  ECMOjo helps the physician and nurse to learn how ECMO machine and progress work easily by practicing
	different scenarios. It consists of a graphical user interface to allow interaction and train ECMO practitioners.  ECMOjo is using Java
	programming to develop as application.  The user can easily run the application with different operating systems.
</p>
<p>
	ECMOjo was developed by Telehealth Research Institute (TRI), University of Hawaii because the regular learning ECMO
	course requires a lot time and money to learn ECMO by taking the courses and owned the ECMO machine.  The course requires a lot reading in
	order to learn how ECMO works, so the learning curve and time will be slow.  Since many physicians and nurses like to learn about ECMO, the
	regular ECMO courses will not be able to fit the demand for large scale of people with a short period of time. ECMOjo helps to solve the
	problem by using just a computer running the application and learn how ECMO works by practicing different scenarios in a short
	period of time.
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

<?php
  include($root.'include/end.inc.'.$phpEx);
?>


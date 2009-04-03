<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/index_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "ECMOjo";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Information</h2></div></div></div>
<div class="gameinfo">
<table>
  <tr>
    <td valign="top">
    <p>
    In intensive care medicine, extracorporeal membrane oxygenation (ECMO) is an extracorporeal technique of providing both cardiac and
    respiratory support oxygen to patients whose heart and lungs are so severely diseased or damaged that they can no longer serve their
    function.  An ECMO machine is similar to a heart-lung machine.  To initiate ECMO, cannulae are placed in large blood vessels to provide
    access to the patient's blood. Anticoagulant drugs are given to prevent blood clotting.  The ECMO machine continuously pumps blood
    from the patient through a "membrane oxygenator" that imitates the gas exchange process of the lungs. Oxygenated blood is then
    returned to the patient.
    </p>
    <p>
    ECMOjo contains the word ECMO and mojo (magic charm).  ECMOjo is a simulator and trainer for ECMO.  ECMOjo is an
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
    period of time. <br><br>
    Please check out our <a href="webstart/ECMOjo.jnlp">Online Demo</a> or <a href="demo.php">Demo page</a>
    </p></td>
  <td width="10" valign="top"></td>
  <td width="200" valign="top">
    <table class="indexscreenshot" cellspacing="5" cellpadding="5">
      <tr>
        <td><div class="indexthumbnail1"><img src="image/interface/jostracart.jpg" alt="Jostra Cart" width="200" height="210" class="main" />
        <img class="frame" src="image/interface/indexframe.gif"></div></td>
      </tr>
      <tr>
        <td><div class="indexthumbnail2"><img src="image/interface/ecmojo.jpg" alt="ECMOjo" width="200" height="150" class="main" />
        <img class="frame" src="image/interface/indexframe.gif"></div></td>
      </tr>
    </table>
  </td>
  </tr>
</table>
</div>
<div class="titlebar"><div><div><h2>News & Updates</h2></div></div></div>
<div class="othertopic">
  <ul>
    <li><strong>January 14, 2009</strong><br>The ECMOjo website opens its doors to the public!</li>
    <li><strong>October 6, 2008</strong><br>Registered ECMOjo to Source Forge.</li>
  </ul>
</div>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>


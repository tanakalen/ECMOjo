<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/faq_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Frequently Asked Questions (FAQ)";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>Frequently Asked Questions</h1>
<p>
  <strong>What is ECMOjo/ECMO?</strong><br>
  ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation (ECMO).  It consists of a graphical user
  interface to allow interaction and train ECMO practitioners, and has been developed by Telehealth Research Institute (TRI), University of Hawaii.
  ECMOjo contains the word ECMO and mojo (magic charm).  In intensive care medicine, extracorporeal membrane oxygenation (ECMO) is an 
  extracorporeal technique of providing both cardiac and respiratory support oxygen to patients whose heart and lungs are so severely 
  diseased or damaged that they can no longer serve their function.</p>
</p>
<p>
  <strong>Who owns ECMOjo?</strong><br>
  ECMOjo is open source software hosted on source forge and has been developed by Telehealth Research Institute, University of Hawaii.
</p>
<p>
  <strong>Is ECMOjo free?</strong><br>
  Yes, ECMOjo is open source software. You are free to use this program under the terms of the Berkeley Software Distribution (BSD) License.
</p>
<p>
  <strong>Does ECMOjo contain any spyware or adware?</strong><br>
  No, ECMOjo does not include any spyware or adware.
</p>
<p>
  <strong>What is Java?</strong><br>
  Java allows you to play online games, chat with people around the world, view images in 3D, and run the simulator
  just to name a few. It's also integral to the intranet applications and other e-business solutions that are 
  the foundation of corporate computing.
</p>
<p>
  <strong>What is Java Webstart?</strong><br>
  Java Web Start is a framework developed by Sun Microsystems which allows application &amp; games for the Java Platform 
  to be started directly from a web browser.
</p>
<p>
  <strong>What is an Applet?</strong><br>
  An Applet is a Java program that can be run inside a browser window.
</p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

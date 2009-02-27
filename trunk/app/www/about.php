<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/about_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "About Us";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="aboutus">

<div class="titlebar"><div><div><h2>About Us</h2></div></div></div>
<p><b>ECMOjo</b> is a simulator and trainer for extracorporeal membrane oxygenation (ECMO).  It consists of a graphical user
 interface to allow interaction and train ECMO practitioners, and has been developed by Telehealth Research Institute (TRI), University of Hawaii.
 ECMOjo contains the word ECMO and mojo (magic charm).  In intensive care medicine, extracorporeal membrane oxygenation (ECMO) is an 
 extracorporeal technique of providing both cardiac and respiratory support oxygen to patients whose heart and lungs are so severely 
 diseased or damaged that they can no longer serve their function.</p>
<br>
<div class="titlebar"><div><div><h2>Credits</h2></div></div></div>
<p>
  <strong>Project Manager</strong>: <br>
  &nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:Mark.T.Ogino@kp.org'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Mark T. Ogino, MD</a><br><br>
  <strong>Lead Developer</strong>: <br>
  &nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:caschwan@hawaii.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Christoph Aschwanden, PhD</a><br><br>
  <strong>Developer</strong>: <br>
  &nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:kinwang@hawaii.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Kin Lik Wang (Alex)</a><br><br>
  <strong>Artist</strong>: <br>
  &nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:lee@imua.com'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Kaleiohu Lee</a><br><br>
 	<strong>Advisors</strong>: <br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:lburgess@hawaii.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Lawrence P. Burgess, MD</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:kristen.costales@kapiolani.org'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Kristen Costales, CCP</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:fiedml@ccm.upmc.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Melinda Hamilton, MD</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:kent.kelly@chp.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Kent Kelly, CCP</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:melodyk@kapiolani.org'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Melody Kilcommons, RNC</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:lutzw@upmc.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>John Lutz</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:donald.mccurnin@ushouthwestern.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Donald McCurnin, MD</a><br>
 	&nbsp;&nbsp;&nbsp;<script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:len.tanaka@kapiolani.org'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Len Tanaka, MD</a><br>
</p>
<br>
<div class="titlebar"><div><div><h2>Contact Information</h2></div></div></div>
<p>
<strong>Telehealth Research Institute</strong><br>
Medical Education Building 212<br>
John A. Burns School of Medicine<br>
University of Hawaii<br>
651 Ilalo Street<br>
Honolulu, HI 96813-5534<br>
<br>
Phone: 808.692.1080<br>
Fax: 808.692.1250<br>
Email: <script type="text/javascript">
         document.write("<n uers=\"<?php echo str_rot13('mailto:telemed@hawaii.edu'); ?>\">".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
       </script>Telehealth Research Institute</a><br>
Website: <a href="http://www.tri.jabsom.hawaii.edu">http://www.tri.jabsom.hawaii.edu</a>
</p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

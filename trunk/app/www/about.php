<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/about_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "About Us";
  include($root.'include/begin.inc.'.$phpEx);
?>

<h1>About Us</h1>
<img src="image/interface/minilogo.png" style="float: right;">
<p>
  ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation (ECMO). 
</p>
<p>
  &nbsp;&nbsp;&nbsp;<strong>ECMOjo = ECMO + mojo</strong>
</p>
<p>
  ECMOjo has been developed by the Telehealth Research Institute (TRI), John A. Burns School of 
  Medicine (JABSOM) of the University of Hawaii.
</p>
<br>

<h2>Contact Information</h2>
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
  Email:
  <script type="text/javascript">
    document.write("<n uers=\"<?php echo str_rot13('mailto:telemed@hawaii.edu'); ?>\"><?php echo str_rot13('telemed@hawaii.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
  </script><br>
  Website: <a href="http://www.tri.jabsom.hawaii.edu">http://www.tri.jabsom.hawaii.edu</a><br>
</p>
<br>

<h2>Credits</h2>
<div style="border: 1px solid #e0e0e0; background: #f7f7f7; padding: 4px;">
  <strong>Principal Investigator</strong>: <br>
  &nbsp;&nbsp;&nbsp;Lawrence P. Burgess, MD -
     <script type="text/javascript">
       document.write("<n uers=\"<?php echo str_rot13('mailto:lburgess@hawaii.edu'); ?>\"><?php echo str_rot13('lburgess@hawaii.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
     </script><br><br>

	<strong>Project Manager</strong>: <br>
	&nbsp;&nbsp;&nbsp;Mark T. Ogino, MD -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:Mark.T.Ogino@kp.org'); ?>\"><?php echo str_rot13('Mark.T.Ogino@kp.org'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br><br>
	     
	<strong>Lead Developer</strong>: <br>
	&nbsp;&nbsp;&nbsp;Christoph Aschwanden, PhD -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:caschwan@hawaii.edu'); ?>\"><?php echo str_rot13('caschwan@hawaii.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br><br>
	     
	<strong>Developers</strong>: <br>
	&nbsp;&nbsp;&nbsp;Kin Lik Wang (Alex) -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:kinwang@hawaii.edu'); ?>\"><?php echo str_rot13('kinwang@hawaii.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
	&nbsp;&nbsp;&nbsp;Len Tanaka, MD -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:len.tanaka@kapiolani.org'); ?>\"><?php echo str_rot13('len.tanaka@kapiolani.org'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br><br>
	
	<strong>Artist</strong>: <br>
	&nbsp;&nbsp;&nbsp;Kaleiohu Lee -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:lee@imua.com'); ?>\"><?php echo str_rot13('lee@imua.com'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br><br>
	     
  <strong>Godfather</strong>: <br>
	 &nbsp;&nbsp;&nbsp;Donald McCurnin, MD -
     <script type="text/javascript">
       document.write("<n uers=\"<?php echo str_rot13('mailto:donald.mccurnin@ushouthwestern.edu'); ?>\"><?php echo str_rot13('donald.mccurnin@ushouthwestern.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
     </script><br><br>
     
	<strong>Advisors</strong>: <br>
	&nbsp;&nbsp;&nbsp;Kristen Costales, CCP -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:kristen.costales@kapiolani.org'); ?>\"><?php echo str_rot13('kristen.costales@kapiolani.org'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
	&nbsp;&nbsp;&nbsp;Melinda Hamilton, MD -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:fiedml@ccm.upmc.edu'); ?>\"><?php echo str_rot13('fiedml@ccm.upmc.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
	&nbsp;&nbsp;&nbsp;Kent Kelly, CCP -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:kent.kelly@chp.edu'); ?>\"><?php echo str_rot13('kent.kelly@chp.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
	&nbsp;&nbsp;&nbsp;Melody Kilcommons, RNC -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:melodyk@kapiolani.org'); ?>\"><?php echo str_rot13('melodyk@kapiolani.org'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
	&nbsp;&nbsp;&nbsp;John Lutz -
	   <script type="text/javascript">
	     document.write("<n uers=\"<?php echo str_rot13('mailto:lutzw@upmc.edu'); ?>\"><?php echo str_rot13('lutzw@upmc.edu'); ?></n>".replace(/[a-zA-Z]/g, function(c){return String.fromCharCode((c<="Z"?90:122)>=(c=c.charCodeAt(0)+13)?c:c-26);}));
	   </script><br>
</div>
<p class="footnote">
  (*) Please contact us if we forgot to add your name. Our apologies.
</p>
<br>

<h2>Acknowledgment</h2>
<p>
  This project has been supported by grant No. W81XWH-06-2-0061 awarded by Department of Defense (DoD), 
  United States of America.
</p>
<br>

<h2>Affiliations</h2>
<ul>
  <li>Hanuola - <a href="http://www.hanuola.com">http://www.hanuola.com</a></li>
  <li>Telehealth Research Institute - <a href="http://www.tri.jabsom.hawaii.edu">http://www.tri.jabsom.hawaii.edu</a></li>
  <li>SimTiki Simulation Center - <a href="http://simtiki.org">http://simtiki.org</a></li>
  <li>シムチキ日本 - <a href="http://www.simtikinihon.org">http://www.simtikinihon.org</a></li>
  <li>John A. Burns School of Medicine - <a href="http://www.jabsom.hawaii.edu">http://www.jabsom.hawaii.edu</a></li>
</ul>
<p class="footnote">
  (*) Please contact us if you would like to have your URL added.
</p>
<br>

<h2>Statistics</h2>
<table class="data">
  <thead>
	  <tr>
	    <th>Stat</th>
	    <th>Count</th>
	  </tr>
  </thead>
  <tbody>
    <tr>
      <td>MDs</td>
      <td>5</td>
    </tr>
    <tr>
      <td>PhDs</td>
      <td>1</td>
    </tr>
    <tr>
      <td>CCPs</td>
      <td>2</td>
    </tr>
    <tr>
      <td>RNCs</td>
      <td>1</td>
    </tr>
    <tr>
      <td>Other</td>
      <td>3</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td colspan="2">including 2 pigs and counting...</td>
    </tr>
  </tfoot>
</table>
<br>
<br>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

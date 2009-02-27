<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/retrieve_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Retrieve Login Information";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Retrieve Login Information</h2></div></div></div>
<p>Did you forget your password? Enter your account's email address to retrieve your login information.</p>
<form action="<?php echo htmlspecialchars($PHP_SELF) ?>" method="POST" name="retrieve_form">
  <input type="hidden" name="cmd" value="retrieve">
  <table>
    <tr>
      <td><p>Email Address<span class="required">*</span></p></td>
      <td><input type="text" name="email" size="35"></td>
    </tr><tr>
      <td>&nbsp;</td>
      <td><button type="submit">Request</button></td>
    </tr>
  </table>
</form>

<div class="hr">&nbsp;</div>

<p class="subtext">
  Please setup a new account if you forgot your email address. Or visit <a href="about.php">About</a> for contact information.
</p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

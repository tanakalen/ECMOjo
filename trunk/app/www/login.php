<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/login_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Login";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Login</h2></div></div></div>
<p>Enter the username and password</p>
<form action="<?php echo htmlspecialchars($PHP_SELF) ?>" method="POST" name="login_form" onsubmit="return saveme();">
  <input type="hidden" name="cmd" value="login">
  <input type="hidden" name="continue" value="<?php echo $controller->continue ?>">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td style="text-align: left;"><p>Username &nbsp;</p></td>
      <td style="text-align: left;"><input type="text" name="username" value="<?php echo $controller->user->username; ?>"></td>
    </tr>
    <tr>
      <td style="text-align: left;"><p>Password &nbsp;</p></td>
      <td style="text-align: left;"><input type="password" name="password" value="<?php echo $controller->user->password; ?>"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><p><button type="submit">Login</button></p></td>
    </tr>
  </table>
</form>

<div class="hr">&nbsp;</div>

<p>
  Not registered yet?Did you forget your password?
  <ul>
    <li><a href="register.php">Register</a>
    <li><a href="retrieve.php">Password Retrieval</a>
  </ul>
</p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

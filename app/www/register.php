<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/register_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "User Registration";
  include($root.'include/begin.inc.'.$phpEx);
?>

<div class="titlebar"><div><div><h2>Register</h2></div></div></div>
<p>Please register your username and password.</p>
<form action="<?php echo htmlspecialchars($PHP_SELF) ?>" method="POST" name="add_form">
  <input type="hidden" name="cmd" value="register">
  <table>
    <tr>
      <td><p>Username <span class="required">*</span></p></td>
      <td>
        <input type="text" name="username" size="25" value="<?php echo $controller->user->username; ?>"><br>
        <div class="smallscript">Letters and numbers only.<br><br></div>
      </td>
    </tr><tr>
      <td><p>Password <span class="required">*</span></p></td>
      <td>
        <input type="password" name="password" size="25" value="<?php echo $controller->user->password; ?>">
        <div class="smallscript">Careful: the password is case-sensitive!<br><br></div>
      </td>
    </tr><tr>
      <td><p>First Name <span class="required">*</span></p></td>
      <td><input type="text" name="first_name" size="35" value="<?php echo $controller->user->first_name; ?>"></td>
    </tr><tr>
      <td><p>Last Name <span class="required">*</span></p></td>
      <td><input type="text" name="last_name" size="35" value="<?php echo $controller->user->last_name; ?>"></td>
    </tr><tr>
      <td><p>Email <span class="required">*</span></p></td>
      <td><input type="text" name="email" size="45" value="<?php echo $controller->user->email; ?>"></td>
    </tr><tr>
      <td>&nbsp;</td>
      <td><button type="submit">Register</button></td>
    </tr>
  </table>
</form>

<div class="hr">&nbsp;</div>

<p class="subtext">
  Please note that we will treat your email address confidential. Your email address will <u>not</u> been given out to third parties.
</p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

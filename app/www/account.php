<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/account_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Account Information";
  include($root.'include/begin.inc.'.$phpEx);
?>
<div class="titlebar"><div><div><h2>My Account</h2></div></div></div>
<form action="<?php echo htmlspecialchars($PHP_SELF) ?>" method="POST" name="update_form">
  <input type="hidden" name="cmd" value="update">
  <table>
    <tr>
      <td><p>Username </p></td>
      <td><p>&nbsp;&nbsp;<strong><?php echo htmlspecialchars($controller->user->username); ?></strong></p></td>
    </tr><tr>
      <td><p>Password <span class="required">*</span></p></td>
      <td><input type="password" name="password" size="25" value="<?php echo $controller->user->password; ?>"></td>
    </tr><tr>
      <td><p>First Name<span class="required">*</span></p></td>
      <td><input type="text" name="first_name" size="35" value="<?php echo $controller->user->first_name; ?>"></td>
    </tr><tr>
      <td><p>Last Name <span class="required">*</span></p></td>
      <td><input type="text" name="last_name" size="35" value="<?php echo $controller->user->last_name; ?>"></td>
    </tr><tr>
      <td><p>Email<span class="required">*</span></p></td>
      <td><input type="text" name="email" size="45" value="<?php echo $controller->user->email; ?>"></td>
    </tr><tr>
      <td>&nbsp;</td>
      <td><button type="submit">Update</button></td>
    </tr>
  </table>
</form>
<?php
  include($root.'include/end.inc.'.$phpEx);
?>

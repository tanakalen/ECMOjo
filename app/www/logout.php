<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/logout_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "Logout";
  include($root.'include/begin.inc.'.$phpEx);
?>

shouldn't get here...

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

<?php
  require_once('root.inc');
  require_once($root.'lib/extension.inc');
  require_once('control/noaccess_controller.inc.'.$phpEx);
  $controller =& new PageController();

  $pageTitle = "No Access";
  include($root.'include/begin.inc.'.$phpEx);
?>

<p class="alert">
  Illegal Access!
</p>

<div class="titlebar"><div><div><h2><?php print msg('#NoAccessPageHeader'); ?></h2></div></div></div>
<p><?php print msg('#NoAccessPageIntro'); ?></p>

<?php
  include($root.'include/end.inc.'.$phpEx);
?>

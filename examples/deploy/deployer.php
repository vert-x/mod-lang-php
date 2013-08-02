<?php
$config = array(
  'name' => 'tim',
  'age' => 823823,
);

Container::deployVerticle('deploy/child.php', $config);

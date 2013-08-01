<?php
$config = array(
  'name' => 'tim',
  'age' => 823823,
);

Container::deployVertcle('deploy/child.php', $config);

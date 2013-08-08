<?php
$config = array(
  'name' => 'tim',
  'age' => 823823,
);

Vertx::deployVerticle('deploy/child.php', $config);

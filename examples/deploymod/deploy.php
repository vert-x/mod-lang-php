<?php

$log = Vert::logger();
$log->info('Deploying module');

$config = array('some-var' => 'Hello world!');

Vertx::deployModule('org.foo~bar-mod~1.0.0', $config, 1, function($deployId, $error) use ($log) {
  if (empty($error)) {
    $log->info('Deployment ID is '. $deployId);
  }
  else {
    $log->info('Failed to deploy module.');
  }
});

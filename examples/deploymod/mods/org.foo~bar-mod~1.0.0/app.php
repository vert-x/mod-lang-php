<?php
$log = Vertx::logger();
$log->info('In foo mod!');

$config = Vertx::config();
$log->info('some-var is '. $config['some-var']);

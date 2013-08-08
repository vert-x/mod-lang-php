<?php

$logger = Vertx::logger();
$logger->info('in child.php, config is '. var_dump(Vertx::config()));

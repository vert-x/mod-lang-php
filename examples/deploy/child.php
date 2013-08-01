<?php

$logger = Container::logger();
$logger->info('in child.php, config is '. var_dump(Container::config()));

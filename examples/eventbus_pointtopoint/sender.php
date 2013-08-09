<?php

$eventBus = Vertx::eventBus();
$logger = Vertx::logger();

Vertx::setPeriodic(1000, function() use ($eventBus, $logger) {
  $eventBus->send('ping-address', 'ping', function($reply) use ($logger) {
    $logger->info("Received reply ". $reply->body);
  });
});

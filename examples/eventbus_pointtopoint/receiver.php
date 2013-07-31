<?php

$logger = Vertx::logger();

Vertx::eventBus()->registerHandler('ping-address', function($message) use ($logger) {
  $logger->info("Received message ". $message->body());
  $message->reply('pong');
});

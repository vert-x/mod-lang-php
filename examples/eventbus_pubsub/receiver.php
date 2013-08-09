<?php

$logger = Vertx::logger();

Vertx::eventBus()->registerHandler('news-feed', function($message) use ($logger) {
  $logger->info('Received news '. $message->body);
});

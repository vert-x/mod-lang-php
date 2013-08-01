<?php

$logger = Vertx::logger();

Vertx::createHttpClient()->port(8080)->getNow('/', function($response) use ($logger) {
  $logger->info('Got response '. $response->statusCode());
  $response->bodyHandler(function ($body) use ($logger) {
    $logger->info('Got body '. $body);
  });
});

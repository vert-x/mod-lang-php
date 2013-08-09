<?php

$client = Vertx::createHttpClient();
$client->port = 4443;
$client->ssl = TRUE;

$client->getNow('/', function($response) {
  $log = Container::logger();
  $log->info('Got response '. $response->statusCode());
  $response->bodyHandler(function($body) use ($log) {
    $log->info('Got data '. $body);
  });
});

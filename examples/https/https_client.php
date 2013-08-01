<?php

$client = Vertx::createHttpClient()->port(4443)->ssl(TRUE)->trustAll(TRUE);

$client->getNow('/', function($response) {
  $log = Container::logger();
  $log->info('Got response '. $response->statusCode());
  $response->bodyHandler(function($body) use ($log) {
    $log->info('Got data '. $body);
  });
});

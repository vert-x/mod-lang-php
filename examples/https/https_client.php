<?php

$client = Vertx::createHttpClient()->setPort(4443)->setSSL(TRUE);

$client->getNow('/', function($response) {
  $log = Container::logger();
  $log->info('Got response '. $response->statusCode());
  $response->bodyHandler(function($body) use ($log) {
    $log->info('Got data '. $body);
  });
});

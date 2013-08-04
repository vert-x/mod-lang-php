<?php

$client = Vertx::createNetClient();
$logger = Vertx::logger();

$client->connectTimeout = 'invalid';

$client->connect(1234, NULL, function($socket, $error) use ($logger) {
  if (!$error) {
    $socket->dataHandler(function($buffer) use ($logger) {
      $logger->info("Client receiving ". $buffer);
    });

    for ($i = 0; $i < 10; $i++) {
      $message = "Hello $i.";
      $logger->info("Client sending ". $message);
      $socket->write($message);
    }
  }
  else {
    $logger->info("Failed to connect to the server. ". $error->getMessage());
  }
});

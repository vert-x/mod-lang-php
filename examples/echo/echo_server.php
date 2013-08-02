<?php

use Vertx\Streams\Pump;

Vertx::createNetServer()->connectHandler(function($socket) {
  $pump = new Vertx\Streams\Pump($socket, $socket);
  $pump->start();
})->listen(1234);

<?php

use Vertx\Pump;

Vertx::createNetServer()->connectHandler(function($socket) {
  $pump = new Pump($socket, $socket);
  $pump->start();
})->listen(1234);

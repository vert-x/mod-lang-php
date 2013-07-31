<?php

$eventBus = Vertx::eventBus();

Vertx::setPeriodic(1000, function() use ($eventBus) {
  $eventBus->publish('news-feed', 'Some news!');
});

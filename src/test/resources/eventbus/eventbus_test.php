<?php

function test_send() {
  $eventBus = Vertx::eventBus();
  $eventBus->send('test.address', 'Hello world!');
}

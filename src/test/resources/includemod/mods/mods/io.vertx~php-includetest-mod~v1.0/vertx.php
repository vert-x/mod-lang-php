<?php
require_vertx("hello-lib.php");

$eventBus = Vertx::eventBus();

Vertx::logger()->info(sayHello("User"));

$eventBus->registerHandler('test.hello', function($message) {
    $message->reply(sayHello("Test"));
});

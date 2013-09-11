<?php
require_vertx("hello-lib.php");

$eventBus = Vertx::eventBus();

Vertx::logger()->info(sayHello("User"));

/**
 * Test if the require_vertx really loads php files of other module ressources
 * if they are included
 */
$eventBus->registerHandler('test.require_vertx', function($message) {
    $message->reply(sayHello("Test"));
});

/**
 * test if the Autoclassloader is using the Vertx ClassLoader
 */
$eventBus->registerHandler('test.autoload', function($message) {
    $helloObject = new lib\HelloClass("Test");
    $message->reply($helloObject->sayHello());
});

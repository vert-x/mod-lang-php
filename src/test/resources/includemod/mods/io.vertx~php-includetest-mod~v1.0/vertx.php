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
 * test if the Autoclassloader is using the Vertx Classloader to load
 * internal classes
 */
$eventBus->registerHandler('test.autoload.internal', function($message) {
    $helloObject = new InternalHelloClass("Test");
    $message->reply($helloObject->sayHello());
});

/**
 * test if the Autoclassloader is using the Vertx ClassLoader, so it can find
 * included module classes
 */
$eventBus->registerHandler('test.autoload.external', function($message) {
    $helloObject = new lib\ExternalHelloClass("Test");
    $message->reply($helloObject->sayHello());
});

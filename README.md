Vert.x PHP
==========

This project provides PHP language support for [Vert.x](http://vertx.io/).
Since Vert.x runs on the JVM, this module is built on the Java-based
[Quercus](http://quercus.caucho.com/) PHP implementation, one which is
kept up to date and supports many useful new PHP features such as closures.
The Vert.x PHP API closely resembles that of the Javascript module, using
shorter method names and closures for event handlers. The PHP Vert.x API
is simply a wrapper around the core Vert.x Java API and is similarly
implemented entirely through the Quercus Java API so at to allow more
control over interacting with the core Vert.x API.

### The core API
The core Vert.x API is always accessible through the top level `Vertx`
static class. It exposes methods for creating TCP and HTTP servers,
accessing the event bus, creating event timers, and more.

```php
$eventBus = Vertx::eventBus();
```

### The container API
Similar to the core `Vertx` class implementation, the container API is
a top-level static class called `Container` which can be used to deploy
or undeploy verticles and modules and allows configuration and logging
facilities to be accessed.

```php
$log = Container::logger();
$log->info('Hello world!');
```

### The Event Bus API
The event bus is the nervous system of Vert.x.

It allows verticles to communicate with each other irrespective of
what language they are written in, and whether they're in the same
Vert.x instance, or in a different Vert.x instance. For more information
on the Vert.x event bus see the [project documentation](http://vertx.io/docs.html).

#### Registering and unregistering handlers
Vert.x PHP handlers are implemented using PHP's closures. Closures are
ideal for event-driven frameworks as they reduce clutter.

`EventBus::registerHandler($address, $handler, $resultHandler = NULL)`

```php
$eventBus = Vertx::eventBus();
$log = Container::logger();

$handler = function($message) use ($log) {
  $log->info('I received a message!');
}
$eventBus->registerHandler('test.address', $handler);
```

Sometime it takes a while for a new handler to propigate around the
cluster. You can optionally register a second handler which will be
called when the handler has been propigated.

```
$eventBus->registerHandler('test.address', $handler, function() use ($log) {
  $log->info("Yay! I've been propigated around the cluster!");
});
```

To unregister a handler, simply call the `unregisterHandler` method.

`EventBus::unregisterHandler($address, $handler)`

```php
$eventBus->unregisterHandler('test.address', $handler);
```

#### Publishing messages

```php
$eventBus->publish('test.address', 'Hello world!');
```

#### Sending messages

```php
$eventBus->send('test.address', 'Hello world!');
```

#### Replying to messages

```php
$log = Container::logger();
$eventBus->registerHandler('test.address', function($message) use ($log) {
  $log->info('I got a message!');
  if ($message->body() == 'Hello world!') {
    $log->info('Replying...');
    $message->reply('Hello world yourself!');
  }
});
```

We can then wait for a reply on the sending side.

```php
$log = Container::logger();
$eventBus->send('test.address', 'Hello world!', function($reply) use ($log) {
  $log->info('I got a reply!');
});
```

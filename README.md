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

## Table of contents
--------------------
1. [The Vert.x API](#the-core-api)
1. [The Container API](#the-container-api)
   * [Accessing verticle configuration](#configuration)
   * [Logging from a verticle](#logging)
   * [Deploying and undeploying verticles programmatically](#deploying-and-undeploying-verticles-programmatically)
1. [The Event Bus](#the-event-bus-api)
   * [Registering and unregistering handlers](#registering-and-unregistering-handlers)
   * [Publishing messages](#publishing-messages)
   * [Sending messages](#sending-messages)
   * [Replying to messages](#replying-to-messages)
1. [Shared Data](#shared-data)
   * [Shared Maps](#shared-maps)
   * [Shared Sets](#shared-sets)
1. [Buffers](#buffers)
   * [Creating buffers](#creating-buffers)
   * [Writing to a buffer](#writing-to-a-buffer)
1. [Delayed and Periodic Tasks](#delayed-and-periodic-tasks)
   * [One-shot timers](#one-shot-timers)
   * [Periodic timers](#periodic-timers)
   * [Cancelling timers](#cancelling-timers)
1. [Writing TCP Servers and Clients](#writing-tcp-servers-and-clients)
   * [Creating a Net Server](#creating-a-netserver)
   * [Starting a Net Server](#starting-a-netserver)
   * [Getting notified of incoming connections](#getting-notified-of-incoming-connections)
   * [Closing a Net Server](#closing-a-netserver)
   * [NetServer proprties](#netserver-properties)
   * [Reading data from the socket](#reading-data-from-the-socket)
   * [Writing data to the socket](#writing-data-to-the-socket)
   * [Socket remote address](#socket-remote-address)
   * [Closing a socket](#closing-a-socket)
   * [Close handler](#close-handler)
   * [Exception handler](#exception-handler)
   * [Event bus write handler](#event-bus-write-handler)
   * [Creating a Net Client](#creating-a-net-client)
   * [Making a connection](#making-a-connection)
   * [Configuring reconnection](#configuring-reconnection)
   * [Net Client properties](#net-client-properties)
   * [SSL Servers](#ssl-servers)
   * [SSL Clients](#ssl-clients)

## The core API
The core Vert.x API is always accessible through the top level `Vertx`
static class. It exposes methods for creating TCP and HTTP servers,
accessing the event bus, creating event timers, and more.

```php
$eventBus = Vertx::eventBus();
```

## The container API
Similar to the core `Vertx` class implementation, the container API is
a top-level static class called `Container` which can be used to deploy
or undeploy verticles and modules and allows configuration and logging
facilities to be accessed.

```php
$log = Container::logger();
$log->info('Hello world!');
```

## The Event Bus API
The event bus is the nervous system of Vert.x.

It allows verticles to communicate with each other irrespective of
what language they are written in, and whether they're in the same
Vert.x instance, or in a different Vert.x instance. For more information
on the Vert.x event bus see the [project documentation](http://vertx.io/docs.html).

### Registering and unregistering handlers
Vert.x PHP handlers are implemented using PHP's closures. Closures are
ideal for event-driven frameworks as they reduce clutter.

#### Vertx\EventBus\registerHandler
```
Vertx\EventBus::registerHandler($address, $handler, $resultHandler = NULL)
```

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

```php
$eventBus->registerHandler('test.address', $handler, function() use ($log) {
  $log->info("Yay! I've been propigated around the cluster!");
});
```

To unregister a handler, simply call the `unregisterHandler` method.

#### Vertx\EventBus::unregisterHandler
```
Vertx\EventBus::unregisterHandler($address, $handler)
```

```php
$eventBus->unregisterHandler('test.address', $handler);
```

### Publishing messages

#### Vertx\EventBus::publish
```
Vertx\EventBus::publish($address, $message);
```

```php
$eventBus->publish('test.address', 'Hello world!');
```

### Sending messages

#### Vertx\EventBus::send
```
Vertx\EventBus::send($address, $message, $replyHandler = NULL)
```

```php
$eventBus->send('test.address', 'Hello world!');
```

### Replying to messages

#### Vertx\EventBus\Message::reply
```
Vertx\EventBus\Message::reply($message, $replyHandler = NULL)
```

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

## Shared data

## Buffers

## Delayed and periodic tasks

### One-shot timers

A one shot timer calls an event handler after a certain delay, expressed
in milliseconds.

To set a timer to fire once you use the `setTimer` method passing in the
delay and a handler function

#### Vertx::setTimer
```
Vertx::setTimer($delay, $handler)
```

```php
$log = Container::logger();
$timer_id = Vertx::setTimer(1000, function($timer_id) use ($log) {
  $log->info('Timer went off!');
});
```

### Periodic timers
You can also set a timer to fire periodically by using the `setPeriodic` method.
There will be an initial delay equal to the period. The return value of
`setPeriodic` is a unique timer id (long). This can be later used if the timer
needs to be cancelled. The argument passed into the timer event handler is
also the unique timer id:

#### Vertx::setPeriodic
```
Vertx::setPeriodic($delay, $handler)
```

```php
$log = Container::logger();
$timer_id = Vertx::setPeriodic(1000, function($timer_id) use ($log) {
  $log->info('Timer is going off every second!');
});
```

### Cancelling timers

#### Vertx::cancelTimer
```
Vertx::cancelTimer($id)
```

```php
$log = Container::logger();
$timer_id = Vertx::setPeriodic(1000, function($timer_id) use ($log) {
  $log->info('Timer is going off every second!');
  $log->info('Cancelling the timer!');
  Vertx::cancelTimer($timer_id);
});
```

## Writing TCP Servers and Clients

### Creating a Net Server

To create a TCP server you call the `createNetServer` method on the static `Vertx`
class.

```php
$server = Vertx::createNetServer();
```

### Starting a Net Server

To tell the server to start listening, we call the `listen` method of the server
instance.

```php
$server = Vertx::createNetServer();
$server->listen(1234);
```

The first parameter to `listen` is the port. A wildcard port of 0 can be specified
which means a random available port will be chosen to actually listen at. Once the
server has completed listening you can then call the port() function of the server
to find out the real port it is using.

The second parameter is the hostname or ip address. If it is omitted it will
default to 0.0.0.0 which means it will listen at all available interfaces.

The actual bind is asynchronous so the server might not actually be listening until
some time after the call to listen has returned. If you want to be notified when
the server is actually listening you can provide a closure as the third argument to
the listen call. This callback will be called when the server has connected.
For example:

```php
$log = Container::logger();
$server->listen(1234, 'myhost', function($error) use ($log) {
  if (empty($error)) {
    $log->info('Server is listening!');
  }
});
```

### Getting notified of incoming connections

To be notified when a connection occurs we need to call the `connectHandler` method of
the server, passing in a function. The function will then be called when a
connection is made:

```php
$log = Container::logger();

Vertx::createNetServer()->connectHandler(function($socket) use ($log) {
  $log->info('A client has connected to the server!');
})->listen(1234, 'localhost');
```

### Closing a Net Server

To close a net server just call the `close` method.

```php
$server->close();
```

The close is actually asynchronous and might not complete until some time after the
close method has returned. If you want to be notified when the actual close has
completed then you can pass in a handler to the close method.

This handler will then be called when the close has fully completed.

```php
$server->close(function($error) {
  Container::logger()->info('The server closed successfully!');
});
```

If an error occurs then the `$error` argument to the `close` handler will be a Java
exception instance.

### Net Server properties

NetServer has a set of properties you can set which affect its behaviour. Firstly
there are bunch of properties used to tweak the TCP parameters, in most cases
you won't need to set these:

`tcpNoDelay($tcpNoDelay)` If true then Nagle's Algorithm is disabled. If false then
it is enabled.

`sendBufferSize($size)` Sets the TCP send buffer size in bytes.

`receiveBufferSize($size)` Sets the TCP receive buffer size in bytes.

`tcpKeepAlive($keepAlive)` if keepAlive is true then TCP keep alive is enabled, if
false it is disabled.

`reuseAddress($reuse)` if reuse is true then addresses in TIME_WAIT state can be
reused after they have been closed.

`soLinger($linger)`

`trafficClass($trafficClass)`

NetServer has a further set of properties which are used to configure SSL.
We'll discuss those later on.

Note that each of the above properties can also be retrieved by calling the
respective methods without any arguments. For example, to get the receive buffer
size, call `receiveBufferSize` with no arguments:

```php
$server = Vertx::createNetServer();
$size = $server->receiveBufferSize();
```

Alternatively, Vert.x PHP uses PHP's magic `__get` method to allow access to
these types of properties using direct access while still maintaining
encapsulation. So, the same property can be accessed like this:

```php
$server = Vertx::createNetServer();
$size = $server->receiveBufferSize;
```

### Reading data from the socket

To read data from the socket you need to set the dataHandler on the socket.
This handler will be called with a Buffer every time data is received on the
socket. You could try the following code and telnet to it to send some data:

```php
$server = Vertx::createNetServer();

$server->connectHandler(function($socket) {
  $log = Container::logger();
  $socket->dataHandler(function($buffer) {
    $log->info('I received '. $buffer->length() .' bytes of data.');
  })
})->listen(1234, 'localhost');
```

### Writing data to the socket

$server = Vertx::createNetServer();

$server->connectHandler(function($socket) {
  $log = Container::logger();
  $socket->dataHandler(function($buffer) use ($socket) {
    $socket->write('Thanks for the data!');
  })
})->listen(1234, 'localhost');
```

Remember that when using closures with PHP, you must remember to indicate
whether external variables are used within the closure with the `use`
expression, otherwise the PHP interpreter will throw an error.

### Socket remote address

You can find out the remote address of the socket (i.e. the address of the
other side of the TCP IP connection) by calling `remoteAddress()`.

```php
$address = $socket->remoteAddress();
```

Just as with Net Server properties, socket properties can be read using
the magic PHP `__get` method.

```php
$address = $socket->remoteAddress;
```

### Closing a socket

You can close a socket by invoking the `close` method. This will close the
underlying TCP connection.

```php
$socket->close();
```

### Close handler

If you want to be notified when a socket is closed, you can set the
`closeHandler':

```php
$server = Vertx::createNetServer();

$server->connectHandler(function($socket) {
  $log = Container::logger();
  $socket->closeHandler(function() use ($log) {
    $log->info('The socket is now closed.');
  });
})->listen(1234, 'localhost');
```

### Exception handler

You can set an exception handler on the socket that will be called if an
exception occurs:

```php
$server = Vertx::createNetServer();

$server->connectHandler(function($socket) {
  $log = Container::logger();
  $socket->exceptionHandler(function($error) use ($log) {
    $log->error('Oh noes! '. $error->getMessage());
  });
})->listen(1234, 'localhost');
```

### Event bus write handler

Every NetSocket automatically registers a handler on the event bus, and when any
buffers are received in this handler, it writes them to itself. This enables you
to write data to a NetSocket which is potentially in a completely different verticle
or even in a different Vert.x instance by sending the buffer to the address of
that handler.

The address of the handler is given by the `writeHandlerID` method.

For example to write some data to the NetSocket from a completely different
verticle you could do:

```php
$writeHandlerID = ... // E.g. retrieve the ID from shared data

Vertx::eventBus()->send($writeHandlerID, $buffer);
```

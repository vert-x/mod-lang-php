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

#### Accessing server properties

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

### Creating a Net Client

To create a TCP client you call the `createNetClient` method on the static `Vertx`
class.

```php
$client = Vertx::createNetClient();
```

### Making a connection

To actually connect to a server you invoke the connect method:

```php
$client = Vertx::createNetClient();
$log = Container::logger();
$client->connect(1234, 'localhost', function($socket, $error) use ($log) {
  if (!$error) {
    $log->info('Connected to the server!');
  }
});
```

The `connect` method takes the port number as the first parameter, followed by
the hostname or ip address of the server. The third parameter is a connect
handler. This handler will be called when the connection actually occurs.

The first argument passed into the connect handler is a the `NetSocket`. If an
exception has occurred, the socket will be null and the second argument will
be an instance of a Java exception. Otherwise, the second argument will be null.

You can read and write data from the socket in exactly the same way as you do
on the server side.

You can also close it, set the closed handler, set the exception handler and
use it as a `ReadStream` or `WriteStream` exactly the same as the server side
`NetSocket`.

### Configuring reconnection

A NetClient can be configured to automatically retry connecting or reconnecting
to the server in the event that it cannot connect or has lost its connection.
This is done by invoking the functions `setReconnectAttempts` and
`setReconnectInterval`:

```php
$client = Vertx::createNetClient();

$client->reconnectAttempts(1000);
$client->reconnectInterval(100);
```

`reconnectAttempts` determines how many times the client will try to connect to
the server before giving up. A value of -1 represents an infinite number of
times. The default value is 0. I.e. no reconnection is attempted.

`reconnectInterval` detemines how long, in milliseconds, the client will wait
between reconnect attempts. The default value is 1000.

### Net Client properties

Just like NetServer, NetClient also has a set of TCP properties you can set
which affect its behaviour. They have the same meaning as those on NetServer.

### SSL Servers

Net servers can also be configured to work with Transport Layer Security
(previously known as SSL).

When a NetServer is working as an SSL Server the API of the NetServer and
NetSocket is identical compared to when it working with standard sockets.
Getting the server to use SSL is just a matter of configuring the NetServer
before `listen` is called.

To enabled SSL the function `ssl(TRUE)` must be called on the Net Server.

The server must also be configured with a key store and an optional trust
store.

These are both Java keystores which can be managed using the keytool utility
which ships with the JDK.

The keytool command allows you to create keystores, and import and export
certificates from them.

The key store should contain the server certificate. This is mandatory - the
client will not be able to connect to the server over SSL if the server does
not have a certificate.

The key store is configured on the server using the `keyStorePath` and
`keyStorePassword` methods.

The trust store is optional and contains the certificates of any clients it
should trust. This is only used if client authentication is required.

To configure a server to use server certificates only:

```php
$server = Vertx::createNetServer()
  ->ssl(TRUE)
  ->keyStorePath('/path/to/your/keystore/server-keystore.jks')
  ->keyStorePassword('password');
```

Making sure that server-keystore.jks contains the server certificate.

To configure a server to also require client certificates:

```php
$server = Vertx::createNetServer()
  ->ssl(TRUE)
  ->keyStorePath('/path/to/your/keystore/server-keystore.jks')
  ->keyStorePassword('password')
  ->trustStorePath('/path/to/your/truststore/server-truststore.jks')
  ->trustStorePassword('password')
  ->clientAuthRequired(true);
```

Making sure that `server-truststore.jks` contains the certificates of any
clients who the server trusts.

If `clientAuthRequired` is set to true and the client cannot provide a
certificate, or it provides a certificate that the server does not trust
then the connection attempt will not succeed.

#### Accessing SSL properties

Just as with other server properties, these properties can be accesed
directly on the object.

```php
if ($server->ssl) {
  Container::logger()->info('Server is SSL.');
}
```

### SSL Clients

Net Clients can also be easily configured to use SSL. They have the exact
same API when using SSL as when using standard sockets.

To enable SSL on a NetClient the function `ssl(TRUE)` is called.

If the `trustAll(TRUE)` is invoked on the client, then the client will trust
all server certificates. The connection will still be encrypted but this
mode is vulnerable to 'man in the middle' attacks. I.e. you can't be sure
who you are connecting to. Use this with caution. Default value is false.

If trustAll is false then a client trust store must be configured and should
contain the certificates of the servers that the client trusts.

The client trust store is just a standard Java key store, the same as the
key stores on the server side. The client trust store location is set by
using the function `trustStorePath` on the `NetClient`. If a server presents
a certificate during connection which is not in the client trust store, the
connection attempt will not succeed.

If the server requires client authentication then the client must present its
own certificate to the server when connecting. This certificate should reside
in the client key store. Again it's just a regular Java key store. The client
keystore location is set by using the function `keyStorePath` on the `NetClient`.

To configure a client to trust all server certificates (dangerous):

```php
$client = Vertx::createNetClient()
  ->ssl(TRUE)
  ->trustAll(TRUE);
```

To configure a client to only trust those certificates it has in its trust store:

```php
$client = Vertx::createNetClient()
  ->ssl(TRUE)
  ->trustStorePath('/path/to/your/client/truststore/client-truststore.jks')
  ->trustStorePassword('password');
```

To configure a client to only trust those certificates it has in its trust
store, and also to supply a client certificate:

```php
$client = Vertx::createNetClient()
  ->ssl(TRUE)
  ->trustStorePath('/path/to/your/client/truststore/client-truststore.jks')
  ->trustStorePassword('password')
  ->clientAuthRequired(TRUE)
  ->keyStorePath('/path/to/keystore/holding/client/cert/client-keystore.jks')
  ->keyStorePassword('password');
```

#### Accessing SSL properties

Just as with other client properties, these properties can be accesed
directly on the object.

```php
if ($client->clientAuthRequired) {
  Container::logger()->info('Client required authentication.');
}
```

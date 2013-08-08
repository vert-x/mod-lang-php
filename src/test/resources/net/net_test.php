<?php
/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

use Vertx\Test\TestRunner;
use Vertx\Test\PhpTestCase;

/**
 * A Vert.x Net test case.
 */
class NetTestCase extends PhpTestCase {

  private $server = NULL;

  private $client = NULL;

  public function setUp() {
    $this->server = Vertx::createNetServer();
    $this->client = Vertx::createNetClient();
  }

  /**
   * Tests connecting client to server.
   */
  public function testConnect() {
    $this->server->connectHandler(function($socket) {
      $this->assertNotNull($socket);
      $this->assertNotNull($socket->localAddress);
      $this->assertNotNull($socket->remoteAddress);
    });

    $this->server->listen(8181, '0.0.0.0', function($server, $error) {
      $this->assertNull($error, 'An error was thrown on server listen.');
      $this->assertNotNull($server, 'The server was not passed to server listen handler.');
      $this->client->connect(8181, 'localhost', function($socket, $error) {
        $this->assertNull($error);
        $this->assertNotNull($socket);
        $this->assertNotNull($socket->localAddress);
        $this->assertNotNull($socket->remoteAddress);
        $this->complete();
      });
    });
  }

  /**
   * Tests net server methods.
   */
  public function testServerMethods() {
    $server = Vertx::createNetServer();

    $server->ssl = TRUE;
    $this->assertTrue($server->ssl);
    $server->ssl = FALSE;
    $this->assertFalse($server->ssl);
    $server->ssl(TRUE);
    $this->assertTrue($server->ssl());

    $server->keyStorePath('foo.jks');
    $this->assertEquals($server->keyStorePath(), 'foo.jks');
    $server->keyStorePath = 'bar.jks';
    $this->assertEquals($server->keyStorePath, 'bar.jks');

    $server->keyStorePassword('foo');
    $this->assertEquals($server->keyStorePassword(), 'foo');
    $server->keyStorePassword = 'bar';
    $this->assertEquals($server->keyStorePassword, 'bar');

    $server->trustStorePath('foo.jks');
    $this->assertEquals($server->trustStorePath(), 'foo.jks');
    $server->trustStorePath = 'bar.jks';
    $this->assertEquals($server->trustStorePath, 'bar.jks');

    $server->trustStorePassword('foo');
    $this->assertEquals($server->trustStorePassword(), 'foo');
    $server->trustStorePassword = 'bar';
    $this->assertEquals($server->trustStorePassword, 'bar');

    $server->sendBufferSize(12345);
    $this->assertEquals($server->sendBufferSize(), 12345);
    $server->sendBufferSize = 12345;
    $this->assertEquals($server->sendBufferSize, 12345);

    $server->receiveBufferSize(12345);
    $this->assertEquals($server->receiveBufferSize(), 12345);
    $server->receiveBufferSize = 12345;
    $this->assertEquals($server->receiveBufferSize, 12345);

    $server->keepAlive = TRUE;
    $this->assertTrue($server->keepAlive);
    $server->keepAlive = FALSE;
    $this->assertFalse($server->keepAlive);
    $server->keepAlive(TRUE);
    $this->assertTrue($server->keepAlive());

    $server->reuseAddress = TRUE;
    $this->assertTrue($server->reuseAddress);
    $server->reuseAddress = FALSE;
    $this->assertFalse($server->reuseAddress);
    $server->reuseAddress(TRUE);
    $this->assertTrue($server->reuseAddress());

    $server->trafficClass(12345);
    $this->assertEquals($server->trafficClass(), 12345);
    $server->trafficClass = 12345;
    $this->assertEquals($server->trafficClass, 12345);

    $this->complete();
  }

  /**
   * Tests net client methods.
   */
  public function testClientMethods() {
    $client = Vertx::createNetClient();

    $client->ssl = TRUE;
    $this->assertTrue($client->ssl);
    $client->ssl = FALSE;
    $this->assertFalse($client->ssl);
    $client->ssl(TRUE);
    $this->assertTrue($client->ssl());

    $client->keyStorePath('foo.jks');
    $this->assertEquals($client->keyStorePath(), 'foo.jks');
    $client->keyStorePath = 'bar.jks';
    $this->assertEquals($client->keyStorePath, 'bar.jks');

    $client->keyStorePassword('foo');
    $this->assertEquals($client->keyStorePassword(), 'foo');
    $client->keyStorePassword = 'bar';
    $this->assertEquals($client->keyStorePassword, 'bar');

    $client->trustStorePath('foo.jks');
    $this->assertEquals($client->trustStorePath(), 'foo.jks');
    $client->trustStorePath = 'bar.jks';
    $this->assertEquals($client->trustStorePath, 'bar.jks');

    $client->trustStorePassword('foo');
    $this->assertEquals($client->trustStorePassword(), 'foo');
    $client->trustStorePassword = 'bar';
    $this->assertEquals($client->trustStorePassword, 'bar');

    $client->trustAll = TRUE;
    $this->assertTrue($client->trustAll);
    $client->trustAll = FALSE;
    $this->assertFalse($client->trustAll);
    $client->trustAll(TRUE);
    $this->assertTrue($client->trustAll());

    $client->sendBufferSize(12345);
    $this->assertEquals($client->sendBufferSize(), 12345);
    $client->sendBufferSize = 12345;
    $this->assertEquals($client->sendBufferSize, 12345);

    $client->receiveBufferSize(12345);
    $this->assertEquals($client->receiveBufferSize(), 12345);
    $client->receiveBufferSize = 12345;
    $this->assertEquals($client->receiveBufferSize, 12345);

    $client->keepAlive = TRUE;
    $this->assertTrue($client->keepAlive);
    $client->keepAlive = FALSE;
    $this->assertFalse($client->keepAlive);
    $client->keepAlive(TRUE);
    $this->assertTrue($client->keepAlive());

    $client->reuseAddress = TRUE;
    $this->assertTrue($client->reuseAddress);
    $client->reuseAddress = FALSE;
    $this->assertFalse($client->reuseAddress);
    $client->reuseAddress(TRUE);
    $this->assertTrue($client->reuseAddress());

    $client->trafficClass(12345);
    $this->assertEquals($client->trafficClass(), 12345);
    $client->trafficClass = 12345;
    $this->assertEquals($client->trafficClass, 12345);

    $this->complete();
  }

  public function tearDown() {
    $this->client->close();
    $this->server->close();
  }

}

TestRunner::run(new NetTestCase());

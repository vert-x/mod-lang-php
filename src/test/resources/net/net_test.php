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

  public function tearDown() {
    $this->client->close();
    $this->server->close();
  }

}

TestRunner::run(new NetTestCase());

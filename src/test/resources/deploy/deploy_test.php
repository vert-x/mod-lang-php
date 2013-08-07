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
 * A Vert.x module/verticle deploy test case.
 */
class DeployTestCase extends PhpTestCase {

  private $eventBus = NULL;

  const TEST_ADDRESS = 'test-address';

  public function setUp() {
    $this->eventBus = Vertx::eventBus();
  }

  /**
   * Tests deploying a verticle.
   */
  public function testDeploy() {
    $this->eventBus->registerHandler(self::TEST_ADDRESS, function($message) {
      if ($message->body == 'started') {
        $this->complete();
      }
    });

    Vertx::deployVerticle('child.php', array('foo' => 'bar'), 1, function($id, $error) {
      $this->assertNull($error);
    });
  }

  /**
   * Tests undeploying a verticle.
   */
  public function testUndeploy() {
    $this->eventBus->registerHandler(self::TEST_ADDRESS, function($message) {});

    Vertx::deployVerticle('child.php', array('foo' => 'bar'), 1, function($id, $error) {
      $this->assertNull($error);
      Vertx::undeployVerticle($id, function($error) {
        $this->assertNull($error);
        $this->complete();
      });
    });
  }

  /**
   * Tests deploying a verticle.
   */
  public function testDeploy2() {
    Vertx::deployVerticle('child2.php', NULL, 1, function($id, $error) {
      $this->assertNull($error);
      $this->assertNotNull($id);
      Vertx::undeployVerticle($id, function($error) {
        $this->assertNull($error);
        $this->complete();
      });
    });
  }

  /**
   * Tests failing a verticle deploy.
   */
  public function testDeployFail() {
    Vertx::deployVerticle('asdlkjsdalf', NULL, 1, function($id, $error) {
      $this->assertNull($id);
      $this->assertNotNull($error);
      $this->complete();
    });
  }

  /**
   * Tests failing a verticle undeploy.
   */
  public function testUndeployFail() {
    Vertx::undeployVerticle('asdlkjsdalf', function($error) {
      $this->assertNotNull($error);
      $this->complete();
    });
  }

}

TestRunner::run(new DeployTestCase());

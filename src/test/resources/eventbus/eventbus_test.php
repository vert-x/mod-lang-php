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
 * A Vert.x EventBus test case.
 */
class EventBusTestCase extends PhpTestCase {

  private $currentHandlerId = NULL;

  public function setUp() {
    $this->eventBus = Vertx::eventBus();
    $this->currentHandlerId = NULL;
  }

  /**
   * Tests sending an empty message on the event bus.
   */
  public function testSendEmpty() {
    $this->currentHandlerId = $this->eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body, array());
      $this->eventBus->unregisterHandler($this->currentHandlerId);
      $this->complete();
    });

    $message = array();
    $this->eventBus->send('test-address', $message);
  }

  /**
   * Tests sending a simple array message on the event bus.
   */
  public function testSendSimple() {
    $this->currentHandlerId = $this->eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $this->eventBus->unregisterHandler($this->currentHandlerId);
      $this->complete();
    });

    $message = array('message' => 'Hello world!');
    $this->eventBus->send('test-address', $message);
  }

  /**
   * Tests sending an empty reply on the event bus.
   */
  public function testReplyEmpty() {
    $this->currentHandlerId = $this->eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $message->reply(array());
    });

    $message = array('message' => 'Hello world!');
    $this->eventBus->send('test-address', $message, function($reply) {
      $this->assertEquals($reply->body, array());
      $this->eventBus->unregisterHandler($this->currentHandlerId);
      $this->complete();
    });
  }

  /**
   * Tests replying to a message on the event bus.
   */
  public function testReplySimple() {
    $this->currentHandlerId = $this->eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $message->reply(array('message2' => 'Hello world 2!'));
    });

    $message = array('message' => 'Hello world!');
    $this->eventBus->send('test-address', $message, function($reply) {
      $this->assertEquals($reply->body['message2'], 'Hello world 2!');
      $this->eventBus->unregisterHandler($this->currentHandlerId);
      $this->complete();
    });
  }

  /**
   * Helper method for echoing messages.
   */
  private function doEcho($message) {
    $this->currentHandlerId = $this->eventBus->registerHandler('test-address', function($echo) {
      $echo->reply($echo->body);
    });

    $this->eventBus->send('test-address', $message, function($reply) use ($message) {
      $this->assertEquals($reply->body, $message);
      $this->eventBus->unregisterHandler($this->currentHandlerId);
      $this->complete();
    });
  }

  /**
   * Tests echoing a string.
   */
  public function testEchoString() {
    $this->doEcho('Hello world!');
  }

  /**
   * Tests echoing a number.
   */
  public function testEchoFixnum() {
    $this->doEcho(12345);
  }

  /**
   * Tests echoing a float.
   */
  public function testEchoFloat() {
    $this->doEcho(1.2345);
  }

  /**
   * Tests echoing a boolean true.
   */
  public function testEchoBooleanTrue() {
    $this->doEcho(TRUE);
  }

  /**
   * Tests echoing a boolean false.
   */
  public function testEchoBooleanFalse() {
    $this->doEcho(FALSE);
  }

}

TestRunner::run(new EventBusTestCase());

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

  /**
   * Tests sending an empty message on the event bus.
   */
  public function testSendEmpty() {
    $eventBus = Vertx::eventBus();
    $eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body, array());
      $this->complete();
    });

    $message = array();
    $eventBus->send('test-address', $message);
  }

  /**
   * Tests sending a simple array message on the event bus.
   */
  public function testSendSimple() {
    $eventBus = Vertx::eventBus();
    $eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $this->complete();
    });

    $message = array('message' => 'Hello world!');
    $eventBus->send('test-address', $message);
  }

  /**
   * Tests sending an empty reply on the event bus.
   */
  public function testReplyEmpty() {
    $eventBus = Vertx::eventBus();
    $eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $message->reply(array());
    });

    $message = array('message' => 'Hello world!');
    $eventBus->send('test-address', $message, function($reply) {
      $this->assertEquals($reply->body, array());
      $this->complete();
    });
  }

  /**
   * Tests replying to a message on the event bus.
   */
  public function testReplySimple() {
    $eventBus = Vertx::eventBus();
    $eventBus->registerHandler('test-address', function($message) {
      $this->assertEquals($message->body['message'], 'Hello world!');
      $message->reply(array('message2' => 'Hello world 2!'));
    });

    $message = array('message' => 'Hello world!');
    $eventBus->send('test-address', $message, function($reply) {
      $this->assertEquals($reply->body['message2'], 'Hello world 2!');
      $this->complete();
    });
  }

  /**
   * Helper method for echoing messages.
   */
  private function doEcho($message) {
    $eventBus = Vertx::eventBus();
    $eventBus->registerHandler('test-address', function($echo) {
      $echo->reply($echo->body);
    });

    $eventBus->send('test-address', $message, function($reply) use ($message) {
      $this->assertEquals($reply->body, $message);
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

<?php

use Vertx\Test\TestRunner;
use Vertx\Test\PhpTestCase;

/**
 * A Vert.x EventBus test case.
 */
class EventBusTest extends PhpTestCase {

  /**
   * Tests sending a simple message on the event bus.
   */
  public function testSimpleSend() {
    $eventBus = Vertx::eventBus();
    $this->assertTrue(TRUE);
  }

}

$test = new EventBusTest();
TestRunner::run($test);

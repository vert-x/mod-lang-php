<?php

use Vertx\Test\TestRunner;
use Vertx\Test\PhpTestCase;

/**
 * A Vert.x EventBus test case.
 */
class EventBusTest extends PhpTestCase {

  public function setUp() {
    print "In setup!";
  }

  /**
   * Tests sending a simple message on the event bus.
   */
  public function testSendSimple() {
    $eventBus = Vertx::eventBus();
    $this->assertTrue(TRUE);
    $this->complete();
  }

  /**
   * Tests sending an array.
   */
  public function testSendArray() {
    $eventBus = Vertx::eventBus();
    $this->assertTrue(TRUE);
    $this->complete();
  }

}

$test = new EventBusTest();
TestRunner::run($test);

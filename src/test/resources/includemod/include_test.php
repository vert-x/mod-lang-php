<?php

use Vertx\Test\TestRunner;
use Vertx\Test\PhpTestCase;

class IncludeTestCase extends PhpTestCase {
    private $eventBus = NULL;
    const TEST_ADDRESS = "test.hello";

    public function setUp() {
        $this->eventBus = Vertx::eventBus();
    }

    public function testInclude() {
        Vertx::deployModule('io.vertx~php-includetest-mod~v1.0', NULL, 1, function($id, $error) {
            Vertx::logger()->info("deploy module $id");
            if ($error) {
                Vertx::logger()->error("error: $error");
                $this->assertTrue(false, "Assert that the deploy was successfull, but it wasn't");
                $this->complete();
            } else {
                $this->eventBus->send(self::TEST_ADDRESS, "hello", function($reply) {
                    $this->assertEquals($reply->body,"Hello Test");
                    $this->complete();
                });
            }
        });
    }
}

TestRunner::run(new IncludeTestCase());

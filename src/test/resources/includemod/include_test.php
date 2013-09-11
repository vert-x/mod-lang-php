<?php

use Vertx\Test\TestRunner;
use Vertx\Test\PhpTestCase;

class IncludeTestCase extends PhpTestCase {
    private $eventBus = NULL;

    public function setUp() {
        $this->eventBus = Vertx::eventBus();
    }

    public function testRequireVertx() {
        $this->eventBus->send("test.require_vertx", "hello", function($reply) {
            $this->assertEquals($reply->body,"Hello Test");
            $this->complete();
        });

    }

    public function testAutoload() {
        $this->eventBus->send("test.autoload", "hello", function($reply) {
            $this->assertEquals($reply->body,"Hello Test");
            $this->complete();
        });
    }
}


function copyMods() {
  Vertx::fileSystem()->copyRecursive("src/test/resources/includemod/mods", "target/mods", function($error) {
    if($error) {
        Vertx::logger()->error($error);
        return;
    } else {
        Vertx::deployModule('io.vertx~php-includetest-mod~v1.0', NULL, 1, function($id, $error) {
            if ($error) {
                Vertx::logger()->error($error);
                return;
            } else {
                TestRunner::run(new IncludeTestCase());
                Vertx::undeployModule($id);
            }
        });
    }
  });
}

Vertx::fileSystem()->deleteRecursive("target/mods/io.vertx~php-includetest-lib~v1.0", function() {
    Vertx::fileSystem()->deleteRecursive("target/mods/io.vertx~php-includetest-mod~v1.0", function() {
        copyMods();
    });
});

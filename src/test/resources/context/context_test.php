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
 * A Vert.x context test case.
 */
class ContextTestCase extends PhpTestCase {

  /**
   * Tests the Vertx runOnContext() method.
   */
  public function testRunOnContext() {
    Vertx::runOnContext(function() {
      $this->complete();
    });
  }

  /**
   * Tests the Context runOnContext method directly.
   */
  public function testGetContext() {
    $context = Vertx::currentContext();
    $context->runOnContext(function() {
      $this->complete();
    });
  }

}

TestRunner::run(new ContextTestCase());

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
 * A Vert.x timer test case.
 */
class TimerTestCase extends PhpTestCase {

  /**
   * Tests a one-off timer.
   */
  public function testOneOff() {
    Vertx::setTimer(10, function() {
      $this->complete();
    });
  }

  /**
   * Tests a periodic timer.
   */
  public function testPeriodic() {
    $total = 10;
    $count = 0;
    Vertx::setPeriodic(10, function($timer_id) use (&$count, $total) {
      $this->assertNotNull($timer_id);
      $count += 1;
      if ($count == $total) {
        Vertx::cancelTimer($timer_id);
        Vertx::setTimer(10, function() {
          $this->complete();
        });
      }
      else if ($count > $total) {
        $this->assertTrue(FALSE, 'Counter went off too many times!');
      }
    });
  }

}

TestRunner::run(new TimerTestCase());

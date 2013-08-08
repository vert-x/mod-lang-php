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
use Vertx\Buffer;

/**
 * A Vert.x buffer test case.
 */
class BufferTestCase extends PhpTestCase {

  const ALPHABET = 'abcdefghijklmnopqrstuvwxyz';

  /**
   * Tests appending a buffer to another buffer.
   */
  public function testAppendBuffer() {
    $buffer1 = new Buffer(substr(self::ALPHABET, 0, 10));
    $buffer2 = new Buffer(substr(self::ALPHABET, 10));
    $buffer1->appendBuffer($buffer2);
    $this->assertEquals($buffer1->length(), strlen(self::ALPHABET));
    $this->assertEquals($buffer1->toString(), self::ALPHABET);
    $this->complete();
  }

}

TestRunner::run(new BufferTestCase());

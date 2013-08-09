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
   * Tests buffer magic methods.
   */
  public function testMagic() {
    $length = 10;
    $buffer = new Buffer(substr(self::ALPHABET, 0, $length));
    $this->assertEquals($buffer->length(), $length);
    $this->assertEquals($buffer->length, $length);
    $this->assertEquals(count($buffer), $length);
    $buffer->appendString(substr(self::ALPHABET, $length));
    $this->assertEquals(count($buffer), 26);
    $this->assertEquals($buffer->toString(), self::ALPHABET);
    $this->assertNull($buffer->toString, self::ALPHABET);
    $this->assertEquals((string) $buffer, self::ALPHABET);
    $this->complete();
  }

  /**
   * Tests buffer string methods.
   */
  public function testString() {
    $buffer = new Buffer(substr(self::ALPHABET, 0, 10));
    $buffer->appendString(substr(self::ALPHABET, 10));
    $this->assertEquals((string) $buffer, self::ALPHABET);
    $string = $buffer->getString(0, 10);
    $this->assertEquals($string, substr(self::ALPHABET, 0, 10));
    $this->complete();
  }

  /**
   * Tests buffer integer methods.
   */
  public function testInteger() {
    $buffer = new Buffer(self::ALPHABET);
    $buffer->appendInt(10);
    $int = $buffer->getInt(26);
    $this->assertEquals($int, 10);
    $this->complete();
  }

  /**
   * Tests buffer long methods.
   */
  public function testLong() {
    $buffer = new Buffer(self::ALPHABET);
    $buffer->appendLong(10000);
    $int = $buffer->getLong(26);
    $this->assertEquals($int, 10000);
    $this->complete();
  }

  /**
   * Tests buffer short methods.
   */
  public function testShort() {
    $buffer = new Buffer(self::ALPHABET);
    $buffer->appendShort(10);
    $int = $buffer->getShort(26);
    $this->assertEquals($int, 10);
    $this->complete();
  }

  /**
   * Tests buffer float methods.
   */
  public function testFloat() {
    $buffer = new Buffer(self::ALPHABET);
    $buffer->appendFloat(1.123);
    $float = $buffer->getFloat(26);
    $this->assertEquals(round($float, 3), 1.123);
    $this->complete();
  }

  /**
   * Tests buffer double methods.
   */
  public function testDouble() {
    $buffer = new Buffer(self::ALPHABET);
    $buffer->appendFloat(1.123);
    $float = $buffer->getFloat(26);
    $this->assertEquals(round($float, 3), 1.123);
    $this->complete();
  }

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

  /**
   * Tests the basic buffer append method.
   */
  public function testAppendAny() {
    $buffer = new Buffer(substr(self::ALPHABET, 0, 10));
    $buffer->append(substr(self::ALPHABET, 10));
    $this->assertEquals(count($buffer), strlen(self::ALPHABET));
    $this->assertEquals($buffer->toString(), self::ALPHABET);
    $this->assertEquals((string) $buffer, self::ALPHABET);
    $this->complete();
  }

}

TestRunner::run(new BufferTestCase());

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

define('TEST_OUTPUT_DIRECTORY', 'php-test-output');

/**
 * A Vert.x FileSystem test case.
 */
class FileSystemTestCase extends PhpTestCase {

  private $fileSystem;

  public function setUp() {
    $this->fileSystem = Vertx::fileSystem();
    if ($this->fileSystem->existsSync(TEST_OUTPUT_DIRECTORY)) {
      $this->fileSystem->deleteRecursiveSync(TEST_OUTPUT_DIRECTORY);
    }
    $this->fileSystem->mkdirSync(TEST_OUTPUT_DIRECTORY, 'rwxr-xr-x');
  }

  /**
   * Tests copying a file.
   */
  public function testCopy() {
    $from = TEST_OUTPUT_DIRECTORY .'/from-file.txt';
    $to = TEST_OUTPUT_DIRECTORY .'/to-file.txt';
    $this->fileSystem->createFile($from, 'rwxr-xr-x', function($error) use ($from, $to) {
      $this->assertNull($error);
      $this->fileSystem->copy($from, $to, function($error) {
        $this->assertNull($error);
        $this->complete();
      });
    });
  }

  /**
   * Tests copying a file synchronously.
   */
  public function testCopySync() {
    $from = TEST_OUTPUT_DIRECTORY .'/from-file.txt';
    $to = TEST_OUTPUT_DIRECTORY .'/to-file.txt';
    $this->fileSystem->createFileSync($from, 'rwxr-xr-x');
    $this->fileSystem->copySync($from, $to);
    $this->complete();
  }

  /**
   * Tests moving a file.
   */
  public function testMove() {
    $from = TEST_OUTPUT_DIRECTORY .'/from-file.txt';
    $to = TEST_OUTPUT_DIRECTORY .'/to-file.txt';
    $this->fileSystem->createFile($from, 'rwxr-xr-x', function($error) use ($from, $to) {
      $this->assertNull($error);
      $this->fileSystem->move($from, $to, function($error) {
        $this->assertNull($error);
        $this->complete();
      });
    });
  }

  /**
   * Tests moving a file synchronously.
   */
  public function testMoveSync() {
    $from = TEST_OUTPUT_DIRECTORY .'/from-file.txt';
    $to = TEST_OUTPUT_DIRECTORY .'/to-file.txt';
    $this->fileSystem->createFileSync($from, 'rwxr-xr-x');
    $this->fileSystem->moveSync($from, $to);
    $this->complete();
  }

  /**
   * Tests file stats.
   */
  public function testStats() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $this->fileSystem->createFile($filename, 'rwxr-xr-x', function($error) use ($filename) {
      $this->assertNull($error);
      $this->fileSystem->props($filename, function($props, $error) {
        $this->assertNull($error);
        $this->assertNotNull($props->creationTime());
        $this->assertNotNull($props->creationTime);
        $this->assertNotNull($props->isDirectory());
        $this->assertNotNull($props->isDirectory);
        $this->assertNotNull($props->isOther());
        $this->assertNotNull($props->isOther);
        $this->assertNotNull($props->isRegularFile());
        $this->assertNotNull($props->isRegularFile);
        $this->assertNotNull($props->isSymbolicLink());
        $this->assertNotNull($props->isSymbolicLink);
        $this->assertNotNull($props->lastAccessTime());
        $this->assertNotNull($props->lastAccessTime);
        $this->assertNotNull($props->lastModifiedTime());
        $this->assertNotNull($props->lastModifiedTIme);
        $this->assertNotNull($props->size());
        $this->assertNotNull($props->size);
        $this->complete();
      });
    });
  }

  /**
   * Tests file stats synchronously.
   */
  public function testStatsSync() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $this->fileSystem->createFileSync($filename, 'rwxr-xr-x');
    $props = $this->fileSystem->propsSync($filename);
    $this->assertNotNull($props->creationTime());
    $this->assertNotNull($props->creationTime);
    $this->assertNotNull($props->isDirectory());
    $this->assertNotNull($props->isDirectory);
    $this->assertNotNull($props->isOther());
    $this->assertNotNull($props->isOther);
    $this->assertNotNull($props->isRegularFile());
    $this->assertNotNull($props->isRegularFile);
    $this->assertNotNull($props->isSymbolicLink());
    $this->assertNotNull($props->isSymbolicLink);
    $this->assertNotNull($props->lastAccessTime());
    $this->assertNotNull($props->lastAccessTime);
    $this->assertNotNull($props->lastModifiedTime());
    $this->assertNotNull($props->lastModifiedTIme);
    $this->assertNotNull($props->size());
    $this->assertNotNull($props->size);
    $this->complete();
  }

  /**
   * Tests deleting a file.
   */
  public function testDelete() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $this->fileSystem->createFile($filename, 'rwxr-xr-x', function($error) use ($filename) {
      $this->assertNull($error);
      $this->assertTrue($this->fileSystem->existsSync($filename));
      $this->fileSystem->delete($filename, function($error) {
        $this->assertNull($error);
        $this->complete();
      });
    });
  }

  /**
   * Tests deleting a file synchronously.
   */
  public function testDeleteSync() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $this->fileSystem->createFileSync($filename, 'rwxr-xr-x');
    $this->assertTrue($this->fileSystem->existsSync($filename));
    $this->fileSystem->deleteSync($filename);
    $this->assertFalse($this->fileSystem->existsSync($filename));
    $this->complete();
  }

  /**
   * Tests writing to and reading from a file.
   */
  public function testWriteReadFile() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $alphabet = 'abcdefghijklmnopqrstuvwxyz';
    $this->fileSystem->createFile($filename, 'rwxr-xr-x', function($error) use ($filename, $alphabet) {
      $this->assertNull($error);
      $this->assertTrue($this->fileSystem->existsSync($filename));
      $buffer = new Buffer();
      $buffer->appendString($alphabet);
      $this->fileSystem->writeFile($filename, $buffer, function($error) use ($filename, $alphabet) {
        $this->assertNull($error);
        $this->fileSystem->readFile($filename, function($buffer, $error) use ($filename, $alphabet) {
          $this->assertNull($error);
          $this->assertEquals((string) $buffer, $alphabet);
          $this->fileSystem->delete($filename, function($error) {
            $this->assertNull($error);
            $this->complete();
          });
        });
      });
    });
  }

  /**
   * Tests writing to and reading from a file synchronously.
   */
  public function testWriteReadFileSync() {
    $filename = TEST_OUTPUT_DIRECTORY .'/test-file.txt';
    $alphabet = 'abcdefghijklmnopqrstuvwxyz';
    $this->fileSystem->createFileSync($filename, 'rwxr-xr-x');
    $this->assertTrue($this->fileSystem->existsSync($filename));
    $buffer = new Buffer();
    $buffer->appendString($alphabet);
    $this->fileSystem->writeFileSync($filename, $buffer);
    $contents = $this->fileSystem->readFileSync($filename);
    $this->assertEquals($alphabet, (string) $contents);
    $this->fileSystem->deleteSync($filename);
    $this->complete();
  }

  public function tearDown() {
    $this->fileSystem->deleteRecursiveSync(TEST_OUTPUT_DIRECTORY);
  }

}

TestRunner::run(new FileSystemTestCase());

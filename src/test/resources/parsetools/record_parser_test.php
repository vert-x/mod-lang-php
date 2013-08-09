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
use Vertx\ParseTools\RecordParser;

/**
 * A Vert.x RecordParser test case.
 */
class RecordParserTestCase extends PhpTestCase {

  /**
   * Tests delimited record parsing.
   */
  public function testDelimited() {
    $input = '';
    for ($i = 0; $i < 100; $i++) {
      $input .= "line $i";
      if ($i < 99) {
        $input .= "\n";
      }
    }

    $lines = array();
    $parser = RecordParser::newDelimited("\n", function($buffer) use (&$lines) {
      $lines[] = trim((string) $buffer);
    });

    $parser->handle(new Vertx\Buffer($input));

    $count = 0;
    foreach ($lines as $line) {
      $this->assertEquals($line, "line $count");
      $count++;
    }
    $this->complete();
  }

}

TestRunner::run(new RecordParserTestCase());

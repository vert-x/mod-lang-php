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
 * A Vert.x SharedData test case.
 */
class SharedDataTestCase extends PhpTestCase {

  private $sharedData = NULL;

  public function setUp() {
    $this->sharedData = Vertx::sharedData();
  }

  /**
   * Tests loading shared maps.
   */
  public function testMapLoad() {
    $map1 = $this->sharedData->getMap('map1');
    $this->assertNotNull($map1);

    $map2 = $this->sharedData->getMap('map1');
    $this->assertNotNull($map2);

    $this->assertEquals(count($map1), count($map2));

    $map3 = $this->sharedData->getMap('map3');
    $this->assertNotNull($map3);
    $this->complete();
  }

  /**
   * Tests setting and getting strings in a shared map.
   */
  public function testMapString() {
    $this->doTestValue('Hello world!');
  }

  /**
   * Tests setting and getting booleans in a shared map.
   */
  public function testMapBoolean() {
    $this->doTestValue(TRUE);
    $this->doTestValue(FALSE);
  }

  /**
   * Tests setting and getting integers in a shared map.
   */
  public function testMapInteger() {
    $this->doTestValue(12345);
  }

  /**
   * Tests setting and getting floats in a shared map.
   */
  public function testMapFloat() {
    $this->doTestValue(1.2345);
  }

  private function doTestValue($value) {
    $map1 = $this->sharedData->getMap('map1');
    $this->assertNotNull($map1);

    $map2 = $this->sharedData->getMap('map1');
    $this->assertNotNull($map2);

    $key = 'foo';

    $map1[$key] = $value;

    $this->assertEquals($map1[$key], $value);
    $this->assertEquals($map2[$key], $value);
    $this->assertEquals($map1[$key], $map2[$key]);

    $map3 = $this->sharedData->getMap('map1');
    $this->assertEquals($map3[$key], $value);
    $this->complete();
  }

}

TestRunner::run(new SharedDataTestCase());

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
 * A Vert.x RouteMatcher test case.
 */
class RouteMatcherTestCase extends PhpTestCase {

  private $server = NULL;

  private $client = NULL;

  private $routeMatcher = NULL;

  private static $params = array(
    'name' => 'foo',
    'version' => 'v0.1',
  );

  private static $regexParams = array(
    'param0' => 'foo',
    'param1' => 'v0.1',
  );

  private static $regex = '\\/([^\\/]+)\\/([^\\/]+)';

  public function setUp() {
    $this->server = Vertx::createHttpServer();
    $this->client = Vertx::createHttpClient();
    $this->client->port = 8080;
    $this->routeMatcher = new Vertx\Http\RouteMatcher();
    $this->server->requestHandler($this->routeMatcher);
  }

  public function testGetWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;
    $uri = '/foo/v0.1';

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params, $uri) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->get($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->get($uri, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  public function tearDown() {
    $this->server->close();
    $this->client->close();
  }

}

TestRunner::run(new RouteMatcherTestCase());

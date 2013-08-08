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

  const URI = '/foo/v0.1';

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

  /**
   * Tests the GET method with a pattern.
   */
  public function testGetWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->get($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->get(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the GET method with a regular expression.
   */
  public function testGetWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->getWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->get(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the PUT method with a pattern.
   */
  public function testPutWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->put($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->put(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the PUT method with a regular expression.
   */
  public function testPutWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->putWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->put(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the POST method with a pattern.
   */
  public function testPostWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->post($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->post(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the POST method with a regular expression.
   */
  public function testPostWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->postWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->post(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the DELETE method with a pattern.
   */
  public function testDeleteWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->delete($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->delete(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the DELETE method with a regular expression.
   */
  public function testDeleteWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->deleteWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->delete(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the OPTIONS method with a pattern.
   */
  public function testOptionsWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->options($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->options(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the OPTIONS method with a regular expression.
   */
  public function testOptionsWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->optionsWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->options(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the HEAD method with a pattern.
   */
  public function testHeadWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->head($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->head(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the HEAD method with a regular expression.
   */
  public function testHeadWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->headWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->head(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the TRACE method with a pattern.
   */
  public function testTraceWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->trace($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->trace(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the TRACE method with a regular expression.
   */
  public function testTraceWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->traceWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->trace(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the PATCH method with a pattern.
   */
  public function testPatchWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->patch($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->patch(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the PATCH method with a regular expression.
   */
  public function testPatchWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->patchWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->patch(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the CONNECT method with a pattern.
   */
  public function testConnectWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->connect($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->connect(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the CONNECT method with a regular expression.
   */
  public function testConnectWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->connectWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->connect(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the ALL method with a pattern.
   */
  public function testAllWithPattern() {
    $pattern =  '/:name/:version';
    $params = self::$params;

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($pattern, $params) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->all($pattern, function($request) use ($params) {
        $this->assertEquals(count($request->params), count($params));
        foreach ($params as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->get(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

  /**
   * Tests the ALL method with a regular expression.
   */
  public function testAllWithRegex() {
    $this->server->listen(8080, '0.0.0.0', function($server, $error) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $this->routeMatcher->allWithRegex(self::$regex, function($request) {
        $this->assertEquals(count($request->params), count(self::$regexParams));
        foreach (self::$regexParams as $key => $value) {
          $this->assertEquals($value, $request->params[$key]);
        }
        $request->response->end();
      });

      $this->client->get(self::URI, function($response) {
        $this->assertEquals($response->statusCode, 200);
        $this->complete();
      })->end();
    });
  }

}

TestRunner::run(new RouteMatcherTestCase());

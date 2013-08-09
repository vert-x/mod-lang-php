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
 * A Vert.x HTTP test case.
 */
class HttpTestCase extends PhpTestCase {

  private static $path = '/someurl/blah.html';

  private static $query = 'param1=vparam1&param2=vparam2';

  private static $uri = 'http://localhost:8080/someurl/blah.html?param1=vparam1&param2=vparam2';

  private $server = NULL;

  private $client = NULL;

  public function setUp() {
    $this->server = Vertx::createHttpServer();
    $this->client = Vertx::createHttpClient();
    $this->client->port = 8080;
  }

  /**
   * Tests the HTTP GET method.
   */
  public function testGet() {
    $this->executeMethod('GET', FALSE);
  }

  /**
   * Tests the HTTP GET method using a chunked request.
   */
  public function testGetChunked() {
    $this->executeMethod('GET', TRUE);
  }

  /**
   * Tests the HTTP PUT method.
   */
  public function testPut() {
    $this->executeMethod('PUT', FALSE);
  }

  /**
   * Tests the HTTP PUT method using a chunked request.
   */
  public function testPutChunked() {
    $this->executeMethod('PUT', TRUE);
  }

  /**
   * Tests the HTTP POST method.
   */
  public function testPost() {
    $this->executeMethod('POST', FALSE);
  }

  /**
   * Tests the HTTP POST method using a chunked request.
   */
  public function testPostChunked() {
    $this->executeMethod('POST', TRUE);
  }

  /**
   * Tests the HTTP DELETE method.
   */
  public function testDelete() {
    $this->executeMethod('DELETE', FALSE);
  }

  /**
   * Tests the HTTP DELETE method using a chunked request.
   */
  public function testDeleteChunked() {
    $this->executeMethod('DELETE', TRUE);
  }

  /**
   * Tests the HTTP HEAD method.
   */
  public function testHead() {
    $this->executeMethod('HEAD', FALSE);
  }

  /**
   * Tests the HTTP HEAD method using a chunked request.
   */
  public function testHeadChunked() {
    $this->executeMethod('HEAD', TRUE);
  }

  /**
   * Tests the HTTP OPTIONS method.
   */
  public function testOptions() {
    $this->executeMethod('OPTIONS', FALSE);
  }

  /**
   * Tests the HTTP OPTIONS method using a chunked request.
   */
  public function testOptionsChunked() {
    $this->executeMethod('OPTIONS', TRUE);
  }

  /**
   * Tests the HTTP TRACE method.
   */
  public function testTrace() {
    $this->executeMethod('TRACE', FALSE);
  }

  /**
   * Tests the HTTP TRACE method using a chunked request.
   */
  public function testTraceChunked() {
    $this->executeMethod('TRACE', TRUE);
  }

  /**
   * Tests the HTTP PATCH method.
   */
  public function testPatch() {
    $this->executeMethod('PATCH', FALSE);
  }

  /**
   * Tests the HTTP PATCH method using a chunked request.
   */
  public function testPatchChunked() {
    $this->executeMethod('PATCH', TRUE);
  }

  /**
   * Tests the HTTP CONNECT method.
   */
  public function testConnect() {
    $this->executeMethod('CONNECT', FALSE);
  }

  /**
   * Tests the HTTP CONNECT method using a chunked request.
   */
  public function testConnectChunked() {
    $this->executeMethod('CONNECT', TRUE);
  }

  /**
   * Executes an HTTP request method.
   */
  private function executeMethod($method, $chunked) {
    $sent_buff = $this->createBuffer(1000);

    $this->server->requestHandler(function($request) use ($method, $chunked) {
      $this->assertEquals((string) $request->version, 'HTTP_1_1', 'HTTP version is incorrect.');
      $this->assertEquals($request->uri, self::$uri, 'URI is incorect.');
      $this->assertEquals($request->method, $method, 'HTTP method is incorrect.');
      $this->assertEquals($request->path, self::$path, 'Path is incorrect.');
      $this->assertEquals($request->query, self::$query, 'Query is incorrect.');
      $this->assertEquals($request->headers['header1'], 'vheader1', 'First header is incorrect.');
      $this->assertEquals($request->headers['header2'], 'vheader2', 'Second header is incorrect.');
      $this->assertEquals($request->params['param1'], 'vparam1', 'First parameter is incorrect.');
      $this->assertEquals($request->params['param2'], 'vparam2', 'Second parameter is incorrect.');

      $request->response->putHeader('rheader1', 'vrheader1');
      $request->response->putHeader('rheader2', 'vrheader2');

      $body = new Buffer();
      $request->dataHandler(function($buffer) use ($body) {
        $body->appendBuffer($buffer);
      });

      if ($method != 'HEAD' && $method != 'CONNECT') {
        $request->response->chunked = $chunked;
      }

      $request->endHandler(function() use ($method, $chunked, $body, $request) {
        if ($method != 'HEAD' && $method != 'CONNECT') {
          if (empty($chunked)) {
            $request->response->putHeader('Content-Length', (string) count($body));
          }
          $request->response->write($body);
          if (!empty($chunked)) {
            $request->response->putTrailer('trailer1', 'vtrailer1');
            $request->response->putTrailer('trailer2', 'vtrailer2');
          }
        }
        $request->response->end();
      });
    });

    $this->server->listen(8080, '0.0.0.0', function($server, $error) use ($method, $chunked, $sent_buff) {
      $this->assertNotNull($server);
      $this->assertNull($error);

      $request = $this->client->request($method, self::$uri, function($response) use ($method, $chunked, $sent_buff) {
        $this->assertEquals($response->statusCode, 200);
        $this->assertEquals($response->headers['rheader1'], 'vrheader1', 'First header is incorrect.');
        $this->assertEquals($response->headers['rheader2'], 'vrheader2', 'Second header is incorrect.');

        $body = new Buffer();
        $response->dataHandler(function($buffer) use ($body) {
          $body->appendBuffer($buffer);
        });

        $response->endHandler(function() use ($method, $chunked, $sent_buff, $body, $response) {
          if ($method != 'HEAD' && $method != 'CONNECT') {
            $this->assertEquals((string) $sent_buff, (string) $body, 'Sent and received buffers do not match.');
            if (!empty($chunked)) {
              $this->assertEquals($response->trailers['trailer1'], 'vtrailer1', 'First trailer is incorrect.');
              $this->assertEquals($response->trailers['trailer2'], 'vtrailer2', 'Second trailer is incorrect.');
            }
          }
          $this->complete();
        });
      });

      $request->chunked = $chunked;
      $request->putHeader('header1', 'vheader1');
      $request->putHeader('header2', 'vheader2');

      if (empty($chunked)) {
        $request->putHeader('Content-Length', (string) $sent_buff->length);
      }

      $request->headers['header3'] = 'vheader3_1';
      $request->headers['header3'] = 'vheader3';

      $request->write($sent_buff);
      $request->end();
    });
  }

  /**
   * Tests HTTP server methods.
   */
  public function testServerMethods() {
    $server = Vertx::createHttpServer();

    $server->ssl = TRUE;
    $this->assertTrue($server->ssl);
    $server->ssl = FALSE;
    $this->assertFalse($server->ssl);
    $server->ssl(TRUE);
    $this->assertTrue($server->ssl());

    $server->keyStorePath('foo.jks');
    $this->assertEquals($server->keyStorePath(), 'foo.jks');
    $server->keyStorePath = 'bar.jks';
    $this->assertEquals($server->keyStorePath, 'bar.jks');

    $server->keyStorePassword('foo');
    $this->assertEquals($server->keyStorePassword(), 'foo');
    $server->keyStorePassword = 'bar';
    $this->assertEquals($server->keyStorePassword, 'bar');

    $server->trustStorePath('foo.jks');
    $this->assertEquals($server->trustStorePath(), 'foo.jks');
    $server->trustStorePath = 'bar.jks';
    $this->assertEquals($server->trustStorePath, 'bar.jks');

    $server->trustStorePassword('foo');
    $this->assertEquals($server->trustStorePassword(), 'foo');
    $server->trustStorePassword = 'bar';
    $this->assertEquals($server->trustStorePassword, 'bar');

    $server->sendBufferSize(12345);
    $this->assertEquals($server->sendBufferSize(), 12345);
    $server->sendBufferSize = 12345;
    $this->assertEquals($server->sendBufferSize, 12345);

    $server->receiveBufferSize(12345);
    $this->assertEquals($server->receiveBufferSize(), 12345);
    $server->receiveBufferSize = 12345;
    $this->assertEquals($server->receiveBufferSize, 12345);

    $server->keepAlive = TRUE;
    $this->assertTrue($server->keepAlive);
    $server->keepAlive = FALSE;
    $this->assertFalse($server->keepAlive);
    $server->keepAlive(TRUE);
    $this->assertTrue($server->keepAlive());

    $server->reuseAddress = TRUE;
    $this->assertTrue($server->reuseAddress);
    $server->reuseAddress = FALSE;
    $this->assertFalse($server->reuseAddress);
    $server->reuseAddress(TRUE);
    $this->assertTrue($server->reuseAddress());

    $server->trafficClass(12345);
    $this->assertEquals($server->trafficClass(), 12345);
    $server->trafficClass = 12345;
    $this->assertEquals($server->trafficClass, 12345);

    $this->complete();
  }

  /**
   * Tests HTTP client methods.
   */
  public function testClientMethods() {
    $client = Vertx::createHttpClient();

    $client->host('0.0.0.0');
    $this->assertEquals($client->host(), '0.0.0.0');
    $client->host = '1.1.1.1';
    $this->assertEquals($client->host, '1.1.1.1');

    $client->port(12345);
    $this->assertEquals($client->port(), 12345);
    $client->port = 12345;
    $this->assertEquals($client->port, 12345);

    $client->ssl = TRUE;
    $this->assertTrue($client->ssl);
    $client->ssl = FALSE;
    $this->assertFalse($client->ssl);
    $client->ssl(TRUE);
    $this->assertTrue($client->ssl());

    $client->keyStorePath('foo.jks');
    $this->assertEquals($client->keyStorePath(), 'foo.jks');
    $client->keyStorePath = 'bar.jks';
    $this->assertEquals($client->keyStorePath, 'bar.jks');

    $client->keyStorePassword('foo');
    $this->assertEquals($client->keyStorePassword(), 'foo');
    $client->keyStorePassword = 'bar';
    $this->assertEquals($client->keyStorePassword, 'bar');

    $client->trustStorePath('foo.jks');
    $this->assertEquals($client->trustStorePath(), 'foo.jks');
    $client->trustStorePath = 'bar.jks';
    $this->assertEquals($client->trustStorePath, 'bar.jks');

    $client->trustStorePassword('foo');
    $this->assertEquals($client->trustStorePassword(), 'foo');
    $client->trustStorePassword = 'bar';
    $this->assertEquals($client->trustStorePassword, 'bar');

    $client->trustAll = TRUE;
    $this->assertTrue($client->trustAll);
    $client->trustAll = FALSE;
    $this->assertFalse($client->trustAll);
    $client->trustAll(TRUE);
    $this->assertTrue($client->trustAll());

    $client->sendBufferSize(12345);
    $this->assertEquals($client->sendBufferSize(), 12345);
    $client->sendBufferSize = 12345;
    $this->assertEquals($client->sendBufferSize, 12345);

    $client->receiveBufferSize(12345);
    $this->assertEquals($client->receiveBufferSize(), 12345);
    $client->receiveBufferSize = 12345;
    $this->assertEquals($client->receiveBufferSize, 12345);

    $client->keepAlive = TRUE;
    $this->assertTrue($client->keepAlive);
    $client->keepAlive = FALSE;
    $this->assertFalse($client->keepAlive);
    $client->keepAlive(TRUE);
    $this->assertTrue($client->keepAlive());

    $client->reuseAddress = TRUE;
    $this->assertTrue($client->reuseAddress);
    $client->reuseAddress = FALSE;
    $this->assertFalse($client->reuseAddress);
    $client->reuseAddress(TRUE);
    $this->assertTrue($client->reuseAddress());

    $client->verifyHost = TRUE;
    $this->assertTrue($client->verifyHost);
    $client->verifyHost = FALSE;
    $this->assertFalse($client->verifyHost);
    $client->verifyHost(TRUE);
    $this->assertTrue($client->verifyHost());

    $client->trafficClass(12345);
    $this->assertEquals($client->trafficClass(), 12345);
    $client->trafficClass = 12345;
    $this->assertEquals($client->trafficClass, 12345);

    $client->connectTimeout(12345);
    $this->assertEquals($client->connectTimeout(), 12345);
    $client->connectTimeout = 12345;
    $this->assertEquals($client->connectTimeout, 12345);

    $client->maxPoolSize(12345);
    $this->assertEquals($client->maxPoolSize(), 12345);
    $client->maxPoolSize = 12345;
    $this->assertEquals($client->maxPoolSize, 12345);

    $this->complete();
  }

  private function createBuffer($size) {
    $str = '';
    while (strlen($str) < $size) {
      $str .= 'abcdefghijklmnopqrstuvwxyz';
    }
    return new Buffer(substr($str, 0, $size));
  }

  public function tearDown() {
    $this->client->close();
    $this->server->close();
  }

}

TestRunner::run(new HttpTestCase());

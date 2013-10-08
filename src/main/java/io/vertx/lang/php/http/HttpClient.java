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
package io.vertx.lang.php.http;

import io.vertx.lang.php.Handler;
import io.vertx.lang.php.ResultModifier;
import io.vertx.lang.php.TCPClient;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpClient.
 * 
 * @author Jordan Halterman
 */
public class HttpClient extends TCPClient<org.vertx.java.core.http.HttpClient> {

  public HttpClient(org.vertx.java.core.http.HttpClient client) {
    super(client);
  }

  public HttpClient(Env env, org.vertx.java.core.http.HttpClient client) {
    super(client);
  }

  /**
   * Returns the HTTP host.
   */
  public StringValue host(Env env) {
    return env.createString(client.getHost());
  }

  /**
   * Sets the HTTP host.
   */
  public HttpClient host(Env env, StringValue host) {
    if (PhpTypes.notNull(host)) {
      client.setHost(host.toString());
    }
    else {
      client.setHost(null);
    }
    return this;
  }

  /**
   * Returns the HTTP port.
   */
  public int port(Env env) {
    return client.getPort();
  }

  /**
   * Sets the HTTP port.
   */
  public HttpClient port(Env env, NumberValue port) {
    PhpTypes.assertNotNull(env, port, "Port to Vertx\\Http\\HttpClient::port() must be an integer.");
    client.setPort(port.toInt());
    return this;
  }

  /**
   * Returns the max connection pool size.
   */
  public int maxPoolSize(Env env) {
    return client.getMaxPoolSize();
  }

  /**
   * Sets the max connection pool size.
   */
  public HttpClient maxPoolSize(Env env, NumberValue size) {
    PhpTypes.assertNotNull(env, size, "Size to Vertx\\Http\\HttpClient::maxPoolSize() must be an integer.");
    client.setMaxPoolSize(size.toInt());
    return this;
  }

  /**
   * Returns the connection timeout.
   */
  public int connectTimeout(Env env) {
    return client.getConnectTimeout();
  }

  /**
   * Sets the connection timeout.
   */
  public HttpClient connectTimeout(Env env, NumberValue timeout) {
    PhpTypes.assertNotNull(env, timeout, "Timeout to Vertx\\Http\\HttpClient::connectTimeout() must be an integer.");
    client.setConnectTimeout(timeout.toInt());
    return this;
  }

  /**
   * Indicates whether verify host is on.
   */
  public boolean verifyHost(Env env) {
    return client.isVerifyHost();
  }

  /**
   * Sets verify host.
   */
  public HttpClient verifyHost(Env env, BooleanValue verifyHost) {
    PhpTypes.assertNotNull(env, verifyHost, "Value to Vertx\\Http\\HttpClient::verifyHost() must be a boolean.");
    client.setVerifyHost(verifyHost.toBoolean());
    return this;
  }

  /**
   * Connects to the server.
   */
  public HttpClientRequest connect(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::connect() must be callable.");
    return new HttpClientRequest(client.connect(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Attempts to connect a websocket to the uri.
   */
  public HttpClient connectWebsocket(Env env, StringValue uri, Value handler) {
    // TODO This method needs to be able to support other HttpClient.connectWebsocket() arguments.
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::connectWebsocket() must be callable.");
    client.connectWebsocket(uri.toString(), new Handler<org.vertx.java.core.http.WebSocket>(env, PhpTypes.toCallable(handler), new ResultModifier<org.vertx.java.core.http.WebSocket, WebSocket>() {
      @Override
      public WebSocket modify(org.vertx.java.core.http.WebSocket socket) {
        return new WebSocket(socket);
      }
    }));
    return this;
  }

  /**
   * Executes a request.
   */
  public HttpClientRequest request(Env env, StringValue method, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::request() must be callable.");
    return new HttpClientRequest(client.request(method.toString(), uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a GET request.
   */
  public HttpClientRequest get(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::get() must be callable.");
    return new HttpClientRequest(client.get(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a GET request.
   */
  public HttpClient getNow(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::getNow() must be callable.");
    client.getNow(uri.toString(), createResponseHandler(env, handler));
    return this;
  }

  /**
   * Executes a PUT request.
   */
  public HttpClientRequest put(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::put() must be callable.");
    return new HttpClientRequest(client.put(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a POST request.
   */
  public HttpClientRequest post(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::post() must be callable.");
    return new HttpClientRequest(client.post(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a DELETE request.
   */
  public HttpClientRequest delete(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::delete() must be callable.");
    return new HttpClientRequest(client.delete(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a HEAD request.
   */
  public HttpClientRequest head(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::head() must be callable.");
    return new HttpClientRequest(client.head(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a PATCH request.
   */
  public HttpClientRequest patch(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::patch() must be callable.");
    return new HttpClientRequest(client.patch(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes a TRACE request.
   */
  public HttpClientRequest trace(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::trace() must be callable.");
    return new HttpClientRequest(client.trace(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Executes an OPTIONS request.
   */
  public HttpClientRequest options(Env env, StringValue uri, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::options() must be callable.");
    return new HttpClientRequest(client.options(uri.toString(), createResponseHandler(env, handler)));
  }

  /**
   * Creates a client response handler.
   */
  private Handler<org.vertx.java.core.http.HttpClientResponse> createResponseHandler(Env env, Value handler) {
    return new Handler<org.vertx.java.core.http.HttpClientResponse>(env, PhpTypes.toCallable(handler),
        new ResultModifier<org.vertx.java.core.http.HttpClientResponse, HttpClientResponse>() {
          @Override
          public HttpClientResponse modify(org.vertx.java.core.http.HttpClientResponse response) {
            return new HttpClientResponse(response);
          }
        });
  }

  /**
   * Sets the client exception handler.
   */
  public HttpClient exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\HttpClient::exceptionHandler() must be callable.");
    client.exceptionHandler(HandlerFactory.createExceptionHandler(env, handler));
    return this;
  }

  /**
   * Closes the client.
   */
  public void close(Env env) {
    client.close();
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpClient";
  }

}

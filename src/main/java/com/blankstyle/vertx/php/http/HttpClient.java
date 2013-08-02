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
package com.blankstyle.vertx.php.http;

import com.blankstyle.vertx.php.TCPClient;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.ArgumentModifier;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpClient.
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
  public String getHost(Env env) {
    return client.getHost();
  }

  /**
   * Sets the HTTP host.
   */
  public HttpClient setHost(Env env, StringValue host) {
    client.setHost(host.toString());
    return this;
  }

  /**
   * Returns the HTTP port.
   */
  public int getPort(Env env) {
    return client.getPort();
  }

  /**
   * Sets the HTTP port.
   */
  public HttpClient setPort(Env env, NumberValue port) {
    client.setPort(port.toInt());
    return this;
  }

  /**
   * Returns the max connection pool size.
   */
  public int getMaxPoolSize(Env env) {
    return client.getMaxPoolSize();
  }

  /**
   * Sets the max connection pool size.
   */
  public HttpClient setMaxPoolSize(Env env, NumberValue size) {
    client.setMaxPoolSize(size.toInt());
    return this;
  }

  /**
   * Returns the connection timeout.
   */
  public int getConnectionTimeout(Env env) {
    return client.getConnectTimeout();
  }

  /**
   * Sets the connection timeout.
   */
  public HttpClient setConnectionTimeout(Env env, NumberValue timeout) {
    client.setConnectTimeout(timeout.toInt());
    return this;
  }

  /**
   * Indicates whether HTTP keepalive is on.
   */
  public boolean isKeepAlive(Env env) {
    return client.isKeepAlive();
  }

  /**
   * Sets the client keepalive.
   */
  public HttpClient setKeepAlive(Env env, BooleanValue keepAlive) {
    client.setKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Indicates whether verify host is on.
   */
  public boolean isVerifyHost(Env env) {
    return client.isVerifyHost();
  }

  /**
   * Sets verify host.
   */
  public HttpClient setVerifyHost(Env env, BooleanValue verifyHost) {
    client.setVerifyHost(verifyHost.toBoolean());
    return this;
  }

  /**
   * Connects to the server.
   */
  public HttpClient connect(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::connect() must be callable.");
    }
    client.connect(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a request.
   */
  public HttpClient request(Env env, StringValue method, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::request() must be callable.");
    }
    client.request(method.toString(), uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient get(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::get() must be callable.");
    }
    client.get(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient getNow(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::getNow() must be callable.");
    }
    client.getNow(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a PUT request.
   */
  public HttpClient put(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::put() must be callable.");
    }
    client.put(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a POST request.
   */
  public HttpClient post(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::post() must be callable.");
    }
    client.post(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a DELETE request.
   */
  public HttpClient delete(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::delete() must be callable.");
    }
    client.delete(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a HEAD request.
   */
  public HttpClient head(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::head() must be callable.");
    }
    client.head(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a PATCH request.
   */
  public HttpClient patch(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::patch() must be callable.");
    }
    client.patch(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes a TRACE request.
   */
  public HttpClient trace(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::trace() must be callable.");
    }
    client.trace(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Executes an OPTIONS request.
   */
  public HttpClient options(Env env, StringValue uri, Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::options() must be callable.");
    }
    client.options(uri.toString(), createResponseHandler(env, (Callback) handler));
    return this;
  }

  /**
   * Creates a client response handler.
   */
  private Handler<org.vertx.java.core.http.HttpClientResponse> createResponseHandler(Env env, Callback handler) {
    return new Handler<org.vertx.java.core.http.HttpClientResponse>(env, handler, new ArgumentModifier<org.vertx.java.core.http.HttpClientResponse, HttpClientResponse>() {
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
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to HttpClient::exceptionHandler() must be callable.");
    }
    client.exceptionHandler(new Handler<Throwable>(env, (Callback) handler));
    return this;
  }

  /**
   * Closes the client.
   */
  public void close(Env env) {
    client.close();
  }

}

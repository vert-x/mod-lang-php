package com.blankstyle.vertx.php.http;

import org.vertx.java.core.http.HttpClientResponse;

import com.blankstyle.vertx.php.TCPClient;
import com.blankstyle.vertx.php.Handler;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x HttpClient.
 */
public class HttpClient extends TCPClient<org.vertx.java.core.http.HttpClient> {

  private org.vertx.java.core.http.HttpClient client;

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
  public HttpClient connect(final Env env, StringValue uri, final Callback handler) {
    client.connect(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a request.
   */
  public HttpClient request(final Env env, StringValue method, StringValue uri, final Callback handler) {
    client.request(method.toString(), uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient get(final Env env, StringValue uri, final Callback handler) {
    client.get(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient getNow(final Env env, StringValue uri, final Callback handler) {
    client.getNow(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a PUT request.
   */
  public HttpClient put(final Env env, StringValue uri, final Callback handler) {
    client.put(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a POST request.
   */
  public HttpClient post(final Env env, StringValue uri, final Callback handler) {
    client.post(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a DELETE request.
   */
  public HttpClient delete(final Env env, StringValue uri, final Callback handler) {
    client.delete(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a HEAD request.
   */
  public HttpClient head(final Env env, StringValue uri, final Callback handler) {
    client.head(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a PATCH request.
   */
  public HttpClient patch(final Env env, StringValue uri, final Callback handler) {
    client.patch(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes a TRACE request.
   */
  public HttpClient trace(final Env env, StringValue uri, final Callback handler) {
    client.trace(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Executes an OPTIONS request.
   */
  public HttpClient options(final Env env, StringValue uri, final Callback handler) {
    client.options(uri.toString(), new Handler<HttpClientResponse>(env, handler));
    return this;
  }

  /**
   * Sets the client exception handler.
   */
  public HttpClient exceptionHandler(final Env env, final Callback handler) {
    client.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

  /**
   * Closes the client.
   */
  public void close(Env env) {
    client.close();
  }

}

package com.blankstyle.vertx.php.http;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpClientResponse;

import com.blankstyle.vertx.php.TCPClient;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;

public class HttpClient extends TCPClient<org.vertx.java.core.http.HttpClient> {

  private org.vertx.java.core.http.HttpClient client;

  public HttpClient(org.vertx.java.core.http.HttpClient client) {
    super(client);
  }

  public HttpClient(Env env, org.vertx.java.core.http.HttpClient client) {
    super(client);
  }

  public String getHost(Env env) {
    return client.getHost();
  }

  public HttpClient setHost(Env env, StringValue host) {
    client.setHost(host.toString());
    return this;
  }

  public int getPort(Env env) {
    return client.getPort();
  }

  public HttpClient setPort(Env env, NumberValue port) {
    client.setPort(port.toInt());
    return this;
  }

  public boolean isKeepAlive(Env env) {
    return client.isKeepAlive();
  }

  public boolean isVerifyHost(Env env) {
    return client.isVerifyHost();
  }

  public HttpClient connect(final Env env, StringValue uri, final Callback handler) {
    client.connect(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a request.
   */
  public HttpClient request(final Env env, StringValue method, StringValue uri, final Callback handler) {
    client.request(method.toString(), uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient get(final Env env, StringValue uri, final Callback handler) {
    client.get(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a GET request.
   */
  public HttpClient getNow(final Env env, StringValue uri, final Callback handler) {
    client.getNow(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a PUT request.
   */
  public HttpClient put(final Env env, StringValue uri, final Callback handler) {
    client.put(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a POST request.
   */
  public HttpClient post(final Env env, StringValue uri, final Callback handler) {
    client.post(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Executes a DELETE request.
   */
  public HttpClient delete(final Env env, StringValue uri, final Callback handler) {
    client.delete(uri.toString(), new Handler<HttpClientResponse>() {
      @Override
      public void handle(HttpClientResponse response) {
        handler.call(env, env.wrapJava(response));
      }
    });
    return this;
  }

  /**
   * Closes the client.
   */
  public void close(Env env) {
    client.close();
  }

}

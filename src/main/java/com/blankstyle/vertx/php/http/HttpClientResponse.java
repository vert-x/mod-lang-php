package com.blankstyle.vertx.php.http;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ExceptionSupport;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpClientResponse.
 *
 * @author Jordan Halterman
 */
public class HttpClientResponse implements ReadStream<HttpClientResponse>, ExceptionSupport<HttpClientResponse> {

  private org.vertx.java.core.http.HttpClientResponse response;

  public HttpClientResponse(org.vertx.java.core.http.HttpClientResponse response) {
    this.response = response;
  }

  public HttpClientResponse(Env env, org.vertx.java.core.http.HttpClientResponse response) {
    this.response = response;
  }

  public Value __getField(Env env, Value name) {
    return env.wrapJava(this).callMethod(env, env.createString(name.toString()));
  }

  @Override
  public HttpClientResponse pause(Env env) {
    response.pause();
    return this;
  }

  @Override
  public HttpClientResponse resume(Env env) {
    response.resume();
    return this;
  }

  public Value cookies(Env env) {
    return env.wrapJava(response.cookies());
  }

  public Value headers(Env env) {
    return env.wrapJava(response.headers());
  }

  public Value trailers(Env env) {
    return env.wrapJava(response.trailers());
  }

  public Value netSocket(Env env) {
    return env.wrapJava(response.netSocket());
  }

  public Value statusCode(Env env) {
    return env.wrapJava(response.statusCode());
  }

  public Value statusMessage(Env env) {
    return env.wrapJava(response.statusMessage());
  }

  @Override
  public HttpClientResponse dataHandler(Env env, Callback handler) {
    response.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  public HttpClientResponse bodyHandler(Env env, Callback handler) {
    response.bodyHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  @Override
  public HttpClientResponse endHandler(Env env, Callback handler) {
    response.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  @Override
  public HttpClientResponse exceptionHandler(Env env, Callback handler) {
    response.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

}

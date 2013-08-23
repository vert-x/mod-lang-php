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

import io.vertx.lang.php.Gettable;
import io.vertx.lang.php.MultiMapArray;
import io.vertx.lang.php.net.NetSocket;
import io.vertx.lang.php.streams.ExceptionSupport;
import io.vertx.lang.php.streams.ReadStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpClientResponse.
 * 
 * @author Jordan Halterman
 */
public class HttpClientResponse implements ReadStream<HttpClientResponse>, ExceptionSupport<HttpClientResponse>,
    Gettable {

  private org.vertx.java.core.http.HttpClientResponse response;

  public HttpClientResponse(org.vertx.java.core.http.HttpClientResponse response) {
    this.response = response;
  }

  public HttpClientResponse(Env env, org.vertx.java.core.http.HttpClientResponse response) {
    this.response = response;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
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
    return env.wrapJava(new MultiMapArray(response.headers()));
  }

  public Value trailers(Env env) {
    return env.wrapJava(new MultiMapArray(response.trailers()));
  }

  public Value netSocket(Env env) {
    return env.wrapJava(new NetSocket(response.netSocket()));
  }

  public Value statusCode(Env env) {
    return env.wrapJava(response.statusCode());
  }

  public Value statusMessage(Env env) {
    return env.wrapJava(response.statusMessage());
  }

  @Override
  public HttpClientResponse dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpClientResponse::dataHandler() must be callable.");
    response.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  public HttpClientResponse bodyHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpClientResponse::bodyHandler() must be callable.");
    response.bodyHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  @Override
  public HttpClientResponse endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpClientResponse::endHandler() must be callable.");
    response.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  @Override
  public HttpClientResponse exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpClientResponse::exceptionHandler() must be callable.");
    response.exceptionHandler(HandlerFactory.createExceptionHandler(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpClientResponse";
  }

}

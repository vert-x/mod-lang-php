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
import io.vertx.lang.php.Settable;
import io.vertx.lang.php.buffer.Buffer;
import io.vertx.lang.php.streams.ExceptionSupport;
import io.vertx.lang.php.streams.WriteStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpServerResponse.
 * 
 * @author Jordan Halterman
 */
public class HttpServerResponse implements WriteStream<HttpServerResponse>, ExceptionSupport<HttpServerResponse>,
    Gettable, Settable {

  private org.vertx.java.core.http.HttpServerResponse response;

  public HttpServerResponse(org.vertx.java.core.http.HttpServerResponse response) {
    this.response = response;
  }

  public HttpServerResponse(Env env, org.vertx.java.core.http.HttpServerResponse response) {
    this.response = response;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  @Override
  public void __setField(Env env, StringValue name, Value value) {
    env.wrapJava(this).callMethod(env, name, value);
  }

  /**
   * Returns the response status code.
   */
  public Value statusCode(Env env) {
    return env.wrapJava(response.getStatusCode());
  }

  /**
   * Sets the response status code.
   */
  public Value statusCode(Env env, NumberValue statusCode) {
    response.setStatusCode(statusCode.toInt());
    return env.wrapJava(this);
  }

  /**
   * Returns the response status message.
   */
  public Value statusMessage(Env env) {
    return env.createString(response.getStatusMessage());
  }

  /**
   * Sets the response status message.
   */
  public HttpServerResponse statusMessage(Env env, StringValue message) {
    response.setStatusMessage(message.toString());
    return this;
  }

  /**
   * Returns response headers.
   */
  public Value headers(Env env) {
    return env.wrapJava(new MultiMapArray(response.headers()));
  }

  /**
   * Puts an HTTP header.
   */
  public HttpServerResponse putHeader(Env env, StringValue name, Value value) {
    response.putHeader(name.toString(), value.toString());
    return this;
  }

  /**
   * Returns response trailers.
   */
  public Value trailers(Env env) {
    return env.wrapJava(new MultiMapArray(response.trailers()));
  }

  /**
   * Puts an HTTP trailer.
   */
  public HttpServerResponse putTrailer(Env env, StringValue name, Value value) {
    response.putTrailer(name.toString(), value.toString());
    return this;
  }

  /**
   * Sends a file to the client.
   */
  public HttpServerResponse sendFile(Env env, StringValue filename) {
    response.sendFile(filename.toString());
    return this;
  }

  @Override
  public HttpServerResponse write(Env env, Value data, @Optional StringValue enc) {
    if (PhpTypes.notNull(enc) && !enc.isDefault()) {
      if (data.isObject()) {
        response.write(((Buffer) data.toJavaObject(env, Buffer.class)).__toVertxBuffer());
      }
      else {
        response.write(data.toString(), enc.toString());
      }
    }
    else {
      if (data.isObject()) {
        response.write(((Buffer) data.toJavaObject(env, Buffer.class)).__toVertxBuffer());
      }
      else {
        response.write(data.toString());
      }
    }
    return this;
  }

  public void end() {
    response.end();
  }

  public void end(Env env) {
    end();
  }

  public void end(Env env, Value data) {
    response.end(data.toString());
  }

  @Override
  public HttpServerResponse drainHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerResponse::drainHandler() must be callable.");
    response.drainHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  public HttpServerResponse closeHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerResponse::closeHandler() must be callable.");
    response.closeHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  /**
   * Gets the chunked value.
   */
  public BooleanValue chunked(Env env) {
    return BooleanValue.create(response.isChunked());
  }

  /**
   * Sets the chunked value.
   */
  public HttpServerResponse chunked(Env env, BooleanValue chunked) {
    PhpTypes.assertNotNull(env, chunked, "Value to Vertx\\Http\\HttpServerResponse::chunked() must be a boolean.");
    response.setChunked(chunked.toBoolean());
    return this;
  }

  @Override
  public HttpServerResponse writeQueueMaxSize(Env env, NumberValue size) {
    PhpTypes.assertNotNull(env, size,
        "Size to Vertx\\Http\\HttpServerResponse::writeQueueMaxSize() must be an integer.");
    response.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(response.writeQueueFull());
  }

  @Override
  public HttpServerResponse exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerResponse::exceptionHandler() must be callable.");
    response.exceptionHandler(HandlerFactory.createExceptionHandler(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpServerResponse";
  }

}

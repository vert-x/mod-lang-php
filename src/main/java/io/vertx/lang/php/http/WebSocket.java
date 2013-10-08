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
import io.vertx.lang.php.streams.ExceptionSupport;
import io.vertx.lang.php.streams.ReadStream;
import io.vertx.lang.php.streams.WriteStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import org.vertx.java.core.http.WebSocketVersion;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x WebSocket.
 * 
 * @author Jordan Halterman
 */
public class WebSocket implements ReadStream<WebSocket>, WriteStream<WebSocket>, ExceptionSupport<WebSocket>, Gettable {

  private org.vertx.java.core.http.WebSocket socket;

  /**
   * Web socket version numbers.
   */
  public static final WebSocketVersion HYBI_00 = WebSocketVersion.HYBI_00;
  public static final WebSocketVersion HYBI_08 = WebSocketVersion.HYBI_08;
  public static final WebSocketVersion RFC6455 = WebSocketVersion.RFC6455;

  public WebSocket(org.vertx.java.core.http.WebSocketBase<?> socket) {
    this.socket = (org.vertx.java.core.http.WebSocket) socket;
  }

  public WebSocket(Env env, org.vertx.java.core.http.WebSocketBase<?> socket) {
    this.socket = (org.vertx.java.core.http.WebSocket) socket;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  @Override
  public WebSocket write(Env env, Value data, StringValue enc) {
    socket.write(new org.vertx.java.core.buffer.Buffer(data.toString()));
    return this;
  }

  /**
   * Writes data to the socket as a binary frame.
   */
  public WebSocket writeBinaryFrame(Env env, Value data) {
    socket.writeBinaryFrame(new org.vertx.java.core.buffer.Buffer(data.toString()));
    return this;
  }

  /**
   * Returns the binary handler ID.
   */
  public StringValue binaryHandlerID(Env env) {
    return env.createString(socket.binaryHandlerID());
  }

  /**
   * Writes data to the socket as a text frame.
   */
  public WebSocket writeTextFrame(Env env, Value data) {
    socket.writeTextFrame(data.toString());
    return this;
  }

  /**
   * Returns the text handler ID.
   */
  public StringValue textHandlerID(Env env) {
    return env.createString(socket.textHandlerID());
  }

  @Override
  public WebSocket drainHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\WebSocket::drainHandler() must be callable.");
    socket.drainHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  @Override
  public WebSocket writeQueueMaxSize(Env env, NumberValue size) {
    socket.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(socket.writeQueueFull());
  }

  @Override
  public WebSocket pause(Env env) {
    socket.pause();
    return this;
  }

  @Override
  public WebSocket resume(Env env) {
    socket.resume();
    return this;
  }

  @Override
  public WebSocket dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\WebSocket::dataHandler() must be callable.");
    socket.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  @Override
  public WebSocket endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\WebSocket::endHandler() must be callable.");
    socket.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  @Override
  public WebSocket exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\WebSocket::exceptionHandler() must be callable.");
    socket.exceptionHandler(HandlerFactory.createExceptionHandler(env, handler));
    return this;
  }

  public Value closeHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\WebSocket::closeHandler() must be callable.");
    socket.closeHandler(HandlerFactory.createVoidHandler(env, handler));
    return env.wrapJava(this);
  }

  /**
   * Closes the socket.
   */
  public void close() {
    socket.close();
  }

  public String toString() {
    return "php:Vertx\\Http\\WebSocket";
  }

}

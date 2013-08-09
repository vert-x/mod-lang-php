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
package com.blankstyle.vertx.php.sockjs;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.blankstyle.vertx.php.util.HandlerFactory;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x SockJSSocket.
 * 
 * @author Jordan Halterman
 */
public class SockJSSocket implements ReadStream<SockJSSocket>, WriteStream<SockJSSocket> {

  private org.vertx.java.core.sockjs.SockJSSocket socket;

  public SockJSSocket(org.vertx.java.core.sockjs.SockJSSocket socket) {
    this.socket = socket;
  }

  public SockJSSocket(Env env, org.vertx.java.core.sockjs.SockJSSocket socket) {
    this.socket = socket;
  }

  @Override
  public SockJSSocket write(Env env, Value data, StringValue enc) {
    socket.write(new Buffer(data.toString()));
    return this;
  }

  @Override
  public SockJSSocket drainHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\SockJS\\SockJSSocket::drainHandler() must be callable.");
    socket.drainHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  @Override
  public SockJSSocket writeQueueMaxSize(Env env, NumberValue size) {
    socket.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(socket.writeQueueFull());
  }

  /**
   * Returns the ID of the write handler.
   */
  public Value writeHandlerID(Env env) {
    return env.wrapJava(socket.writeHandlerID());
  }

  @Override
  public SockJSSocket pause(Env env) {
    socket.pause();
    return this;
  }

  @Override
  public SockJSSocket resume(Env env) {
    socket.resume();
    return this;
  }

  @Override
  public SockJSSocket dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\SockJS\\SockJSSocket::dataHandler() must be callable.");
    socket.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  @Override
  public SockJSSocket endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\SockJS\\SockJSSocket::endHandler() must be callable.");
    socket.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  /**
   * Closes the socket.
   */
  public void close(Env env) {
    socket.close();
  }

  public String toString() {
    return "php:Vertx\\SockJS\\SockJSSocket";
  }

}

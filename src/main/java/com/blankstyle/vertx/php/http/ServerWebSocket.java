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

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x WebSocket.
 *
 * @author Jordan Halterman
 */
public class ServerWebSocket extends WebSocket {

  private org.vertx.java.core.http.ServerWebSocket socket;

  public ServerWebSocket(org.vertx.java.core.http.ServerWebSocket socket) {
    super(socket);
    this.socket = socket;
  }

  public ServerWebSocket(Env env, org.vertx.java.core.http.ServerWebSocket socket) {
    super(env, socket);
    this.socket = socket;
  }

  /**
   * The path the websocket is attempting to connect at
   */
  public StringValue path(Env env) {
    return env.createString(socket.path());
  }

  /**
   * The query string passed on the websocket uri
   */
  public StringValue query(Env env) {
    return env.createString(socket.query());
  }

  /**
   * A map of all headers in the request to upgrade to websocket
   */
  public Value headers(Env env) {
    return env.wrapJava(socket.headers());
  }

  /**
   * Rejects the websocket.
   */
  public Value reject(Env env) {
    socket.reject();
    return env.wrapJava(this);
  }

  public String toString() {
    return "php:Vertx\\Http\\ServerWebSocket";
  }

}

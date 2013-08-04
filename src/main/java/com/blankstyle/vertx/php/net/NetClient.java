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
package com.blankstyle.vertx.php.net;

import org.vertx.java.core.AsyncResult;

import com.blankstyle.vertx.php.AsyncResultHandler;
import com.blankstyle.vertx.php.AsyncResultWrapper;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.TCPClient;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x NetClient.
 *
 * @author Jordan Halterman
 */
public class NetClient extends TCPClient<org.vertx.java.core.net.NetClient> {

  public NetClient(org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  public NetClient(Env env, org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  /**
   * Connects to a server.
   */
  public NetClient connect(Env env, NumberValue port, @Optional StringValue host, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\Net\\NetClient::connect() must be callable.");
    }

    if (PhpTypes.notNull(host)) {
      client.connect(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.net.NetSocket, NetSocket>() {
        @Override
        public NetSocket wrap(org.vertx.java.core.net.NetSocket socket) {
          return new NetSocket(socket);
        }
      }));
    }
    else {
      client.connect(port.toInt(), new Handler<AsyncResult<org.vertx.java.core.net.NetSocket>>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.net.NetSocket, NetSocket>() {
        @Override
        public NetSocket wrap(org.vertx.java.core.net.NetSocket socket) {
          return new NetSocket(socket);
        }
      }));
    }
    return this;
  }

  /**
   * Sets the connect timeout.
   */
  public Value connectTimeout(Env env, @Optional NumberValue timeout) {
    if (PhpTypes.notNull(timeout)) {
      client.setConnectTimeout(timeout.toInt());
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(client.getConnectTimeout());
    }
  }

  /**
   * Sets the reconnect attempts.
   */
  public Value reconnectAttempts(Env env, @Optional NumberValue attempts) {
    if (PhpTypes.notNull(attempts)) {
      client.setReconnectAttempts(attempts.toInt());
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(client.getReconnectAttempts());
    }
  }

  /**
   * Sets the reconnect interval.
   */
  public Value reconnectInterval(Env env, @Optional LongValue interval) {
    if (PhpTypes.notNull(interval)) {
      client.setReconnectInterval(interval.toLong());
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(client.getReconnectInterval());
    }
  }

  /**
   * Closes the client.
   */
  public void close() {
    client.close();
  }

  public String toString() {
    return "php:Vertx\\Net\\NetClient";
  }

}

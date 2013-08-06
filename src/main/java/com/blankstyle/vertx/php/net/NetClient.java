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
      client.connect(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>(env,
          PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.net.NetSocket, NetSocket>() {
            @Override
            public NetSocket wrap(org.vertx.java.core.net.NetSocket socket) {
              return new NetSocket(socket);
            }
          }));
    }
    else {
      client.connect(port.toInt(),
          new Handler<AsyncResult<org.vertx.java.core.net.NetSocket>>(env, PhpTypes.toCallable(handler),
              new AsyncResultWrapper<org.vertx.java.core.net.NetSocket, NetSocket>() {
                @Override
                public NetSocket wrap(org.vertx.java.core.net.NetSocket socket) {
                  return new NetSocket(socket);
                }
              }));
    }
    return this;
  }

  /**
   * Gets the connect timeout.
   */
  public int connectTimeout(Env env) {
    return client.getConnectTimeout();
  }

  /**
   * Sets the connect timeout.
   */
  public NetClient connectTimeout(Env env, NumberValue timeout) {
    PhpTypes.assertNotNull(env, timeout, "Timeout to Vertx\\Net\\NetClient::connectTimeout() must be an integer.");
    client.setConnectTimeout(timeout.toInt());
    return this;
  }

  /**
   * Gets the reconnect attempts.
   */
  public int reconnectAttempts(Env env) {
    return client.getReconnectAttempts();
  }

  /**
   * Sets the reconnect attempts.
   */
  public NetClient reconnectAttempts(Env env, NumberValue attempts) {
    PhpTypes.assertNotNull(env, attempts, "Attempts to Vertx\\Net\\NetClient::reconnectAttempts() must be an integer.");
    client.setReconnectAttempts(attempts.toInt());
    return this;
  }

  /**
   * Gets the reconnect interval.
   */
  public long reconnectInterval(Env env) {
    return client.getReconnectInterval();
  }

  /**
   * Sets the reconnect interval.
   */
  public NetClient reconnectInterval(Env env, LongValue interval) {
    PhpTypes.assertNotNull(env, interval, "Interval to Vertx\\Net\\NetClient::reconnectInterval() must be a long.");
    client.setReconnectInterval(interval.toLong());
    return this;
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

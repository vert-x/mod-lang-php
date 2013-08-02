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

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.TCPClient;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

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
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to NetClient::connect() must be callable.");
    }
    if (host != null && !host.isDefault()) {
      client.connect(port.toInt(), host.toString(), new Handler<AsyncResult<org.vertx.java.core.net.NetSocket>>(env, (Callback) handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.net.NetSocket>, AsyncResult<NetSocket>>() {
        @Override
        public AsyncResult<NetSocket> modify(final AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          return new AsyncResult<NetSocket>() {
            @Override
            public NetSocket result() {
              return new NetSocket(socket.result());
            }
            @Override
            public Throwable cause() {
              return socket.cause();
            }
            @Override
            public boolean succeeded() {
              return socket.succeeded();
            }
            @Override
            public boolean failed() {
              return socket.failed();
            }
          };
        }
      }));
    }
    else {
      client.connect(port.toInt(), new Handler<AsyncResult<org.vertx.java.core.net.NetSocket>>(env, (Callback) handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.net.NetSocket>, AsyncResult<NetSocket>>() {
        @Override
        public AsyncResult<NetSocket> modify(final AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          return new AsyncResult<NetSocket>() {
            @Override
            public NetSocket result() {
              return new NetSocket(socket.result());
            }
            @Override
            public Throwable cause() {
              return socket.cause();
            }
            @Override
            public boolean succeeded() {
              return socket.succeeded();
            }
            @Override
            public boolean failed() {
              return socket.failed();
            }
          };
        }
      }));
    }
    return this;
  }

  /**
   * Returns the connection timeout.
   */
  public int getConnectTimeout(Env env) {
    return client.getConnectTimeout();
  }

  /**
   * Sets the connect timeout.
   */
  public NetClient setConnectTimeout(Env env, NumberValue timeout) {
    client.setConnectTimeout(timeout.toInt());
    return this;
  }

  /**
   * Returns the reconnect attempts.
   */
  public int getReconnectAttempts(Env env) {
    return client.getReconnectAttempts();
  }

  /**
   * Sets the reconnect attempts.
   */
  public NetClient setReconnectAttempts(Env env, NumberValue attempts) {
    client.setReconnectAttempts(attempts.toInt());
    return this;
  }

  /**
   * Returns the reconnect interval.
   */
  public long getReconnectInterval(Env env) {
    return client.getReconnectInterval();
  }

  /**
   * Sets the reconnect interval.
   */
  public NetClient setReconnectInterval(Env env, LongValue interval) {
    client.setReconnectInterval(interval.toLong());
    return this;
  }

  /**
   * Closes the client.
   */
  public void close() {
    client.close();
  }

}

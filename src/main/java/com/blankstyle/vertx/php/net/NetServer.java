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

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.TCPServer;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x NetServer.
 */
public class NetServer extends TCPServer<org.vertx.java.core.net.NetServer> {

  public NetServer(org.vertx.java.core.net.NetServer server) {
    super(server);
  }

  public NetServer(Env env, org.vertx.java.core.net.NetServer server) {
    super(server);
  }

  /**
   * Supplies a connect handler for the server.
   *
   * @param callback A callable PHP item.
   * @return The called server instance.
   */
  public NetServer connectHandler(Env env, Callback handler) {
    if (handler == null || handler.isNull() || !handler.isCallable(env, false, null)) {
      env.error("Argument to NetServer::connectHandler() must be callable.");
    }
    server.connectHandler(new Handler<org.vertx.java.core.net.NetSocket>(env, handler, new ArgumentModifier<org.vertx.java.core.net.NetSocket, NetSocket>() {
      @Override
      public NetSocket modify(org.vertx.java.core.net.NetSocket socket) {
        return new NetSocket(socket);
      }
    }));
    return this;
  }

  /**
   * Instructs the server to start listening on a host and port.
   *
   * @param port The port on which to listen.
   * @param host The host on which to listen. This is an optional
   * argument. If the host is not provided then the server will
   * listen on all available interfaces.
   * @param callback A callback to execute once the server has
   * begun listening. This is an optional argument.
   * @return The called server instance.
   */
  public NetServer listen(Env env, NumberValue port, @Optional Value host, @Optional Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Argument to NetServer::listen() must be callable.");
    }
    if (host != null && !host.isDefault()) {
      if (handler != null && !handler.isNull()) {
        server.listen(port.toInt(), host.toString(), new Handler<AsyncResult<org.vertx.java.core.net.NetServer>>(env, (Callback) handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.net.NetServer>, AsyncResult<NetServer>>() {
          @Override
          public AsyncResult<NetServer> modify(final AsyncResult<org.vertx.java.core.net.NetServer> server) {
            return new AsyncResult<NetServer>() {
              @Override
              public NetServer result() {
                return new NetServer(server.result());
              }
              @Override
              public Throwable cause() {
                return server.cause();
              }
              @Override
              public boolean succeeded() {
                return server.succeeded();
              }
              @Override
              public boolean failed() {
                return server.failed();
              }
            };
          }
        }));
      }
      else {
        server.listen(port.toInt(), host.toString());
      }
    }
    else if (handler != null && !handler.isNull()) {
      server.listen(port.toInt(), new Handler<AsyncResult<org.vertx.java.core.net.NetServer>>(env, (Callback) handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.net.NetServer>, AsyncResult<NetServer>>() {
        @Override
        public AsyncResult<NetServer> modify(final AsyncResult<org.vertx.java.core.net.NetServer> server) {
          return new AsyncResult<NetServer>() {
            @Override
            public NetServer result() {
              return new NetServer(server.result());
            }
            @Override
            public Throwable cause() {
              return server.cause();
            }
            @Override
            public boolean succeeded() {
              return server.succeeded();
            }
            @Override
            public boolean failed() {
              return server.failed();
            }
          };
        }
      }));
    }
    else {
      server.listen(port.toInt());
    }
    return this;
  }

  /**
   * Returns the server port.
   */
  public NumberValue port(Env env) {
    return (NumberValue) Env.toValue(server.port());
  }

  /**
   * Closes the server.
   *
   * @param callback An optional callable PHP item to be invoked when
   * the server is closed.
   */
  public void close(Env env, @Optional Value handler) {
    if (handler == null || handler.isNull()) {
      server.close();
    }
    else {
      server.close(new Handler<AsyncResult<Void>>(env, (Callback) handler));
    }
  }

  public String toString() {
    return "php:Vertx\\Net\\NetServer";
  }

}

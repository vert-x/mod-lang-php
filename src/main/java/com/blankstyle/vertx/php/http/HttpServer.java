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

import com.blankstyle.vertx.php.ResultModifier;
import com.blankstyle.vertx.php.AsyncResultHandler;
import com.blankstyle.vertx.php.AsyncResultWrapper;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.TCPServer;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpServer.
 *
 * @author Jordan Halterman
 */
public class HttpServer extends TCPServer<org.vertx.java.core.http.HttpServer> {

  public HttpServer(org.vertx.java.core.http.HttpServer server) {
    super(server);
  }

  public HttpServer(Env env, org.vertx.java.core.http.HttpServer server) {
    super(server);
  }

  public org.vertx.java.core.http.HttpServer getVertxServer() {
    return server;
  }

  /**
   * Instructs the server to start listening on a host and port.
   *
   * @param port The port on which to listen.
   * @param host The host on which to listen. This is an optional
   * argument. If the host is not provided then the server will
   * listen on all available interfaces.
   * @param handler A PHP callable to execute once the server has
   * begun listening. This is an optional argument.
   * @return The called server instance.
   */
  public HttpServer listen(Env env, NumberValue port, @Optional Value host, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\Http\\HttpServer::listen() must be callable.");
    }

    if (PhpTypes.notNull(host)) {
      if (PhpTypes.isCallable(env, handler)) {
        server.listen(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.http.HttpServer>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.http.HttpServer, HttpServer>() {
          @Override
          public HttpServer wrap(org.vertx.java.core.http.HttpServer server) {
            return new HttpServer(server);
          }
        }));
      }
      else {
        server.listen(port.toInt(), host.toString());
      }
    }
    else if (PhpTypes.isCallable(env, handler)) {
      server.listen(port.toInt(), new AsyncResultHandler<org.vertx.java.core.http.HttpServer>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.http.HttpServer, HttpServer>() {
        @Override
        public HttpServer wrap(org.vertx.java.core.http.HttpServer server) {
          return new HttpServer(server);
        }
      }));
    }
    else {
      server.listen(port.toInt());
    }
    return this;
  }

  /**
   * Creates or gets the server request handler.
   *
   * @param handler An optional handler. If no handler is provided
   * then the current request handler will be returned.
   */
  public Value requestHandler(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\Http\\HttpServer::requestHandler() must be callable.");
    }

    if (PhpTypes.isCallable(env, handler)) {
      server.requestHandler(new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler), new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
        @Override
        public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
          return new HttpServerRequest(request);
        }
      }));
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(server.requestHandler());
    }
  }

  /**
   * Creates or gets the server route handler.
   */
  public Value routeHandler(final Env env, @Optional final RouteMatcher matcher) {
    if (matcher != null) {
      server.requestHandler(new org.vertx.java.core.Handler<org.vertx.java.core.http.HttpServerRequest>() {
        @Override
        public void handle(org.vertx.java.core.http.HttpServerRequest request) {
          matcher.handle(env, request);
        }
      });
      return env.wrapJava(this);
    }
    else {
      return requestHandler(env, null);
    }
  }

  /**
   * Creates or gets the server websocket handler.
   */
  public Value websocketHandler(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\Http\\HttpServer::websocketHandler() must be callable.");
    }

    if (PhpTypes.isCallable(env, handler)) {
      server.websocketHandler(new Handler<org.vertx.java.core.http.ServerWebSocket>(env, PhpTypes.toCallable(handler), new ResultModifier<org.vertx.java.core.http.ServerWebSocket, ServerWebSocket>() {
        @Override
        public ServerWebSocket modify(org.vertx.java.core.http.ServerWebSocket socket) {
          return new ServerWebSocket(socket);
        }
      }));
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(server.websocketHandler());
    }
  }

  /**
   * Closes the server connection.
   */
  public void close() {
    server.close();
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpServer";
  }

}

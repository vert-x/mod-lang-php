package com.blankstyle.vertx.php.http;

import org.vertx.java.core.AsyncResult;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.TCPServer;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpServer/
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
   * @param callback A callback to execute once the server has
   * begun listening. This is an optional argument.
   * @return The called server instance.
   */
  public HttpServer listen(final Env env, final NumberValue port, @Optional final Value host, @Optional final Callback handler) {
    if (host != null && !host.isDefault()) {
      if (handler != null && !handler.isDefault()) {
        server.listen(port.toInt(), host.toString(), new Handler<AsyncResult<org.vertx.java.core.http.HttpServer>>(env, handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.http.HttpServer>, AsyncResult<HttpServer>>() {
          @Override
          public AsyncResult<HttpServer> modify(final AsyncResult<org.vertx.java.core.http.HttpServer> server) {
            return new AsyncResult<HttpServer>() {
              @Override
              public HttpServer result() {
                return new HttpServer(server.result());
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
    else if (handler != null && !handler.isDefault()) {
      server.listen(port.toInt(), new Handler<AsyncResult<org.vertx.java.core.http.HttpServer>>(env, handler, new ArgumentModifier<AsyncResult<org.vertx.java.core.http.HttpServer>, AsyncResult<HttpServer>>() {
        @Override
        public AsyncResult<HttpServer> modify(final AsyncResult<org.vertx.java.core.http.HttpServer> server) {
          return new AsyncResult<HttpServer>() {
            @Override
            public HttpServer result() {
              return new HttpServer(server.result());
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
   * Creates or gets the server request handler.
   */
  public Value requestHandler(final Env env, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      if (!handler.isCallable(env, true, null)) {
        env.error("Argument to HttpServer::requestHandler() must be callable.");
      }
      server.requestHandler(new Handler<org.vertx.java.core.http.HttpServerRequest>(env, handler, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
   * Creates or gets the server websocket handler.
   */
  public Value websocketHandler(final Env env, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      if (!handler.isCallable(env, true, null)) {
        env.error("Argument to HttpServer::websocketHandler() must be callable.");
      }
      server.websocketHandler(new Handler<org.vertx.java.core.http.ServerWebSocket>(env, handler, new ArgumentModifier<org.vertx.java.core.http.ServerWebSocket, ServerWebSocket>() {
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

}

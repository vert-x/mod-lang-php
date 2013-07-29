package com.blankstyle.vertx.php.http;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.ServerWebSocket;

import com.blankstyle.vertx.php.TCPServer;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

public class HttpServer extends TCPServer<org.vertx.java.core.http.HttpServer> {

  public HttpServer(org.vertx.java.core.http.HttpServer server) {
    super(server);
  }

  public HttpServer(Env env, org.vertx.java.core.http.HttpServer server) {
    super(server);
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
  public HttpServer listen(final Env env, final NumberValue port, @Optional final Value host, @Optional final Value callback) {
    if (host != null && !host.isDefault()) {
      if (callback != null && !callback.isDefault()) {
        server.listen(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.http.HttpServer>() {
          public void handle(AsyncResult<org.vertx.java.core.http.HttpServer> result) {
            callback.call(env, env.wrapJava(result));
          }
        });
      }
      else {
        server.listen(port.toInt(), host.toString());
      }
    }
    else if (callback != null && !callback.isDefault()) {
      server.listen(port.toInt(), new AsyncResultHandler<org.vertx.java.core.http.HttpServer>() {
        public void handle(AsyncResult<org.vertx.java.core.http.HttpServer> result) {
          callback.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      server.listen(port.toInt());
    }
    return this;
  }

  /**
   * Creates or gets the server request handler.
   */
  public Value requestHandler(final Env env, @Optional final Value handler) {
    if (handler != null && !handler.isDefault()) {
      if (!handler.isCallable(env, true, null)) {
        env.error("Argument to HttpServer::requestHandler() must be callable.");
      }
      server.requestHandler(new Handler<HttpServerRequest>() {
        public void handle(HttpServerRequest request) {
          handler.call(env, env.wrapJava(request));
        }
      });
      return env.wrapJava(this);
    }
    else {
      return env.wrapJava(server.requestHandler());
    }
  }

  /**
   * Creates or gets the server websocket handler.
   */
  public Value websocketHandler(final Env env, @Optional final Value handler) {
    if (handler != null && !handler.isDefault()) {
      if (!handler.isCallable(env, true, null)) {
        env.error("Argument to HttpServer::websocketHandler() must be callable.");
      }
      server.websocketHandler(new Handler<ServerWebSocket>() {
        public void handle(ServerWebSocket socket) {
          handler.call(env, env.wrapJava(socket));
        }
      });
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

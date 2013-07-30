package com.blankstyle.vertx.php.net;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;

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
  public NetServer connectHandler(final Env env, final Callback handler) {
    server.connectHandler(new Handler<org.vertx.java.core.net.NetSocket>() {
      public void handle(org.vertx.java.core.net.NetSocket socket) {
        handler.toCallable(env, false).call(env, env.wrapJava(new NetSocket(socket)));
      }
    });
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
  public NetServer listen(final Env env, final NumberValue port, @Optional final Value host, @Optional final Callback handler) {
    if (host != null && !host.isDefault()) {
      if (handler != null && !handler.isDefault()) {
        server.listen(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetServer>() {
          public void handle(AsyncResult<org.vertx.java.core.net.NetServer> result) {
            handler.call(env, env.wrapJava(result));
          }
        });
      }
      else {
        server.listen(port.toInt(), host.toString());
      }
    }
    else if (handler != null && !handler.isDefault()) {
      server.listen(port.toInt(), new AsyncResultHandler<org.vertx.java.core.net.NetServer>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetServer> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
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
  public void close(final Env env, @Optional final Callback handler) {
    if (handler == null) {
      server.close();
    }
    else {
      server.close(new AsyncResultHandler<Void>() {
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
  }

}

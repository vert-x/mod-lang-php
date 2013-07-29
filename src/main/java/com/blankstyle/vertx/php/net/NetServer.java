package com.blankstyle.vertx.php.net;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x NetServer.
 */
public class NetServer {

  private org.vertx.java.core.net.NetServer server;

  public NetServer(org.vertx.java.core.net.NetServer server) {
    this.server = server;
  }

  /**
   * Supplies a connect handler for the server.
   *
   * @param callback A callable PHP item.
   * @return The called server instance.
   */
  public NetServer connectHandler(final Env env, final Callback callback) {
    server.connectHandler(new Handler<org.vertx.java.core.net.NetSocket>() {
      public void handle(org.vertx.java.core.net.NetSocket socket) {
        callback.toCallable(env, false).call(env, env.wrapJava(new NetSocket(socket)));
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
  public NetServer listen(final Env env, final NumberValue port, @Optional final Value host, @Optional final Value callback) {
    if (host.isDefault()) {
      server.listen(port.toInt());
    }
    else if (host.isCallable(env, false, null)) {
      server.listen(port.toInt(), new AsyncResultHandler<org.vertx.java.core.net.NetServer>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetServer> result) {
          host.call(env, env.wrapJava(result));
        }
      });
    }
    else if (callback.isDefault()) {
      server.listen(port.toInt(), host.toString());
    }
    else {
      server.listen(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetServer>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetServer> result) {
          callback.call(env, env.wrapJava(result));
        }
      });
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
  public void close(final Env env, @Optional final Callback callback) {
    if (callback == null) {
      server.close();
    }
    else {
      server.close(new AsyncResultHandler<Void>() {
        public void handle(AsyncResult<Void> result) {
          callback.call(env, env.wrapJava(result));
        }
      });
    }
  }

}

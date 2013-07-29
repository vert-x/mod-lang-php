package com.blankstyle.vertx.php.net;

import org.vertx.java.core.Handler;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x NetSocket.
 */
public class NetSocket {

  org.vertx.java.core.net.NetSocket socket;

  public NetSocket(org.vertx.java.core.net.NetSocket socket) {
    this.socket = socket;
  }

  /**
   * Returns the local socket address.
   */
  public Value localAddress(Env env) {
    return env.createString(socket.localAddress().toString());
  }

  /**
   * Returns the remote socket address.
   */
  public Value remoteAddress(Env env) {
    return env.createString(socket.remoteAddress().toString());
  }

  public NetSocket dataHandler(final Env env, final Callback callback) {
    socket.dataHandler(new Handler<org.vertx.java.core.buffer.Buffer>() {
      public void handle(org.vertx.java.core.buffer.Buffer data) {
        callback.call(env, env.wrapJava(data));
      }
    });
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public NetSocket write(Env env, Value value) {
    socket.write(value.toJavaString());
    return this;
  }

  /**
   * Sends a file through the socket.
   */
  public NetSocket sendFile(Env env, StringValue filename) {
    return this;
  }

  /**
   * Sets the socket close handler.
   */
  public void closeHandler(final Env env, final Callback callback) {
    socket.closeHandler(new Handler<Void>() {
      public void handle(Void result) {
        callback.call(env, env.wrapJava(result));
      }
    });
  }

  /**
   * Closes the socket.
   */
  public void close(Env env) {
    socket.close();
  }

}

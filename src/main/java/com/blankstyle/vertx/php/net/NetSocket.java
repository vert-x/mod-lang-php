package com.blankstyle.vertx.php.net;

import org.vertx.java.core.Handler;

import com.caucho.quercus.env.BooleanValue;
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

  /**
   * Sets the socket data handler.
   */
  public NetSocket dataHandler(final Env env, final Callback handler) {
    socket.dataHandler(new Handler<org.vertx.java.core.buffer.Buffer>() {
      public void handle(org.vertx.java.core.buffer.Buffer data) {
        handler.call(env, env.wrapJava(data));
      }
    });
    return this;
  }

  /**
   * Pauses producing on the socket.
   */
  public NetSocket pause(Env env) {
    socket.pause();
    return this;
  }

  /**
   * Resumes producing on the socket.
   */
  public NetSocket resume(Env env) {
    socket.resume();
    return this;
  }

  /**
   * Sets the socket end handler.
   */
  public NetSocket endHandler(final Env env, final Callback handler) {
    socket.endHandler(new Handler<Void>() {
      public void handle(Void result) {
        handler.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  /**
   * Sets the socket drain handler.
   */
  public NetSocket drainHandler(final Env env, final Callback handler) {
    socket.drainHandler(new Handler<Void>() {
      public void handle(Void result) {
        handler.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  /**
   * Sets the max write queue size.
   */
  public NetSocket setWriteQueueMaxSize(Env env, Value value) {
    socket.setWriteQueueMaxSize(value.toJavaInteger());
    return this;
  }

  /**
   * Indicates whether the write queue is full.
   */
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(socket.writeQueueFull());
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
  public void closeHandler(final Env env, final Callback handler) {
    socket.closeHandler(new Handler<Void>() {
      public void handle(Void result) {
        handler.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
  }

  /**
   * Closes the socket.
   */
  public void close(Env env) {
    socket.close();
  }

  /**
   * Sets the socket exception handler callback.
   */
  public NetSocket exceptionHandler(final Env env, final Callback handler) {
    socket.exceptionHandler(new Handler<Throwable>() {
      public void handle(Throwable e) {
        handler.toCallable(env, false).call(env, env.wrapJava(e));
      }
    });
    return this;
  }

}

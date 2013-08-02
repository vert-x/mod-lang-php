package com.blankstyle.vertx.php.net;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ExceptionSupport;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x NetSocket.
 */
public class NetSocket implements ReadStream<NetSocket>, WriteStream<NetSocket>, ExceptionSupport<NetSocket> {

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
  public NetSocket dataHandler(Env env, Callback handler) {
    socket.dataHandler(new Handler<org.vertx.java.core.buffer.Buffer>(env, handler));
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
  public NetSocket endHandler(Env env, Callback handler) {
    socket.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  /**
   * Sets the socket drain handler.
   */
  public NetSocket drainHandler(Env env, Callback handler) {
    socket.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  /**
   * Sets the max write queue size.
   */
  public NetSocket setWriteQueueMaxSize(Env env, NumberValue value) {
    socket.setWriteQueueMaxSize(value.toInt());
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
  public NetSocket write(Env env, Value value, StringValue enc) {
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
  public void closeHandler(Env env, Callback handler) {
    socket.closeHandler(new Handler<Void>(env, handler));
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
  public NetSocket exceptionHandler(Env env, Callback handler) {
    socket.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

}

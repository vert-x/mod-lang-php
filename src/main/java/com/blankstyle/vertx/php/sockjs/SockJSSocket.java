package com.blankstyle.vertx.php.sockjs;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x SockJSSocket.
 *
 * @author JordanHalterman
 */
public class SockJSSocket implements ReadStream<SockJSSocket>, WriteStream<SockJSSocket> {

  private org.vertx.java.core.sockjs.SockJSSocket socket;

  public SockJSSocket(org.vertx.java.core.sockjs.SockJSSocket socket) {
    this.socket = socket;
  }

  public SockJSSocket(Env env, org.vertx.java.core.sockjs.SockJSSocket socket) {
    this.socket = socket;
  }

  @Override
  public SockJSSocket write(Env env, Value data, StringValue enc) {
    socket.write(new Buffer(data.toString()));
    return this;
  }

  @Override
  public SockJSSocket drainHandler(Env env, Callback handler) {
    socket.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  @Override
  public SockJSSocket drainHandler(org.vertx.java.core.Handler<Void> handler) {
    socket.drainHandler(handler);
    return this;
  }

  @Override
  public SockJSSocket writeQueueMaxSize(Env env, NumberValue size) {
    socket.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(socket.writeQueueFull());
  }

  /**
   * Returns the ID of the write handler.
   */
  public Value writeHandlerID(Env env) {
    return env.wrapJava(socket.writeHandlerID());
  }

  @Override
  public SockJSSocket pause(Env env) {
    socket.pause();
    return this;
  }

  @Override
  public SockJSSocket resume(Env env) {
    socket.resume();
    return this;
  }

  @Override
  public SockJSSocket dataHandler(Env env, Callback handler) {
    socket.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  @Override
  public SockJSSocket dataHandler(org.vertx.java.core.Handler<Buffer> handler) {
    socket.dataHandler(handler);
    return this;
  }

  @Override
  public SockJSSocket endHandler(Env env, Callback handler) {
    socket.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  /**
   * Closes the socket.
   */
  public void close(Env env) {
    socket.close();
  }

}

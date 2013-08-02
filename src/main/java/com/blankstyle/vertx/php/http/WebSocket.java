package com.blankstyle.vertx.php.http;

import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.WebSocketVersion;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ExceptionSupport;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x WebSocket.
 * 
 * @author Jordan Halterman
 */
public class WebSocket implements ReadStream<WebSocket>, WriteStream<WebSocket>, ExceptionSupport<WebSocket> {

  private org.vertx.java.core.http.WebSocket socket;

  /**
   * Web socket version numbers.
   */
  public static final WebSocketVersion HYBI_00 = WebSocketVersion.HYBI_00;
  public static final WebSocketVersion HYBI_08 = WebSocketVersion.HYBI_08;
  public static final WebSocketVersion RFC6455 = WebSocketVersion.RFC6455;

  public WebSocket(org.vertx.java.core.http.WebSocketBase<?> socket) {
    this.socket = (org.vertx.java.core.http.WebSocket) socket;
  }

  public WebSocket(Env env, org.vertx.java.core.http.WebSocketBase<?> socket) {
    this.socket = (org.vertx.java.core.http.WebSocket) socket;
  }

  @Override
  public WebSocket write(Env env, Value data, StringValue enc) {
    socket.write(new Buffer(data.toString()));
    return this;
  }

  /**
   * Writes data to the socket as a binary frame.
   */
  public WebSocket writeBinaryFrame(Env env, Value data) {
    socket.writeBinaryFrame(new Buffer(data.toString()));
    return this;
  }

  /**
   * Returns the binary handler ID.
   */
  public StringValue binaryHandlerID(Env env) {
    return env.createString(socket.binaryHandlerID());
  }

  /**
   * Writes data to the socket as a text frame.
   */
  public WebSocket writeTextFrame(Env env, Value data) {
    socket.writeTextFrame(data.toString());
    return this;
  }

  /**
   * Returns the text handler ID.
   */
  public StringValue textHandlerID(Env env) {
    return env.createString(socket.textHandlerID());
  }

  @Override
  public WebSocket drainHandler(Env env, Callback handler) {
    socket.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  @Override
  public WebSocket setWriteQueueMaxSize(Env env, NumberValue size) {
    socket.setWriteQueueMaxSize(size.toInt());
    return this;
  }

  @Override
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(socket.writeQueueFull());
  }

  @Override
  public WebSocket pause(Env env) {
    socket.pause();
    return this;
  }

  @Override
  public WebSocket resume(Env env) {
    socket.resume();
    return this;
  }

  @Override
  public WebSocket dataHandler(Env env, Callback handler) {
    socket.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  @Override
  public WebSocket endHandler(Env env, Callback handler) {
    socket.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  @Override
  public WebSocket exceptionHandler(Env env, Callback handler) {
    socket.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

  public Value closeHandler(Env env, Callback handler) {
    socket.closeHandler(new Handler<Void>(env, handler));
    return env.wrapJava(this);
  }

  /**
   * Closes the socket.
   */
  public void close() {
    socket.close();
  }

  @Override
  public WebSocket drainHandler(org.vertx.java.core.Handler<Void> handler) {
    socket.drainHandler(handler);
    return this;
  }

  @Override
  public WebSocket dataHandler(org.vertx.java.core.Handler<Buffer> handler) {
    socket.dataHandler(handler);
    return this;
  }

}

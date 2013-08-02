package com.blankstyle.vertx.php.file;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x AsyncFile.
 */
public final class AsyncFile implements ReadStream<AsyncFile>, WriteStream<AsyncFile> {

  private org.vertx.java.core.file.AsyncFile file;

  public AsyncFile(org.vertx.java.core.file.AsyncFile file) {
    this.file = file;
  }

  /**
   * Reads from the file.
   */
  public AsyncFile read(Env env, Buffer buffer, NumberValue offset, NumberValue position, NumberValue length, Callback handler) {
    file.read(buffer, offset.toInt(), position.toInt(), length.toInt(), new Handler<AsyncResult<Buffer>>(env, handler));
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(Env env, Buffer buffer, NumberValue position, Callback handler) {
    file.write(buffer, position.toInt(), new Handler<AsyncResult<Void>>(env, handler));
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(Env env, Value data, StringValue enc) {
    file.write(new Buffer(data.toString()));
    return this;
  }

  /**
   * Sets the file data handler.
   */
  public AsyncFile dataHandler(Env env, Callback handler) {
    file.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  /**
   * Sets an internal data handler on the stream.
   */
  public AsyncFile dataHandler(org.vertx.java.core.Handler<Buffer> handler) {
    file.dataHandler(handler);
    return this;
  }

  /**
   * Pauses producing on the file.
   */
  public AsyncFile pause(Env env) {
    file.pause();
    return this;
  }

  /**
   * Resumes producing on the file.
   */
  public AsyncFile resume(Env env) {
    file.resume();
    return this;
  }

  /**
   * Sets the file end handler.
   */
  public AsyncFile endHandler(Env env, Callback handler) {
    file.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  /**
   * Sets the file drain handler.
   */
  public AsyncFile drainHandler(Env env, Callback handler) {
    file.drainHandler(new Handler<Void>(env, handler));
    return this;
  }

  /**
   * Sets an internal drain handler on the stream.
   */
  public AsyncFile drainHandler(org.vertx.java.core.Handler<Void> handler) {
    file.drainHandler(handler);
    return this;
  }

  /**
   * Sets the max write queue size.
   */
  public AsyncFile writeQueueMaxSize(Env env, NumberValue value) {
    file.setWriteQueueMaxSize(value.toJavaInteger());
    return this;
  }

  /**
   * Indicates whether the write queue is full.
   */
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(file.writeQueueFull());
  }

  /**
   * Flushes the file.
   */
  public AsyncFile flush(Env env, @Optional Callback handler) {
    if (handler != null && !handler.isNull()) {
      file.flush(new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      file.flush();
    }
    return this;
  }

  /**
   * Closes the file.
   */
  public void close(Env env, @Optional Callback handler) {
    if (handler != null && !handler.isNull()) {
      file.flush(new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      file.flush();
    }
  }

  /**
   * Sets the socket exception handler callback.
   */
  public AsyncFile exceptionHandler(Env env, Callback handler) {
    file.exceptionHandler(new Handler<Throwable>(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\File\\AsyncFile";
  }

}

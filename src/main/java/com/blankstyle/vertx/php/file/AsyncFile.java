package com.blankstyle.vertx.php.file;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.AsyncResultHandler;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.VoidAsyncResultHandler;
import com.blankstyle.vertx.php.VoidHandler;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.blankstyle.vertx.php.streams.WriteStream;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x AsyncFile.
 *
 * @author Jordan Halterman
 */
public final class AsyncFile implements ReadStream<AsyncFile>, WriteStream<AsyncFile> {

  private org.vertx.java.core.file.AsyncFile file;

  public AsyncFile(org.vertx.java.core.file.AsyncFile file) {
    this.file = file;
  }

  /**
   * Reads from the file.
   */
  public AsyncFile read(Env env, Buffer buffer, NumberValue offset, NumberValue position, NumberValue length, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::read() must be callable.");
    file.read(buffer, offset.toInt(), position.toInt(), length.toInt(), new AsyncResultHandler<Buffer>(env, PhpTypes.toCallable(handler)));
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(Env env, Buffer buffer, NumberValue position, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::write() must be callable.");
    file.write(buffer, position.toInt(), new VoidAsyncResultHandler(env, PhpTypes.toCallable(handler)));
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
  public AsyncFile dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::dataHandler() must be callable.");
    file.dataHandler(new Handler<Buffer>(env, PhpTypes.toCallable(handler)));
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
  public AsyncFile endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::endHandler() must be callable.");
    file.endHandler(new VoidHandler(env, PhpTypes.toCallable(handler)));
    return this;
  }

  /**
   * Sets the file drain handler.
   */
  public AsyncFile drainHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::drainHandler() must be callable.");
    file.drainHandler(new VoidHandler(env, PhpTypes.toCallable(handler)));
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
  public AsyncFile flush(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::flush() must be callable.");
      file.flush(new VoidAsyncResultHandler(env, PhpTypes.toCallable(handler)));
    }
    else {
      file.flush();
    }
    return this;
  }

  /**
   * Closes the file.
   */
  public void close(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::close() must be callable.");
      file.flush(new VoidAsyncResultHandler(env, PhpTypes.toCallable(handler)));
    }
    else {
      file.flush();
    }
  }

  /**
   * Sets the socket exception handler callback.
   */
  public AsyncFile exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::exceptionHandler() must be callable.");
    file.exceptionHandler(new Handler<Throwable>(env, PhpTypes.toCallable(handler)));
    return this;
  }

  public String toString() {
    return "php:Vertx\\File\\AsyncFile";
  }

}

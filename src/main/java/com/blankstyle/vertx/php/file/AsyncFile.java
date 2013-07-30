package com.blankstyle.vertx.php.file;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.net.NetSocket;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x AsyncFile.
 */
public final class AsyncFile {

  private org.vertx.java.core.file.AsyncFile file;

  public AsyncFile(org.vertx.java.core.file.AsyncFile file) {
    this.file = file;
  }

  /**
   * Reads from the file.
   */
  public AsyncFile read(final Env env, Buffer buffer, int offset, int position, int length, final Callback handler) {
    file.read(buffer, offset, position, length, new AsyncResultHandler<Buffer>() {
      @Override
      public void handle(AsyncResult<Buffer> buffer) {
        handler.call(env, env.wrapJava(buffer));
      }
    });
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(final Env env, Buffer buffer, int position, final Callback handler) {
    file.write(buffer, position, new AsyncResultHandler<Void>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        handler.call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  /**
   * Sets the file data handler.
   */
  public AsyncFile dataHandler(final Env env, final Callback callback) {
    file.dataHandler(new Handler<org.vertx.java.core.buffer.Buffer>() {
      public void handle(org.vertx.java.core.buffer.Buffer data) {
        callback.call(env, env.wrapJava(data));
      }
    });
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
  public AsyncFile endHandler(final Env env, final Callback callback) {
    file.endHandler(new Handler<Void>() {
      public void handle(Void result) {
        callback.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  /**
   * Sets the file drain handler.
   */
  public AsyncFile drainHandler(final Env env, final Callback callback) {
    file.drainHandler(new Handler<Void>() {
      public void handle(Void result) {
        callback.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
    return this;
  }

  /**
   * Sets the max write queue size.
   */
  public AsyncFile setWriteQueueMaxSize(Env env, Value value) {
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
  public AsyncFile flush(final Env env, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      file.flush(new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      file.flush();
    }
    return this;
  }

  /**
   * Closes the file.
   */
  public void close(final Env env, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      file.flush(new AsyncResultHandler<Void>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          handler.call(env, env.wrapJava(result));
        }
      });
    }
    else {
      file.flush();
    }
  }

  /**
   * Sets the socket exception handler callback.
   */
  public AsyncFile exceptionHandler(final Env env, final Callback callback) {
    file.exceptionHandler(new Handler<Throwable>() {
      public void handle(Throwable e) {
        callback.toCallable(env, false).call(env, env.wrapJava(e));
      }
    });
    return this;
  }

}

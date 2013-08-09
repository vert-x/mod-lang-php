package com.blankstyle.vertx.php.util;

import org.vertx.java.core.AsyncResult;

import com.blankstyle.vertx.php.AsyncResultHandler;
import com.blankstyle.vertx.php.AsyncResultWrapper;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.ResultModifier;
import com.blankstyle.vertx.php.buffer.Buffer;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A utility class for creating common Vert.x handlers.
 *
 * @author Jordan Halterman
 */
public class HandlerFactory {

  private HandlerFactory() {
  }

  /**
   * Creates a generic handler.
   */
  public static <T> org.vertx.java.core.Handler<T> createGenericHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new Handler<T>(env, PhpTypes.toCallable(handler));
  }

  /**
   * Creates a void result handler.
   */
  public static org.vertx.java.core.Handler<Void> createVoidHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new Handler<Void>(env, PhpTypes.toCallable(handler)) {
      @Override
      public void handle(Void arg) {
        Env env = getEnvironment();
        getCallable().call(env);
      }
    };
  }

  /**
   * Creates a buffer handler.
   */
  public static org.vertx.java.core.Handler<org.vertx.java.core.buffer.Buffer> createBufferHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new Handler<org.vertx.java.core.buffer.Buffer>(env, PhpTypes.toCallable(handler), new ResultModifier<org.vertx.java.core.buffer.Buffer, Buffer>() {
      @Override
      public Buffer modify(org.vertx.java.core.buffer.Buffer buffer) {
        return new Buffer(buffer);
      }
    });
  }

  /**
   * Creates an exception handler.
   */
  public static org.vertx.java.core.Handler<Throwable> createExceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new Handler<Throwable>(env, PhpTypes.toCallable(handler));
  }

  /**
   * Creates a generic asynchronous handler.
   */
  public static <T> org.vertx.java.core.Handler<AsyncResult<T>> createAsyncGenericHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new AsyncResultHandler<T>(env, PhpTypes.toCallable(handler));
  }

  /**
   * Creates an asynchronous void handler.
   */
  public static org.vertx.java.core.Handler<AsyncResult<Void>> createAsyncVoidHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new AsyncResultHandler<Void>(env, PhpTypes.toCallable(handler)) {
      @Override
      public void handle(AsyncResult<Void> result) {
        Env env = getEnvironment();
        if (result.succeeded()) {
          getCallable().call(env, env.wrapJava(null));
        }
        else {
          getCallable().call(env, env.wrapJava(result.cause()));
        }
      }
    };
  }

  /**
   * Creates an asynchronous buffer result handler.
   */
  public static org.vertx.java.core.Handler<AsyncResult<org.vertx.java.core.buffer.Buffer>> createAsyncBufferHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler);
    return new AsyncResultHandler<org.vertx.java.core.buffer.Buffer>(env, PhpTypes.toCallable(handler), new AsyncResultWrapper<org.vertx.java.core.buffer.Buffer, Buffer>() {
      @Override
      public Buffer wrap(org.vertx.java.core.buffer.Buffer buffer) {
        return new Buffer(buffer);
      }
    });
  }

}

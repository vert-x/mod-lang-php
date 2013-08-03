package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;

import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;

/**
 * An asynchronous result handler.
 *
 * @author Jordan Halterman
 */
public class AsyncResultHandler<T> extends Handler<AsyncResult<T>> {

  public AsyncResultHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public AsyncResultHandler(Env env, Callable handler, ArgumentWrapper<AsyncResult<T>, ?> modifier) {
    super(env, handler, modifier);
  }

  public void handle(AsyncResult<T> result) {
    Env env = getEnvironment();
    if (result.succeeded()) {
      getCallable().call(env, env.wrapJava(result.result()), env.wrapJava(null));
    }
    else {
      getCallable().call(env, env.wrapJava(null), env.wrapJava(result.cause()));
    }
  }

}

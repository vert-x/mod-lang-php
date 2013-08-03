package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;

import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;

/**
 * A void AsyncResult handler.
 *
 * @author Jordan Halterman
 */
public class VoidAsyncResultHandler extends AsyncResultHandler<Void> {

  public VoidAsyncResultHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public void handle(AsyncResult<Void> result) {
    Env env = getEnvironment();
    if (result.succeeded()) {
      getCallable().call(env, env.wrapJava(null));
    }
    else {
      getCallable().call(env, env.wrapJava(result.cause()));
    }
  }

}

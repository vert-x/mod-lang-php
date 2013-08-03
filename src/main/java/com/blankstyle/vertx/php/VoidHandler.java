package com.blankstyle.vertx.php;

import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;

/**
 * A void handler that calls the callable with no arguments.
 */
public class VoidHandler extends Handler<Void> {

  public VoidHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public void handle(Void arg) {
    Env env = getEnvironment();
    getCallable().call(env);
  }

}

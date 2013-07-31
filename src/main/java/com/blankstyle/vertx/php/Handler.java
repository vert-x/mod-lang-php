package com.blankstyle.vertx.php;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;

/**
 * A helper class for creating Vertx handlers that invoke PHP callbacks.
 */
public final class Handler<T> implements org.vertx.java.core.Handler<T> {

  /**
   * A Quercus environment.
   */
  private Env env;

  /**
   * A PHP callback.
   */
  private Callback handler;

  /**
   * An optional argument modifier. The modifier will be applied to
   * arguments when the handler's handle() method is called.
   */
  private ArgumentModifier<T, ?> modifier;

  public Handler(Env env, Callback handler) {
    this.env = env;
    this.handler = handler;
  }

  public Handler(Env env, Callback handler, ArgumentModifier<T, ?> modifier) {
    this.env = env;
    this.handler = handler;
    this.modifier = modifier;
  }

  public void handle(T arg) {
    if (modifier != null) {
      handler.call(env, env.wrapJava(modifier.modify(arg)));
    }
    else {
      handler.call(env, env.wrapJava(arg));
    }
  }

}

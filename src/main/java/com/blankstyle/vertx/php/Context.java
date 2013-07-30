package com.blankstyle.vertx.php;

import org.vertx.java.core.Handler;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x Context.
 */
public final class Context {

  private org.vertx.java.core.Context context;

  public Context(org.vertx.java.core.Context context) {
    this.context = context;
  }

  /**
   * @param callback A callable PHP function, method, or closure.
   */
  public void runOnContext(final Env env, final Callback callback) {
    context.runOnContext(new Handler<Void>() {
      public void handle(Void result) {
        callback.call(env, env.wrapJava(result));
      }
    });
  }

}

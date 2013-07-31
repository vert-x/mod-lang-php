package com.blankstyle.vertx.php;

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
  public void runOnContext(final Env env, final Callback handler) {
    context.runOnContext(new Handler<Void>(env, handler));
  }

}

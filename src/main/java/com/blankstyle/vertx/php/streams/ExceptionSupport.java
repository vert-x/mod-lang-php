package com.blankstyle.vertx.php.streams;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x ExceptionSupport interface.
 */
public interface ExceptionSupport {

  /**
   * Sets an exception handler.
   *
   * @param handler A PHP callback.
   * @return The called object.
   */
  public Value exceptionHandler(Env env, Callback handler);

}

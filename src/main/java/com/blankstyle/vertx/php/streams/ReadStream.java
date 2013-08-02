package com.blankstyle.vertx.php.streams;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x ReadStream.
 */
public interface ReadStream {

  /**
   * Pauses the stream.
   *
   * @return The stream implementation.
   */
  public Value pause(Env env);

  /**
   * Resumes the stream.
   *
   * @return The stream implementation.
   */
  public Value resume(Env env);

  /**
   * Sets a data handler on the stream.
   *
   * @param handler A PHP callback.
   * @return The stream implementation.
   */
  public Value dataHandler(Env env, Callback handler);

  /**
   * Sets an end handler on the stream.
   *
   * @param handler A PHP callback.
   * @return The stream implementation.
   */
  public Value endHandler(Env env, Callback handler);

}

package com.blankstyle.vertx.php.streams;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x ReadStream.
 */
public interface ReadStream<T> {

  /**
   * Pauses the stream.
   *
   * @return The stream implementation.
   */
  public T pause(Env env);

  /**
   * Resumes the stream.
   *
   * @return The stream implementation.
   */
  public T resume(Env env);

  /**
   * Sets a data handler on the stream.
   *
   * @param handler A PHP callback.
   * @return The stream implementation.
   */
  public T dataHandler(Env env, Callback handler);

  /**
   * Sets an internal data handler on the stream.
   */
  public T dataHandler(Handler<Buffer> handler);

  /**
   * Sets an end handler on the stream.
   *
   * @param handler A PHP callback.
   * @return The stream implementation.
   */
  public T endHandler(Env env, Callback handler);

}

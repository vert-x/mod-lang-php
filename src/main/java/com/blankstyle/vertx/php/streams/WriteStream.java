package com.blankstyle.vertx.php.streams;

import org.vertx.java.core.Handler;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x WriteStream.
 */
public interface WriteStream<T> {

  /**
   * Writes data to the stream.
   */
  public T write(Env env, Value data, @Optional StringValue enc);

  /**
   * Adds a drain handler to the stream.
   *
   * @param handler A PHP callback.
   * @return The stream implementation.
   */
  public T drainHandler(Env env, Callback handler);

  /**
   * Sets an internal drain handler on the stream.
   */
  public T drainHandler(Handler<Void> handler);

  /**
   * Sets the max write queue size.
   *
   * @param size A PHP number value.
   * @return The stream implementation.
   */
  public T writeQueueMaxSize(Env env, NumberValue size);

  /**
   * Returns a boolean value indicating whether the write
   * queue is full.
   */
  public BooleanValue writeQueueFull(Env env);

}

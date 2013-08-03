package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;

/**
 * An argument modifier that wraps asynchronous results.
 *
 * @author Jordan Halterman
 */
public abstract class AsyncResultWrapper<T, E> implements ArgumentWrapper<AsyncResult<T>, AsyncResult<E>> {
  @Override
  public AsyncResult<E> modify(final AsyncResult<T> result) {
    return new AsyncResult<E>() {

      @Override
      public Throwable cause() {
        return result.cause();
      }

      @Override
      public boolean failed() {
        return result.failed();
      }

      @Override
      public E result() {
        if (result.succeeded()) {
          return wrap(result.result());
        }
        return null;
      }

      @Override
      public boolean succeeded() {
        return result.succeeded();
      }

    };
  }

  /**
   * Wraps the result in the appropriate wrapper.
   */
  public abstract E wrap(T result);

}

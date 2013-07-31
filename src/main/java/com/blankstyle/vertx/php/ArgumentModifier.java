package com.blankstyle.vertx.php;

/**
 * An interface for wrapping event arguments.
 *
 * @param <T> The type of argument that is being wrapped.
 * @param <E> The resulting argument type after wrapping.
 */
public interface ArgumentModifier<T, E> {

  /**
   * Wraps an argument.
   *
   * @param arg The argument to wrap.
   * @return The wrapped argument.
   */
  public E modify(T arg);

}

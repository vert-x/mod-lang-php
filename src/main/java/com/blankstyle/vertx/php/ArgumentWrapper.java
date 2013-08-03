/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blankstyle.vertx.php;

/**
 * An interface for wrapping event arguments.
 *
 * @param <T> The type of argument that is being wrapped.
 * @param <E> The resulting argument type after wrapping.
 */
public interface ArgumentWrapper<T, E> {

  /**
   * Wraps an argument.
   *
   * @param arg The argument to wrap.
   * @return The wrapped argument.
   */
  public E modify(T arg);

}

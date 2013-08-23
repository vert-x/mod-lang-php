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
package io.vertx.lang.php.streams;

import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.Env;

/**
 * A PHP compatible implementation of the Vert.x ExceptionSupport interface.
 *
 * @author Jordan Halterman
 */
public interface ExceptionSupport<T> {

  /**
   * Sets an exception handler.
   *
   * @param handler A PHP callback.
   * @return The called object.
   */
  public T exceptionHandler(Env env, Value handler);

}

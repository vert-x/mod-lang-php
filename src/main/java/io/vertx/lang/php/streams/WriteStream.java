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

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x WriteStream.
 *
 * @author Jordan Halterman
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
  public T drainHandler(Env env, Value handler);

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

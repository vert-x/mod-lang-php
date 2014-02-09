/*
 * Copyright 2014 the original author or authors.
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
package io.vertx.lang.php.eventbus;

import org.vertx.java.core.VertxException;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * An event bus reply exception.
 *
 * @author Jordan Halterman
 */
@SuppressWarnings("serial")
public class ReplyException extends VertxException {
  private final org.vertx.java.core.eventbus.ReplyException exception;

  ReplyException(org.vertx.java.core.eventbus.ReplyException exception) {
    super(exception);
    this.exception = exception;
  }

  /**
   * Returns the reply failure type.
   *
   * @return The reply failure type.
   */
  public Value failureType(Env env) {
    return env.wrapJava(exception.failureType().toInt());
  }

}

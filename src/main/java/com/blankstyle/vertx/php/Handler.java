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

import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;

/**
 * A helper class for creating Vertx handlers that invoke PHP callbacks.
 */
public class Handler<T> implements org.vertx.java.core.Handler<T> {

  /**
   * A Quercus environment.
   */
  private Env env;

  /**
   * A PHP callback.
   */
  private Callback handler;

  /**
   * An optional argument modifier. The modifier will be applied to
   * arguments when the handler's handle() method is called.
   */
  private ArgumentModifier<T, ?> modifier;

  public Handler(Env env, Callback handler) {
    this.env = env;
    this.handler = handler;
  }

  public Handler(Env env, Callback handler, ArgumentModifier<T, ?> modifier) {
    this.env = env;
    this.handler = handler;
    this.modifier = modifier;
  }

  public void handle(T arg) {
    if (modifier != null) {
      handler.call(env, env.wrapJava(modifier.modify(arg)));
    }
    else {
      handler.call(env, env.wrapJava(arg));
    }
  }

  public String toString() {
    return "php:Vertx\\Handler";
  }

}

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

import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;

/**
 * An implementation of the Vert.x Handler that invokes PHP
 * callables. This allows closures and other callables to be
 * passed to the PHP Vert.x API as common event handlers.
 *
 * @author Jordan Halterman
 */
public class Handler<T> implements org.vertx.java.core.Handler<T> {

  /**
   * A Quercus environment.
   */
  private Env env;

  /**
   * A PHP callback.
   */
  private Callable handler;

  /**
   * An optional argument modifier. The modifier will be applied to
   * arguments when the handler's handle() method is called.
   */
  private ResultModifier<T, ?> modifier;

  public Handler(Env env, Callable handler) {
    this.env = env;
    this.handler = handler;
  }

  public Handler(Env env, Callable handler, ResultModifier<T, ?> modifier) {
    this.env = env;
    this.handler = handler;
    this.modifier = modifier;
  }

  protected Env getEnvironment() {
    return env;
  }

  protected Callable getCallable() {
    return handler;
  }

  protected boolean hasModifier() {
    return modifier != null;
  }

  protected ResultModifier<T, ?> getModifier() {
    return modifier;
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

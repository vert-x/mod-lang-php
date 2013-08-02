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
 * A PHP compatible implementation of the Vert.x Context.
 */
public final class Context {

  private org.vertx.java.core.Context context;

  public Context(org.vertx.java.core.Context context) {
    this.context = context;
  }

  /**
   * @param callback A callable PHP function, method, or closure.
   */
  public void runOnContext(final Env env, final Callback handler) {
    context.runOnContext(new Handler<Void>(env, handler));
  }

}

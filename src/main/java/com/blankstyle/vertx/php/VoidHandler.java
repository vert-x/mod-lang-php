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
 * A void handler that calls the callable with no arguments.
 */
public class VoidHandler extends Handler<Void> {

  public VoidHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public void handle(Void arg) {
    Env env = getEnvironment();
    getCallable().call(env);
  }

}

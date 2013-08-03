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

import org.vertx.java.core.AsyncResult;

import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;

/**
 * A void AsyncResult handler.
 *
 * This handler will invoke the PHP callback with a single
 * argument - an error if one so occurs.
 *
 * @author Jordan Halterman
 */
public class VoidAsyncResultHandler extends AsyncResultHandler<Void> {

  public VoidAsyncResultHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public void handle(AsyncResult<Void> result) {
    Env env = getEnvironment();
    if (result.succeeded()) {
      getCallable().call(env, env.wrapJava(null));
    }
    else {
      getCallable().call(env, env.wrapJava(result.cause()));
    }
  }

}

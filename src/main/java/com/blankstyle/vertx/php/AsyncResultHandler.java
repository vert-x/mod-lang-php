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
 * An asynchronous result handler.
 *
 * This implementation of the org.vertx.java.core.Handler interface
 * executes PHP callbacks with asynchronous results, calling the
 * callbacks with the result value as the first argument and the
 * result error (if any) as the second.
 *
 * @author Jordan Halterman
 */
public class AsyncResultHandler<T> extends Handler<AsyncResult<T>> {

  public AsyncResultHandler(Env env, Callable handler) {
    super(env, handler);
  }

  public AsyncResultHandler(Env env, Callable handler, ArgumentWrapper<AsyncResult<T>, ?> modifier) {
    super(env, handler, modifier);
  }

  public void handle(AsyncResult<T> result) {
    Env env = getEnvironment();
    if (result.succeeded()) {
      getCallable().call(env, env.wrapJava(result.result()), env.wrapJava(null));
    }
    else {
      getCallable().call(env, env.wrapJava(null), env.wrapJava(result.cause()));
    }
  }

}

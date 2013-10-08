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
package io.vertx.lang.php.sockjs;

import io.vertx.lang.php.Handler;
import io.vertx.lang.php.ResultModifier;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.LongValue;

/**
 * A PHP compatible implementation of the Vert.x SockJSServer.
 * 
 * @author Jordan Halterman
 */
public class SockJSServer {

  private org.vertx.java.core.sockjs.SockJSServer server;

  public SockJSServer(org.vertx.java.core.sockjs.SockJSServer server) {
    this.server = server;
  }

  /**
   * Bridges the SockJS app to the event bus.
   */
  public SockJSServer bridge(Env env, ArrayValue config, ArrayValue inboundPermitted, ArrayValue outboundPermitted,
      @Optional LongValue authTimeout, @Optional Value authAddress) {
    if (PhpTypes.notNull(authTimeout) && PhpTypes.notNull(authAddress)) {
      server.bridge(PhpTypes.arrayToJson(env, config), PhpTypes.arrayToJsonArray(env, inboundPermitted),
          PhpTypes.arrayToJsonArray(env, outboundPermitted), authTimeout.toLong(), authAddress.toString());
    }
    else if (PhpTypes.notNull(authTimeout)) {
      server.bridge(PhpTypes.arrayToJson(env, config), PhpTypes.arrayToJsonArray(env, inboundPermitted),
          PhpTypes.arrayToJsonArray(env, outboundPermitted), authTimeout.toLong());
    }
    else {
      server.bridge(PhpTypes.arrayToJson(env, config), PhpTypes.arrayToJsonArray(env, inboundPermitted),
          PhpTypes.arrayToJsonArray(env, outboundPermitted));
    }
    return this;
  }

  /**
   * Installs an app.
   */
  public SockJSServer installApp(Env env, ArrayValue config, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\SockJS\\SockJSServer::installApp() must be callable.");
    server.installApp(PhpTypes.arrayToJson(env, config),
        new Handler<org.vertx.java.core.sockjs.SockJSSocket>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.sockjs.SockJSSocket, SockJSSocket>() {
              @Override
              public SockJSSocket modify(org.vertx.java.core.sockjs.SockJSSocket socket) {
                return new SockJSSocket(socket);
              }
            }));
    return this;
  }

  /**
   * Sets a hook.
   */
  public SockJSServer setHook(Env env, ObjectValue hook) {
    return this;
  }

  public String toString() {
    return "php:Vertx\\SockJS\\SockJSServer";
  }

}

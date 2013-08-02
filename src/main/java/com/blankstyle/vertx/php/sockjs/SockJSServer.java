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
package com.blankstyle.vertx.php.sockjs;

import java.util.ArrayList;
import java.util.HashMap;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Handler;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.LongValue;

/**
 * A PHP compatible implementation of the Vert.x SockJSServer.
 */
public class SockJSServer {

  private org.vertx.java.core.sockjs.SockJSServer server;

  public SockJSServer(org.vertx.java.core.sockjs.SockJSServer server) {
    this.server = server;
  }

  /**
   * Bridges the SockJS app to the event bus.
   */
  @SuppressWarnings("unchecked")
  public SockJSServer bridge(Env env, ArrayValue config, ArrayValue inboundPermitted, ArrayValue outboundPermitted, @Optional LongValue authTimeout, @Optional StringValue authAddress) {
    if (authTimeout != null && !authTimeout.isDefault() && authAddress != null && !authAddress.isDefault()) {
      server.bridge(new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), new JsonArray(inboundPermitted.toArray().toJavaList(env, new ArrayList<Object>().getClass())), new JsonArray(outboundPermitted.toJavaList(env, new ArrayList<Object>().getClass())), authTimeout.toLong(), authAddress.toString());
    }
    else if (authTimeout != null && !authTimeout.isDefault()) {
      server.bridge(new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), new JsonArray(inboundPermitted.toArray().toJavaList(env, new ArrayList<Object>().getClass())), new JsonArray(outboundPermitted.toJavaList(env, new ArrayList<Object>().getClass())), authTimeout.toLong());
    }
    else {
      server.bridge(new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), new JsonArray(inboundPermitted.toArray().toJavaList(env, new ArrayList<Object>().getClass())), new JsonArray(outboundPermitted.toJavaList(env, new ArrayList<Object>().getClass())));
    }
    return this;
  }

  /**
   * Installs an app.
   */
  @SuppressWarnings("unchecked")
  public SockJSServer installApp(Env env, ArrayValue config, Callback handler) {
    server.installApp(new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())),
                      new Handler<org.vertx.java.core.sockjs.SockJSSocket>(env, handler, new ArgumentModifier<org.vertx.java.core.sockjs.SockJSSocket, SockJSSocket>() {
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

}

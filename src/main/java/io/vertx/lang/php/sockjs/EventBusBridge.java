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
package io.vertx.lang.php.sockjs;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

import io.vertx.lang.php.PhpVerticleFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible event bus bridge.
 *
 * @author Jordan Halterman
 */
public class EventBusBridge {
  private final org.vertx.java.core.sockjs.EventBusBridge bridge;
  private EventBusBridgeHook hook;

  public EventBusBridge(Env env, ArrayValue inboundPermitted, ArrayValue outboundPermitted, @Optional Value authTimeout, @Optional StringValue authAddress) {
    if (PhpTypes.notNull(authTimeout) && PhpTypes.notNull(authAddress)) {
      bridge = new org.vertx.java.core.sockjs.EventBusBridge(PhpVerticleFactory.vertx, PhpTypes.arrayToJsonArray(env, inboundPermitted), PhpTypes.arrayToJsonArray(env, outboundPermitted), authTimeout.toLong(), authAddress.toString());
    }
    else if (PhpTypes.notNull(authTimeout)) {
      bridge = new org.vertx.java.core.sockjs.EventBusBridge(PhpVerticleFactory.vertx, PhpTypes.arrayToJsonArray(env, inboundPermitted), PhpTypes.arrayToJsonArray(env, outboundPermitted), authTimeout.toLong());
    }
    else {
      bridge = new org.vertx.java.core.sockjs.EventBusBridge(PhpVerticleFactory.vertx, PhpTypes.arrayToJsonArray(env, inboundPermitted), PhpTypes.arrayToJsonArray(env, outboundPermitted));
    }
  }

  /**
   * Called when an event has occurred.
   */
  public void handle(final SockJSSocket socket) {
    bridge.handle(socket.socket);
  }

  /**
   * Sets the event bus bridge hook.
   *
   * @param hook An event bus bridge hook.
   */
  public void setHook(EventBusBridgeHook hook) {
    this.hook = hook;
    bridge.setHook(new WrappedHook(hook));
  }

  /**
   * Returns the event bus bridge hook.
   *
   * @return An event bus bridge hook.
   */
  public EventBusBridgeHook getHook() {
    return hook;
  }

  /**
   * A wrapped event bus bridge hook
   */
  private static class WrappedHook implements org.vertx.java.core.sockjs.EventBusBridgeHook {
    private final EventBusBridgeHook hook;
    private WrappedHook(EventBusBridgeHook hook) {
      this.hook = hook;
    }
    @Override
    public boolean handleSocketCreated(org.vertx.java.core.sockjs.SockJSSocket sock) {
      return hook.handleSocketCreated(new SockJSSocket(sock)).toBoolean();
    }
    @Override
    public void handleSocketClosed(org.vertx.java.core.sockjs.SockJSSocket sock) {
      hook.handleSocketClosed(new SockJSSocket(sock));
    }
    @Override
    public boolean handleSendOrPub(org.vertx.java.core.sockjs.SockJSSocket sock, boolean send, JsonObject msg, String address) {
      return hook.handleSendOrPub(new SockJSSocket(sock), BooleanValue.create(send), PhpTypes.arrayFromJson(Env.getCurrent(), msg), StringValue.create(address).toStringValue()).toBoolean();
    }
    @Override
    public boolean handlePreRegister(org.vertx.java.core.sockjs.SockJSSocket sock, String address) {
      return hook.handlePreRegister(new SockJSSocket(sock), StringValue.create(address).toStringValue()).toBoolean();
    }
    @Override
    public void handlePostRegister(org.vertx.java.core.sockjs.SockJSSocket sock, String address) {
      hook.handlePostRegister(new SockJSSocket(sock), StringValue.create(address).toStringValue());
    }
    @Override
    public boolean handleUnregister(org.vertx.java.core.sockjs.SockJSSocket sock, String address) {
      return hook.handleUnregister(new SockJSSocket(sock), StringValue.create(address).toStringValue()).toBoolean();
    }
    @Override
    public boolean handleAuthorise(JsonObject message, String sessionID, Handler<AsyncResult<Boolean>> handler) {
      return hook.handleAuthorise(PhpTypes.arrayFromJson(Env.getCurrent(), message), StringValue.create(sessionID).toStringValue(), null).toBoolean();
    }
  }

}

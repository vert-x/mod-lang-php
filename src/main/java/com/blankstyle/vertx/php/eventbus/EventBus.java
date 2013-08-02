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
package com.blankstyle.vertx.php.eventbus;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Handler;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.json.JsonModule;

/**
 * A PHP compatible implementation of the Vert.x EventBus.
 */
public final class EventBus {

  private org.vertx.java.core.eventbus.EventBus eventBus;

  public EventBus(org.vertx.java.core.eventbus.EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Registers a new event handler.
   */
  public EventBus registerHandler(Env env, final StringValue address, Callback handler, @Optional Value resultHandler) {
    if (resultHandler != null && !resultHandler.isNull()) {
      if (!resultHandler.isCallable(env, false, null)) {
        env.error("Handler argument to EventBus::registerHandler() must be callable.");
      }
      eventBus.registerHandler(address.toString(), new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, handler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
        @Override
        public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> message) {
          return new Message<Object>(message);
        }
      }), new Handler<AsyncResult<Void>>(env, (Callback) resultHandler));
    }
    else {
      eventBus.registerHandler(address.toString(), new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, handler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
        @Override
        public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> message) {
          return new Message<Object>(message);
        }
      }));
    }
    return this;
  }

  /**
   * Registers a new local event handler.
   */
  public EventBus registerLocalHandler(Env env, StringValue address, Value handler) {
    if (handler == null || handler.isNull() || !handler.isCallable(env, false, null)) {
      env.error("Handler argument to EventBus::registerLocalHandler() must be callable.");
    }
    eventBus.registerLocalHandler(address.toString(), new Handler<org.vertx.java.core.eventbus.Message<?>>(env, (Callback) handler));
    return this;
  }

  /**
   * Sends a message on the bus.
   */
  public EventBus send(Env env, StringValue address, final Value message, @Optional Value handler) {
    boolean hasHandler = false;
    Handler<org.vertx.java.core.eventbus.Message<Object>> sendHandler = null;

    if (handler != null && !handler.isNull()) {
      if (!handler.isCallable(env, false, null)) {
        env.error("Handler argument to EventBus::send() must be callable.");
      }
      hasHandler = true;
      sendHandler = new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, (Callback) handler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
        @Override
        public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> arg) {
          return new Message<Object>(arg);
        }
      });
    }

    if (message.isBoolean()) {
      if (hasHandler) {
        eventBus.send(address.toString(), message.toBoolean(), sendHandler);
      }
      else {
        eventBus.send(address.toString(), message.toBoolean());
      }
    }
    else if (message.isString()) {
      if (hasHandler) {
        eventBus.send(address.toString(), message.toString(), sendHandler);
      }
      else {
        eventBus.send(address.toString(), message.toString());
      }
    }
    else if (message.isNumeric()) {
      if (hasHandler) {
        eventBus.send(address.toString(), message.toInt(), sendHandler);
      }
      else {
        eventBus.send(address.toString(), message.toInt());
      }
    }
    else if (message.isArray()) {
      if (hasHandler) {
        eventBus.send(address.toString(), new JsonObject(JsonModule.json_encode(env, message, 0).toString()), sendHandler);
      }
      else {
        eventBus.send(address.toString(), new JsonObject(JsonModule.json_encode(env, message, 0).toString()));
      }
    }
    return this;
  }

  /**
   * Publishes a message to the event bus.
   */
  public EventBus publish(Env env, Value address, Value message) {
    if (message.isBoolean()) {
      eventBus.send(address.toString(), message.toBoolean());
    }
    else if (message.isString()) {
      eventBus.send(address.toString(), message.toString());
    }
    else if (message.isNumeric()) {
      eventBus.send(address.toString(), message.toInt());
    }
    else if (message.isArray()) {
      eventBus.send(address.toString(), new JsonObject(JsonModule.json_encode(env, message, 0).toString()));
    }
    return this;
  }

  /**
   * Closes the event bus.
   */
  public void close(Env env, Value handler) {
    if (handler == null || handler.isNull() || !handler.isCallable(env, false, null)) {
      env.error("Handler argument to EventBus::close() must be callable.");
    }
    eventBus.close(new Handler<AsyncResult<Void>>(env, (Callback) handler));
  }

}

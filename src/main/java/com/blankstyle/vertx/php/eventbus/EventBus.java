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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.util.PHPAssert;
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

  private static Map<String, Map<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>>> handlers = new HashMap<String, Map<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>>>();

  public EventBus(org.vertx.java.core.eventbus.EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Registers an address handler in the internal handler map. This
   * allows us to unregister handlers by the object rather thanan ID.
   *
   * @param address The address at which the handler is being registered.
   * @param handler A PHP callable event handler.
   */
  private org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> createAddressHandler(Env env, String address, Value callback) {
    org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> handler = new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, (Callback) callback, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
      @Override
      public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> message) {
        return new Message<Object>(message);
      }
    });

    if (!EventBus.handlers.containsKey(address)) {
      Map<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> hashMap = new HashMap<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>>();
      EventBus.handlers.put(address, hashMap);
    }
    else {
      Map<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> hashMap = EventBus.handlers.get(address);
      hashMap.put(callback, handler);
    }
    return handler;
  }

  /**
   * Looks up an existing internal address handler.
   *
   * @param address The address at which the handler was registered.
   * @param callback A PHP callable event handler.
   * @return
   */
  private org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> findAddressHandler(Env env, String address, Value callback) {
    if (!EventBus.handlers.containsKey(address)) {
      return null;
    }
    Map<Value, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> hashMap = EventBus.handlers.get(address);
    return hashMap.containsKey(callback) ? hashMap.get(callback) : null;
  }

  /**
   * Registers a new event handler.
   */
  public EventBus registerHandler(Env env, StringValue address, Callback handler, @Optional Value resultHandler) {
    if (resultHandler != null && !resultHandler.isNull()) {
      if (!resultHandler.isCallable(env, false, null)) {
        env.error("Handler argument to EventBus::registerHandler() must be callable.");
      }

      // Create Vert.x API compatible handlers. This will wrap PHP callbacks
      // and wrap return values when the handler is invoked.
      org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> eventHandler = createAddressHandler(env, address.toString(), handler);
      org.vertx.java.core.Handler<org.vertx.java.core.AsyncResult<Void>> resultEventHandler = new Handler<AsyncResult<Void>>(env, (Callback) resultHandler);

      eventBus.registerHandler(address.toString(), eventHandler, resultEventHandler);
    }
    else {
      if (handler == null || handler.isNull() || !handler.isCallable(env, false, null)) {
        env.error("Handler argument to EventBus::registerHandler() must be callable.");
      }
      eventBus.registerHandler(address.toString(), createAddressHandler(env, address.toString(), handler));
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
    eventBus.registerLocalHandler(address.toString(), createAddressHandler(env, address.toString(), handler));
    return this;
  }

  /**
   * Unregisters an event handler.
   */
  public EventBus unregisterHandler(Env env, StringValue address, Value handler) {
    validateRequiredHandler(env, handler, "Handler to unregisterHandler() must be callable.");
    org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> eventHandler = findAddressHandler(env, address.toString(), handler);
    eventBus.unregisterHandler(address.toString(), eventHandler);
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

  /**
   * Validates a required PHP callback.
   *
   * The handler will be evaluated to ensure it is not null and is
   * callable. This is required because Quercus will sometimes pass
   * values that are not null but are not callable either, such as
   * when an invalid PHP variable is passed to the method. Using the
   * Quercus Value type is preferred over using the Callback type
   * because Quercus will simply pass a null value for Callback types
   * if the value is invalid, and we'd prefer to be able to validate
   * the value and give the user a meaningful message.
   */
  private boolean validateRequiredHandler(Env env, Value handler, String message, Object... args) {
    try {
      PHPAssert.isCallable(env, handler, message, args);
    }
    catch (IllegalArgumentException e) {
      env.error(e);
    }
    return true;
  }

  /**
   * Validates an optional PHP callback.
   *
   * If the handler value is null then true will be returned. Otherwise,
   * the method will evaluate whether the handler is callable.
   */
  private boolean validateOptionalHandler(Env env, Value handler, String message, Object... args) {
    try {
      PHPAssert.isCallable(env, handler, message, args);
    }
    catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  public String toString() {
    return "php:Vertx\\EventBus";
  }

}

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
  public EventBus registerHandler(final Env env, final StringValue address, final Callback handler, @Optional final Callback resultHandler) {
    if (resultHandler != null && !resultHandler.isDefault()) {
      eventBus.registerHandler(address.toString(), new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, handler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
        @Override
        public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> message) {
          return new Message<Object>(message);
        }
      }), new Handler<AsyncResult<Void>>(env, resultHandler));
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
  public EventBus registerLocalHandler(final Env env, final StringValue address, final Callback handler) {
    eventBus.registerLocalHandler(address.toString(), new Handler<org.vertx.java.core.eventbus.Message<?>>(env, handler));
    return this;
  }

  /**
   * Sends a message on the bus.
   */
  public EventBus send(final Env env, final StringValue address, final Value message, @Optional final Callback handler) {
    boolean hasHandler = false;
    Handler<org.vertx.java.core.eventbus.Message<Object>> sendHandler = null;

    if (handler != null && !handler.isDefault()) {
      hasHandler = true;
      sendHandler = new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, handler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
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
  public EventBus publish(final Env env, final Value address, final Value message) {
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
  public void close(final Env env, final Callback handler) {
    eventBus.close(new Handler<AsyncResult<Void>>(env, handler));
  }

}

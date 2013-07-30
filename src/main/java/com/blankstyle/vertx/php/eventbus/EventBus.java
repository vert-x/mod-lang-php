package com.blankstyle.vertx.php.eventbus;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

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
  public EventBus registerHandler(final Env env, final StringValue address, final Callback handler) {
    eventBus.registerHandler(address.toJavaString(), new Handler<Message>() {
      public void handle(Message message) {
        handler.call(env, env.wrapJava(message));
      }
    });
    return this;
  }

  /**
   * Sends a message on the bus.
   */
  public EventBus send(final Env env, final StringValue address, final Value message, @Optional final Callback handler) {
    if (message.isBoolean()) {
      if (handler != null && !handler.isDefault()) {
        eventBus.send(address.toJavaString(), message.toJavaBoolean(), new Handler<Message>() {
          public void handle(Message message) {
            handler.toCallable(env, false).call(env, env.wrapJava(message));
          }
        });
      }
      else {
        eventBus.send(address.toJavaString(), message.toJavaBoolean());
      }
    }
    else if (message.isString()) {
      if (handler != null && !handler.isDefault()) {
        eventBus.send(address.toJavaString(), message.toJavaString(), new Handler<Message>() {
          public void handle(Message message) {
            handler.toCallable(env, false).call(env, env.wrapJava(message));
          }
        });
      }
      else {
        eventBus.send(address.toJavaString(), message.toJavaString());
      }
    }
    else if (message.isNumeric()) {
      if (handler != null && !handler.isDefault()) {
        eventBus.send(address.toJavaString(), message.toJavaInteger(), new Handler<Message>() {
          public void handle(Message message) {
            handler.toCallable(env, false).call(env, env.wrapJava(message));
          }
        });
      }
      else {
        eventBus.send(address.toJavaString(), message.toJavaInteger());
      }
    }
    else if (message.isArray()) {
      if (handler != null && !handler.isDefault()) {
        eventBus.send(address.toJavaString(), new JsonObject(JsonModule.json_encode(env, message, 0).toJavaString()), new Handler<Message>() {
          public void handle(Message message) {
            handler.toCallable(env, false).call(env, env.wrapJava(message));
          }
        });
      }
      else {
        eventBus.send(address.toJavaString(), new JsonObject(JsonModule.json_encode(env, message, 0).toJavaString()));
      }
    }
    return this;
  }

  /**
   * Publishes a message to the event bus.
   */
  public EventBus publish(final Env env, final Value address, final Value message) {
    if (message.isBoolean()) {
      eventBus.send(address.toJavaString(), message.toJavaBoolean());
    }
    else if (message.isString()) {
      eventBus.send(address.toJavaString(), message.toJavaString());
    }
    else if (message.isNumeric()) {
      eventBus.send(address.toJavaString(), message.toJavaInteger());
    }
    else if (message.isArray()) {
      eventBus.send(address.toJavaString(), new JsonObject(JsonModule.json_encode(env, message, 0).toJavaString()));
    }
    return this;
  }

  /**
   * Closes the event bus.
   */
  public void close(final Env env, final Callback handler) {
    eventBus.close(new AsyncResultHandler<Void>() {
      public void handle(AsyncResult<Void> result) {
        handler.toCallable(env, false).call(env, env.wrapJava(result));
      }
    });
  }

}

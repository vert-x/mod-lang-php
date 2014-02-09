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
package io.vertx.lang.php.eventbus;

import io.vertx.lang.php.Handler;
import io.vertx.lang.php.ResultModifier;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.vertx.java.core.AsyncResult;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x EventBus.
 * 
 * @author Jordan Halterman
 */
public final class EventBus {

  private org.vertx.java.core.eventbus.EventBus eventBus;

  private static PairRegistry<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> handlers = new PairRegistry<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>>();

  public EventBus(org.vertx.java.core.eventbus.EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Registers an address handler in the internal handler map. This allows us to
   * unregister handlers by the object rather than an ID.
   * 
   * @param address
   *          The address at which the handler is being registered.
   * @param handler
   *          A PHP callable event handler.
   */
  private org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> createAddressHandler(Env env, String address, Value callback) {
    return new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, PhpTypes.toCallable(callback),
        new ResultModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
          @Override
          public Message<Object> modify(org.vertx.java.core.eventbus.Message<Object> message) {
            return new Message<Object>(message);
          }
        });
  }

  /**
   * Creates an address/handler pair from PHP arguments.
   */
  public AddressPair<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> createAddressPair(Env env, StringValue address, Value handler) {
    String realAddress = address.toString();
    org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>> realHandler = createAddressHandler(env, realAddress, handler);
    return new AddressPair<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>>(realAddress, realHandler);
  }

  /**
   * Registers a new event handler.
   * 
   * @param address
   *          The address at which to register the handler.
   * @param handler
   *          The handler to register. This can be any PHP callable.
   * @param resultHandler
   *          An optional handler to be invoke when the handler registration has
   *          been propagated across the cluster. It will be invoked with a
   *          single argument that represents an error if one occurs, else null.
   * @return The called object.
   */
  public StringValue registerHandler(Env env, StringValue address, Value handler, @Optional Value resultHandler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\EventBus::registerHandler() must be callable.");
    AddressPair<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> addressPair = createAddressPair(env, address, handler);

    if (PhpTypes.isCallable(env, resultHandler)) {
      org.vertx.java.core.Handler<AsyncResult<Void>> resultEventHandler = HandlerFactory.createAsyncVoidHandler(env, handler);
      eventBus.registerHandler(addressPair.getAddress(), addressPair.getHandler(), resultEventHandler);
    }
    else {
      eventBus.registerHandler(addressPair.getAddress(), addressPair.getHandler());
    }
    return env.createString(handlers.register(addressPair).toString());
  }

  /**
   * Registers a new local event handler.
   * 
   * @param address
   *          The address at which to register the handler.
   * @param handler
   *          The handler to register. This can be any PHP callable.
   * @return The called object.
   */
  public StringValue registerLocalHandler(Env env, StringValue address, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\EventBus::registerLocalHandler() must be callable.");
    AddressPair<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> addressPair = createAddressPair(env, address, handler);
    eventBus.registerLocalHandler(addressPair.getAddress(), addressPair.getHandler());
    return env.createString(handlers.register(addressPair).toString());
  }

  /**
   * Unregisters an event handler.
   * 
   * @param address
   *          The address at which to unregister the handler.
   * @param handler
   *          The handler to unregister. This can be any PHP callable.
   * @return The called object.
   */
  public EventBus unregisterHandler(Env env, Value handlerID) {
    if (handlers.exists(handlerID.toString())) {
      AddressPair<String, org.vertx.java.core.Handler<org.vertx.java.core.eventbus.Message<Object>>> pair = handlers.get(handlerID.toString());
      eventBus.unregisterHandler(pair.getAddress(), pair.getHandler());
      handlers.unregister(pair);
    }
    return this;
  }

  /**
   * Sends a point-to-point message on the bus.
   * 
   * @param address
   *          The address to which to send the message.
   * @param message
   *          A mixed value message to send.
   * @param handler
   *          An optional handler to be invoked in response to the message.
   * @return The called object.
   */
  public EventBus send(Env env, StringValue address, Value message, @Optional Value handler) {
    boolean hasHandler = false;
    Handler<org.vertx.java.core.eventbus.Message<Object>> sendHandler = null;

    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\EventBus::send() must be callable.");
      hasHandler = true;
      sendHandler = new Handler<org.vertx.java.core.eventbus.Message<Object>>(env, PhpTypes.toCallable(handler),
          new ResultModifier<org.vertx.java.core.eventbus.Message<Object>, Message<Object>>() {
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
    else if (message.isObject()) {
      if (hasHandler) {
        eventBus.send(address.toString(), (org.vertx.java.core.buffer.Buffer) message.toJavaObject(env, org.vertx.java.core.buffer.Buffer.class), sendHandler);
      }
      else {
        eventBus.send(address.toString(), (org.vertx.java.core.buffer.Buffer) message.toJavaObject(env, org.vertx.java.core.buffer.Buffer.class));
      }
    }
    else if (message.isArray()) {
      if (hasHandler) {
        eventBus.send(address.toString(), PhpTypes.arrayToJson(env, message), sendHandler);
      }
      else {
        eventBus.send(address.toString(), PhpTypes.arrayToJson(env, message));
      }
    }
    return this;
  }

  /**
   * Sends a point-to-point message on the bus with a timeout.
   * 
   * @param address
   *          The address to which to send the message.
   * @param message
   *          A mixed value message to send.
   * @param handler
   *          An optional handler to be invoked in response to the message.
   * @return The called object.
   */
  public EventBus sendWithTimeout(final Env env, StringValue address, Value message, Value timeout, final Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\EventBus::sendWithTimeout() must be callable.");
    org.vertx.java.core.Handler<AsyncResult<org.vertx.java.core.eventbus.Message<Object>>> sendHandler = new org.vertx.java.core.Handler<AsyncResult<org.vertx.java.core.eventbus.Message<Object>>>() {
      @Override
      public void handle(AsyncResult<org.vertx.java.core.eventbus.Message<Object>> result) {
        if (result.failed()) {
          handler.call(env, env.wrapJava(null), env.wrapJava(new ReplyException((org.vertx.java.core.eventbus.ReplyException) result.cause())));
        }
        else {
          handler.call(env, env.wrapJava(new Message<Object>(result.result())), env.wrapJava(null));
        }
      }
    };

    if (message.isBoolean()) {
      eventBus.sendWithTimeout(address.toString(), message.toBoolean(), timeout.toLong(), sendHandler);
    }
    else if (message.isString()) {
      eventBus.sendWithTimeout(address.toString(), message.toString(), timeout.toLong(), sendHandler);
    }
    else if (message.isNumeric()) {
      eventBus.sendWithTimeout(address.toString(), message.toInt(), timeout.toLong(), sendHandler);
    }
    else if (message.isObject()) {
      eventBus.sendWithTimeout(address.toString(), (org.vertx.java.core.buffer.Buffer) message.toJavaObject(env, org.vertx.java.core.buffer.Buffer.class), timeout.toLong(), sendHandler);
    }
    else if (message.isArray()) {
      eventBus.sendWithTimeout(address.toString(), PhpTypes.arrayToJson(env, message), timeout.toLong(), sendHandler);
    }
    return this;
  }

  /**
   * Publishes a message to the event bus.
   * 
   * @param address
   *          The address to which to send the message.
   * @param message
   *          A mixed value message to send.
   * @return The called object.
   */
  public EventBus publish(Env env, Value address, Value message) {
    if (message.isBoolean()) {
      eventBus.publish(address.toString(), message.toBoolean());
    }
    else if (message.isString()) {
      eventBus.publish(address.toString(), message.toString());
    }
    else if (message.isNumeric()) {
      eventBus.publish(address.toString(), message.toInt());
    }
    else if (message.isObject()) {
      eventBus.publish(address.toString(), (org.vertx.java.core.buffer.Buffer) message.toJavaObject(env, org.vertx.java.core.buffer.Buffer.class));
    }
    else if (message.isArray()) {
      eventBus.publish(address.toString(), PhpTypes.arrayToJson(env, message));
    }
    return this;
  }

  /**
   * Closes the event bus.
   * 
   * @param handler
   *          A handler to invoke when the event bus is closed. This handler
   *          will be called with a single argument which represents an error if
   *          one occurs.
   * @return The called object.
   */
  public void close(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\EventBus::close() must be callable.");
    eventBus.close(HandlerFactory.createAsyncVoidHandler(env, handler));
  }

  /**
   * Sets the default reply timeout.
   *
   * @param timeout
   *          A reply timeout.
   * @return The called object
   */
  public EventBus setDefaultReplyTimeout(Env env, Value timeout) {
    eventBus.setDefaultReplyTimeout(timeout.toLong());
    return this;
  }

  /**
   * Gets the default reply timeout.
   *
   * @return The default event bus reply timeout.
   */
  public LongValue getDefaultReplyTimeout(Env env) {
    return LongValue.create(eventBus.getDefaultReplyTimeout());
  }

  public String toString() {
    return "php:Vertx\\EventBus";
  }

  /**
   * A registry of current eventbus handlers.
   *
   * @author Jordan Halterman
   *
   * @param <A> An address pair address type.
   * @param <H> An address pair handler type.
   */
  static private class PairRegistry<A, H> {
    private Map<UUID, AddressPair<A, H>> registry;

    public PairRegistry() {
      registry = new HashMap<UUID, AddressPair<A, H>>();
    }

    /**
     * Registers a new pair.
     */
    public UUID register(AddressPair<A, H> pair) {
      registry.put(pair.getIdentifier(), pair);
      return pair.getIdentifier();
    }

    /**
     * Checks whether a pair with the given ID exists in the registry.
     */
    public boolean exists(UUID id) {
      return registry.containsKey(id);
    }

    /**
     * Checks whether a pair with the given ID exists in the registry.
     */
    public boolean exists(String id) {
      return exists(UUID.fromString(id));
    }

    /**
     * Gets an address pair by ID.
     */
    public AddressPair<A, H> get(UUID id) {
      if (!registry.containsKey(id)) {
        return null;
      }
      return registry.get(id);
    }

    /**
     * Gets an address pair by ID.
     */
    public AddressPair<A, H> get(String id) {
      return get(UUID.fromString(id));
    }

    /**
     * Unregisters an address pair by ID.
     */
    public void unregister(UUID id) {
      if (registry.containsKey(id)) {
        registry.remove(id);
      }
    }

    /**
     * Unregisters an address pair by ID.
     */
    public void unregister(AddressPair<A, H> pair) {
      unregister(pair.getIdentifier());
    }
  }

  /**
   * An address/handler pair.
   *
   * @author Jordan Halterman
   *
   * @param <A> An address type.
   * @param <H> A handler type.
   */
  static private class AddressPair<L, R> {
    private L address;
    private R handler;
    private UUID id;

    public AddressPair(L address, R handler) {
      this.address = address;
      this.handler = handler;
      this.id = UUID.randomUUID();
    }

    /**
     * Returns the unique pair identifier.
     */
    public UUID getIdentifier() {
      return id;
    }

    /**
     * Returns the pair address.
     */
    public L getAddress() {
      return address;
    }

    /**
     * Returns the pair handler.
     */
    public R getHandler() {
      return handler;
    }

    public int hashCode() {
      int hashLeft = address != null ? address.hashCode() : 0;
      int hashRight = handler != null ? handler.hashCode() : 0;
      return (hashLeft + hashRight) * hashRight + hashLeft;
    }

    public String toString() {
      return String.format("(%s, %s)", address, handler);
    }
  }

}

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

import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vertx.php.ResultModifier;
import com.blankstyle.vertx.php.Gettable;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.buffer.Buffer;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x message.
 * 
 * @author Jordan Halterman
 */
public class Message<T> implements Gettable {

  private org.vertx.java.core.eventbus.Message<T> message;

  private boolean isCache;

  private Value cache;

  public Message(org.vertx.java.core.eventbus.Message<T> message) {
    this.message = message;
    initCache();
  }

  private void initCache() {
    Object body = message.body();
    if (body instanceof JsonObject) {
      isCache = true;
      Env env = Env.getCurrent();
      cache = PhpTypes.arrayFromJson(env, (JsonObject) message.body());
    }
    else if (body instanceof org.vertx.java.core.buffer.Buffer) {
      isCache = true;
      cache = Env.getCurrent().wrapJava(new Buffer((org.vertx.java.core.buffer.Buffer) body));
    }
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  /**
   * Returns the body of the message.
   * 
   * Note that this method can also be accessed using the magic PHP getter, i.e.
   * $message->body;
   * 
   * @return The message body.
   */
  public Value body(Env env) {
    if (isCache) {
      return cache;
    }
    return env.wrapJava(message.body());
  }

  /**
   * Replies to the message.
   * 
   * @param message
   *          An optional mixed value message. If no message is provided in the
   *          reply, an empty message will be sent.
   * @param replyHandler
   *          An optional reply handler.
   * @return The called object.
   */
  public void reply(Env env, @Optional Value message, @Optional Value replyHandler) {
    if (PhpTypes.notNull(message)) {
      if (PhpTypes.notNull(replyHandler)) {
        PhpTypes.assertCallable(env, replyHandler,
            "Handler argument to Vertx\\EventBus\\Message::reply() must be callable.");

        Handler<org.vertx.java.core.eventbus.Message<T>> handler = new Handler<org.vertx.java.core.eventbus.Message<T>>(
            env, PhpTypes.toCallable(replyHandler),
            new ResultModifier<org.vertx.java.core.eventbus.Message<T>, Message<T>>() {
              @Override
              public Message<T> modify(org.vertx.java.core.eventbus.Message<T> message) {
                return new Message<T>(message);
              }
            });

        if (message.isBoolean()) {
          this.message.reply(message.toBoolean(), handler);
        }
        else if (message.isString()) {
          this.message.reply(message.toString(), handler);
        }
        else if (message.isNumeric()) {
          this.message.reply(message.toInt(), handler);
        }
        else if (message.isDouble()) {
          this.message.reply(message.toDouble(), handler);
        }
        else if (message.isArray()) {
          this.message.reply(PhpTypes.arrayToJson(env, message), handler);
        }
      }
      else {
        if (message.isBoolean()) {
          this.message.reply(message.toBoolean());
        }
        else if (message.isString()) {
          this.message.reply(message.toString());
        }
        else if (message.isNumeric()) {
          this.message.reply(message.toInt());
        }
        else if (message.isDouble()) {
          this.message.reply(message.toDouble());
        }
        else if (message.isArray()) {
          this.message.reply(PhpTypes.arrayToJson(env, message));
        }
      }
    }
    else if (PhpTypes.notNull(replyHandler)) {
      PhpTypes.assertCallable(env, replyHandler,
          "Handler argument to Vertx\\EventBus\\Message::reply() must be callable.");

      this.message.reply(new Handler<org.vertx.java.core.eventbus.Message<T>>(env, PhpTypes.toCallable(replyHandler),
          new ResultModifier<org.vertx.java.core.eventbus.Message<T>, Message<T>>() {
            @Override
            public Message<T> modify(org.vertx.java.core.eventbus.Message<T> message) {
              return new Message<T>(message);
            }
          }));
    }
    else {
      this.message.reply();
    }
  }

  /**
   * The reply address (if any).
   * 
   * @return The message reply address.
   */
  public StringValue replyAddress(Env env) {
    return env.createString(message.replyAddress());
  }

  public String toString() {
    return "php:Vertx\\EventBus\\Message";
  }

}

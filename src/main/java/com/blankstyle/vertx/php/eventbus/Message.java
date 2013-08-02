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

import java.util.HashMap;

import org.vertx.java.core.json.JsonObject;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Gettable;
import com.blankstyle.vertx.php.Handler;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x message.
 */
public class Message<T> implements Gettable {

  private org.vertx.java.core.eventbus.Message<T> message;

  public Message(org.vertx.java.core.eventbus.Message<T> message) {
    this.message = message;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  /**
   * Returns the body of the message.
   */
  public Value body(Env env) {
    return env.wrapJava(message.body());
  }

  /**
   * Replies to the message.
   */
  @SuppressWarnings("unchecked")
  public void reply(Env env, @Optional Value message, @Optional Callback replyHandler) {
    if (message != null && !message.isDefault()) {
      if (replyHandler != null && !replyHandler.isDefault()) {
        Handler<org.vertx.java.core.eventbus.Message<T>> handler = new Handler<org.vertx.java.core.eventbus.Message<T>>(env, replyHandler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<T>, Message<T>>() {
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
          ArrayValue arr = (ArrayValue) message;
          this.message.reply(new JsonObject(arr.toJavaMap(env, new HashMap<String, Object>().getClass())), handler);
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
          ArrayValue arr = (ArrayValue) message;
          this.message.reply(new JsonObject(arr.toJavaMap(env, new HashMap<String, Object>().getClass())));
        }
      }
    }
    else if (replyHandler != null && !replyHandler.isDefault()) {
      this.message.reply(new Handler<org.vertx.java.core.eventbus.Message<T>>(env, replyHandler, new ArgumentModifier<org.vertx.java.core.eventbus.Message<T>, Message<T>>() {
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
   */
  public StringValue replyAddress(Env env) {
    return env.createString(message.replyAddress());
  }

}

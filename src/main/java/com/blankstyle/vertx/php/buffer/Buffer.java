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
package com.blankstyle.vertx.php.buffer;

import java.util.Arrays;

import com.blankstyle.vertx.php.Gettable;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.spl.ArrayAccess;
import com.caucho.quercus.lib.spl.Countable;

/**
 * A wrapper around the Vert.x Buffer.
 *
 * @author Jordan Halterman
 */
public class Buffer implements ArrayAccess, Countable, Gettable {

  private org.vertx.java.core.buffer.Buffer buffer;

  private static final String[] properties = {"length"};

  public Buffer() {
    buffer = new org.vertx.java.core.buffer.Buffer();
  }

  public Buffer(Env env) {
    buffer = new org.vertx.java.core.buffer.Buffer();
  }

  public Buffer(org.vertx.java.core.buffer.Buffer buffer) {
    this.buffer = buffer;
  }

  public Buffer(Env env, Value value) {
    buffer = new org.vertx.java.core.buffer.Buffer(value.toString());
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    if (Arrays.asList(Buffer.properties).contains(name.toString())) {
      return env.wrapJava(this).callMethod(env, name);
    }
    return null;
  }

  public Value getByte(Env env, NumberValue pos) {
    return env.wrapJava(buffer.getByte(pos.toInt()));
  }

  public Buffer setByte(Env env, NumberValue pos, Value value) {
    buffer.setByte(pos.toInt(), value.toJavaByte());
    return this;
  }

  public Buffer appendByte(Env env, Value value) {
    buffer.appendByte(value.toJavaByte());
    return this;
  }

  public double getDouble(Env env, NumberValue pos) {
    return buffer.getDouble(pos.toInt());
  }

  public Buffer setDouble(Env env, NumberValue pos, Value value) {
    buffer.setDouble(pos.toInt(), value.toDouble());
    return this;
  }

  public Buffer appendDouble(Env env, Value value) {
    buffer.appendDouble(value.toDouble());
    return this;
  }

  public float getFloat(Env env, NumberValue pos) {
    return buffer.getFloat(pos.toInt());
  }

  public Buffer setFloat(Env env, NumberValue pos, Value value) {
    buffer.setFloat(pos.toInt(), value.toJavaFloat());
    return this;
  }

  public Buffer appendFloat(Env env, Value value) {
    buffer.appendFloat(value.toJavaFloat());
    return this;
  }

  public int getInt(Env env, NumberValue pos) {
    return buffer.getInt(pos.toInt());
  }

  public Buffer setInt(Env env, NumberValue pos, Value value) {
    buffer.setInt(pos.toInt(), value.toInt());
    return this;
  }

  public Buffer appendInt(Env env, Value value) {
    buffer.appendInt(value.toInt());
    return this;
  }

  public long getLong(Env env, NumberValue pos) {
    return buffer.getLong(pos.toInt());
  }

  public Buffer setLong(Env env, NumberValue pos, Value value) {
    buffer.setLong(pos.toInt(), value.toLong());
    return this;
  }

  public Buffer appendLong(Env env, Value value) {
    buffer.appendLong(value.toLong());
    return this;
  }

  public short getShort(Env env, NumberValue pos) {
    return buffer.getShort(pos.toInt());
  }

  public Buffer setShort(Env env, NumberValue pos, Value value) {
    buffer.setShort(pos.toInt(), value.toJavaShort());
    return this;
  }

  public Buffer appendShort(Env env, Value value) {
    buffer.appendShort(value.toJavaShort());
    return this;
  }

  public Value getString(Env env, NumberValue start, NumberValue end) {
    return env.wrapJava(buffer.getString(start.toInt(), end.toInt()));
  }

  public Buffer setString(Env env, NumberValue pos, Value value, @Optional Value enc) {
    if (PhpTypes.notNull(enc)) {
      buffer.setString(pos.toInt(), value.toString(), enc.toString());
    }
    else {
      buffer.setString(pos.toInt(), value.toString());
    }
    return this;
  }

  public Buffer appendString(Env env, Value value, @Optional Value enc) {
    if (PhpTypes.notNull(enc)) {
      buffer.appendString(value.toString(), enc.toString());
    }
    else {
      buffer.appendString(value.toString());
    }
    return this;
  }

  public Buffer getBuffer(Env env, NumberValue start, NumberValue end) {
    return new Buffer(buffer.getBuffer(start.toInt(), end.toInt()));
  }

  public Buffer setBuffer(Env env, NumberValue pos, Value value) {
    buffer.setBuffer(pos.toInt(), ((Buffer) value.toJavaObject(env, Buffer.class)).__toVertxBuffer());
    return this;
  }

  public Buffer appendBuffer(Env env, Value value) {
    buffer.appendBuffer(((Buffer) value.toJavaObject(env, Buffer.class)).__toVertxBuffer());
    return this;
  }

  /**
   * Sets the given value at the given position.
   */
  public Buffer set(Env env, Value pos, Value value) {
    if (value.isLong()) {
      buffer.setLong(pos.toInt(), value.toLong());
    }
    else if (value.isDouble()) {
      buffer.setDouble(pos.toInt(), value.toDouble());
    }
    else if (value.isNumeric()) {
      buffer.setInt(pos.toInt(), value.toInt());
    }
    else if (value.isString()) {
      buffer.setString(pos.toInt(), value.toString());
    }
    else if (value.isObject()) {
      buffer.setBuffer(pos.toInt(), ((Buffer) value.toJavaObject(Env.getCurrent(), Buffer.class)).__toVertxBuffer());
    }
    return this;
  }

  /**
   * Appends the given value to the buffer.
   */
  public Buffer append(Env env, Value value) {
    if (value.isLong()) {
      buffer.appendLong(value.toLong());
    }
    else if (value.isDouble()) {
      buffer.appendDouble(value.toDouble());
    }
    else if (value.isNumeric()) {
      buffer.appendInt(value.toInt());
    }
    else if (value.isString()) {
      buffer.appendString(value.toString());
    }
    else if (value.isObject()) {
      buffer.appendBuffer((org.vertx.java.core.buffer.Buffer) value.toJavaObject(env, org.vertx.java.core.buffer.Buffer.class));
    }
    return this;
  }

  /**
   * Gets the byte value at the given position.
   */
  public Value get(Env env, Value pos) {
    return env.wrapJava(buffer.getByte(pos.toInt()));
  }

  @Override
  public boolean offsetExists(Value pos) {
    return buffer.length() > pos.toInt();
  }

  @Override
  public Value offsetGet(Value pos) {
    return get(Env.getCurrent(), pos);
  }

  @Override
  public Value offsetSet(Value pos, Value value) {
    set(Env.getCurrent(), pos, value);
    return null;
  }

  @Override
  public Value offsetUnset(Value pos) {
    return null;
  }

  @Override
  public int count() {
    return buffer.length();
  }

  public int length() {
    return buffer.length();
  }

  /**
   * Copies the buffer.
   */
  public Value copy(Env env) {
    return env.wrapJava(new Buffer(buffer.copy()));
  }

  public String toString() {
    return buffer.toString();
  }

  public Value toString(Env env) {
    return env.wrapJava(toString());
  }

  public String __toString() {
    return toString();
  }

  public Value toString(Env env, Value enc) {
    if (PhpTypes.isNull(env, enc)) {
      return toString(env);
    }
    return env.wrapJava(buffer.toString(enc.toString()));
  }

  public org.vertx.java.core.buffer.Buffer __toVertxBuffer() {
    return buffer;
  }

}

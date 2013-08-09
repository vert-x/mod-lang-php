package com.blankstyle.vertx.php.buffer;

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
public class Buffer implements ArrayAccess, Countable {

  private org.vertx.java.core.buffer.Buffer buffer;

  public Buffer() {
    buffer = new org.vertx.java.core.buffer.Buffer();
  }

  public Buffer(Env env) {
    buffer = new org.vertx.java.core.buffer.Buffer();
  }

  public Buffer(org.vertx.java.core.buffer.Buffer buffer) {
    this.buffer = buffer;
  }

  public Buffer(Env env, StringValue value) {
    buffer = new org.vertx.java.core.buffer.Buffer(value.toString());
  }

  public Value getByte(Env env, NumberValue pos) {
    return env.wrapJava(buffer.getByte(pos.toInt()));
  }

  public Buffer setByte(Env env, NumberValue pos, Value value) {
    buffer.setByte(pos.toInt(), value.toJavaByte());
    return this;
  }

  public Buffer appendByte(Env env, NumberValue pos, Value value) {
    buffer.appendByte(value.toJavaByte());
    return this;
  }

  public float getFloat(Env env, NumberValue pos) {
    return buffer.getFloat(pos.toInt());
  }

  public Buffer setFloat(Env env, NumberValue pos, Value value) {
    buffer.setFloat(pos.toInt(), value.toJavaFloat());
    return this;
  }

  public Buffer appendFloat(Env env, NumberValue pos, Value value) {
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

  public Buffer appendInt(Env env, NumberValue pos, Value value) {
    buffer.appendInt(value.toInt());
    return this;
  }

  public Value getString(Env env, NumberValue start, NumberValue end) {
    return env.wrapJava(buffer.getString(start.toInt(), end.toInt()));
  }

  public Buffer setString(Env env, NumberValue pos, Value value, @Optional StringValue enc) {
    if (PhpTypes.notNull(enc)) {
      buffer.setString(pos.toInt(), value.toString(), enc.toString());
    }
    else {
      buffer.setString(pos.toInt(), value.toString());
    }
    return this;
  }

  public Buffer appendString(Env env, NumberValue pos, Value value, @Optional StringValue enc) {
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
    buffer.setBuffer(pos.toInt(), ((Buffer) value.toJavaObject(env, Buffer.class)).getWrapped());
    return this;
  }

  public Buffer appendBuffer(Env env, NumberValue pos, Value value) {
    buffer.appendBuffer(((Buffer) value.toJavaObject(env, Buffer.class)).getWrapped());
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
      buffer.setBuffer(pos.toInt(), ((Buffer) value.toJavaObject(Env.getCurrent(), Buffer.class)).getWrapped());
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
    return env.wrapJava(buffer.toString());
  }

  public Value toString(Env env, StringValue enc) {
    if (PhpTypes.isNull(env, enc)) {
      return toString(env);
    }
    return env.wrapJava(buffer.toString(enc.toString()));
  }

  org.vertx.java.core.buffer.Buffer getWrapped() {
    return buffer;
  }

}

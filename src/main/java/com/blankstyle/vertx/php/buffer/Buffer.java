package com.blankstyle.vertx.php.buffer;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x Buffer.
 */
public class Buffer {

  private org.vertx.java.core.buffer.Buffer buffer;

  public Buffer(org.vertx.java.core.buffer.Buffer buffer) {
    this.buffer = buffer;
  }

  public Buffer(Env env, org.vertx.java.core.buffer.Buffer buffer, @Optional Value bytes) {
    if (!bytes.isDefault()) {
      buffer = new org.vertx.java.core.buffer.Buffer(bytes.toString());
    }
    else {
      buffer = new org.vertx.java.core.buffer.Buffer();
    }
  }

  public Value append(Env env, Value value) {
    if (value.isA("Buffer")) {
      return appendBuffer(env, (ObjectValue) value);
    }
    else if (value.isDouble()) {
      return appendDouble(env, value);
    }
    else if (value.isLong()) {
      return appendLong(env, value);
    }
    else if (value.isNumeric()) {
      return appendInt(env, value);
    }
    else if (value.isString()) {
      return appendString(env, value);
    }
    return env.wrapJava(this);
  }

  public Value appendBuffer(Env env, ObjectValue value) {
    return env.wrapJava(this);
  }

  public Value appendByte(Env env, Value value) {
    buffer.appendByte(value.toJavaByte());
    return env.wrapJava(this);
  }

  public Value appendBytes(Env env, Value value) {
    buffer.appendBytes(value.toString().getBytes());
    return env.wrapJava(this);
  }

  public Value appendDouble(Env env, Value value) {
    buffer.appendDouble(value.toJavaDouble());
    return env.wrapJava(this);
  }

  public Value appendFloat(Env env, Value value) {
    buffer.appendFloat(value.toJavaFloat());
    return env.wrapJava(this);
  }

  public Value appendInt(Env env, Value value) {
    buffer.appendInt(value.toJavaInteger());
    return env.wrapJava(this);
  }

  public Value appendLong(Env env, Value value) {
    buffer.appendLong(value.toJavaLong());
    return env.wrapJava(this);
  }

  public Value appendShort(Env env, Value value) {
    buffer.appendShort(value.toJavaShort());
    return env.wrapJava(this);
  }

  public Value appendString(Env env, Value value) {
    buffer.appendString(value.toJavaString());
    return env.wrapJava(this);
  }

  public NumberValue getInt(Env env, int pos) {
    return LongValue.create(buffer.getInt(pos));
  }

  public StringValue getString(Env env, int start, int end) {
    return (StringValue) StringValue.create(buffer.getString(start, end));
  }

  public NumberValue length(Env env) {
    return LongValue.create(buffer.length());
  }

  public Value setDouble(Env env, int pos, Value value) {
    buffer.setDouble(pos, value.toJavaDouble());
    return env.wrapJava(this);
  }

  public Value setFloat(Env env, int pos, Value value) {
    buffer.setFloat(pos, value.toJavaFloat());
    return env.wrapJava(this);
  }

  public Value setInt(Env env, int pos, Value value) {
    buffer.setInt(pos, value.toJavaInteger());
    return env.wrapJava(this);
  }

  public Value setLong(Env env, int pos, Value value) {
    buffer.setLong(pos, value.toJavaLong());
    return env.wrapJava(this);
  }

  public Value setShort(Env env, int pos, Value value) {
    buffer.setLong(pos, value.toJavaShort());
    return env.wrapJava(this);
  }

  public Value setString(Env env, int pos, Value value) {
    buffer.setString(pos, value.toJavaString());
    return env.wrapJava(this);
  }

  public StringValue toString(Env env) {
    return (StringValue) StringValue.create(buffer.toString());
  }

}

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
package com.blankstyle.vertx.php.util;

import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.DoubleValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * Static class for validating PHP variable values.
 *
 * This is required for validating values as passed to Java
 * callbacks by Quercus. Often times, while a value may not
 * be "null" it may actually represent a "NULL" PHP value,
 * so this class helps validate the values of PHP variables
 * as represented in Java objects.
 *
 * @author Jordan Halterman
 */
public class PhpTypes {

  private PhpTypes() {
  }

  public static boolean isNull(Value value) {
    return value == null || value.isNull();
  }

  public static boolean isNull(Env env, Value value) {
    return PhpTypes.isNull(value);
  }

  public static void assertNull(Env env, Value value) {
    if (!PhpTypes.isNull(value)) {
      env.error("Object is not null.");
    }
  }

  public static void assertNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isNull(value)) {
      env.error(String.format(message, args));
    }
  }

  public static boolean notNull(Value value) {
    return !PhpTypes.isNull(value);
  }

  public static boolean notNull(Env env, Value value) {
    return !PhpTypes.isNull(value);
  }

  public static void assertNotNull(Env env, Value value) {
    if (!PhpTypes.notNull(value)) {
      env.error("Object is null.");
    }
  }

  public static void assertNotNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notNull(value)) {
      env.error(String.format(message, args));
    }
  }

  public static boolean isDefault(Value value) {
    return value != null && value.isDefault();
  }

  public static boolean isDefault(Env env, Value value) {
    return PhpTypes.isDefault(value);
  }

  public static void assertDefault(Env env, Value value) {
    if (!PhpTypes.isDefault(value)) {
      env.error("Object is not default.");
    }
  }

  public static void assertDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isDefault(value)) {
      env.error(String.format(message, args));
    }
  }

  public static boolean notDefault(Value value) {
    return value == null || !value.isDefault();
  }

  public static boolean notDefault(Env env, Value value) {
    return PhpTypes.notDefault(value);
  }

  public static void assertNotDefault(Env env, Value value) {
    if (!PhpTypes.notDefault(value)) {
      env.error("Object is default.");
    }
  }

  public static void assertNotDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notDefault(value)) {
      env.error(String.format(message, args));
    }
  }

  public static boolean isCallable(Value value) {
    return !PhpTypes.isCallable(Env.getCurrent(), value);
  }

  public static boolean isCallable(Env env, Value value) {
    return PhpTypes.notNull(value) && value.isCallable(env, false, null);
  }

  public static void assertCallable(Env env, Value value) {
    if (!PhpTypes.isCallable(value)) {
      env.error("Object is not callable.");
    }
  }

  public static void assertCallable(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isCallable(value)) {
      env.error(String.format(message, args));
    }
  }

  public static Callable toCallable(Value value) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(Env.getCurrent(), false);
  }

  public static Callable toCallable(Env env, Value value) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(env, false);
  }

  public static Callable toCallable(Value value, boolean isOptional) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(Env.getCurrent(), isOptional);
  }

  public static Callable toCallable(Env env, Value value, boolean isOptional) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(env, isOptional);
  }

  public static boolean isString(Value value) {
    return PhpTypes.notNull(value) && value.isString();
  }

  public static String toString(Value value) {
    return value.toString();
  }

  public static StringValue toStringValue(Value value) {
    return Env.getCurrent().createString(value.toString());
  }

  public static boolean isBinary(Value value) {
    return PhpTypes.notNull(value) && value.isBinary();
  }

  public static StringValue toBinaryValue(Value value) {
    return value.toBinaryValue();
  }

  public static boolean isNumeric(Value value) {
    return PhpTypes.notNull(value) && value.isNumeric();
  }

  public static int toInt(Value value) {
    return value.toInt();
  }

  public static boolean isBoolean(Value value) {
    return PhpTypes.notNull(value) && value.isBoolean();
  }

  public static BooleanValue toBooleanValue(Value value) {
    return BooleanValue.create(value.toBoolean());
  }

  public static BooleanValue toBooleanValue(boolean value) {
    return BooleanValue.create(value);
  }

  public static boolean isDouble(Value value) {
    return PhpTypes.notNull(value) && value.isDouble();
  }

  public static double toDouble(Value value) {
    return value.toDouble();
  }

  public static DoubleValue toDoubleValue(Value value) {
    return value.toDoubleValue();
  }

  public static boolean isLong(Value value) {
    return PhpTypes.notNull(value) && value.isLong();
  }

  public static long toLong(Value value) {
    return value.toLong();
  }

  public static LongValue toLongValue(Value value) {
    return value.toLongValue();
  }

  public static boolean isArray(Value value) {
    return PhpTypes.notNull(value) && value.isArray();
  }

  public static ArrayValue toArrayValue(Value value) {
    return value.toArray();
  }

  public static ArrayValue toArrayValue(Env env, Value value) {
    return value.toArrayValue(env);
  }

  public static boolean isObject(Value value) {
    return PhpTypes.notNull(value) && value.isObject();
  }

}

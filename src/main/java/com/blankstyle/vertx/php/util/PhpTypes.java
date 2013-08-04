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

import com.caucho.quercus.Location;
import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

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

  /**
   * Validates that a PHP value is null.
   */
  public static boolean isNull(Value value) {
    return value == null || value.isNull();
  }

  /**
   * Validates that a PHP value is null.
   */
  public static boolean isNull(Env env, Value value) {
    return PhpTypes.isNull(value);
  }

  /**
   * Asserts that a PHP value is null.
   */
  public static void assertNull(Env env, Value value) {
    if (!PhpTypes.isNull(value)) {
      env.error(PhpTypes.buildErrorMessage(env, "Object is not null.", new Object[]{}));
    }
  }

  /**
   * Asserts that a PHP value is null.
   */
  public static void assertNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isNull(value)) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Validates that a PHP value is not null.
   */
  public static boolean notNull(Value value) {
    return !PhpTypes.isNull(value);
  }

  /**
   * Validates that a PHP value is not null.
   */
  public static boolean notNull(Env env, Value value) {
    return !PhpTypes.isNull(value);
  }

  /**
   * Asserts that a PHP value is not null.
   */
  public static void assertNotNull(Env env, Value value) {
    if (!PhpTypes.notNull(value)) {
      env.error(PhpTypes.buildErrorMessage(env, "Object is null.", new Object[]{}));
    }
  }

  /**
   * Asserts that a PHP value is not null.
   */
  public static void assertNotNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notNull(value)) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Validates that a PHP value is set to its default value.
   */
  public static boolean isDefault(Value value) {
    return value != null && value.isDefault();
  }

  /**
   * Validates that a PHP value is set to its default value.
   */
  public static boolean isDefault(Env env, Value value) {
    return PhpTypes.isDefault(value);
  }

  /**
   * Asserts that a PHP value is set to its default value.
   */
  public static void assertDefault(Env env, Value value) {
    if (!PhpTypes.isDefault(value)) {
      env.error(PhpTypes.buildErrorMessage(env, "Object is not default.", new Object[]{}));
    }
  }

  /**
   * Asserts that a PHP value is set to its default value.
   */
  public static void assertDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isDefault(value)) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Validates that a PHP value is not set to its default value.
   */
  public static boolean notDefault(Value value) {
    return value == null || !value.isDefault();
  }

  /**
   * Validates that a PHP value is not set to its default value.
   */
  public static boolean notDefault(Env env, Value value) {
    return PhpTypes.notDefault(value);
  }

  /**
   * Asserts that a PHP value is not set to its default value.
   */
  public static void assertNotDefault(Env env, Value value) {
    if (!PhpTypes.notDefault(value)) {
      env.error(PhpTypes.buildErrorMessage(env, "Object is default.", new Object[]{}));
    }
  }

  /**
   * Asserts that a PHP value is not set to its default value.
   */
  public static void assertNotDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notDefault(value)) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Validates that a PHP value is callable.
   */
  public static boolean isCallable(Env env, Value value) {
    return PhpTypes.notNull(value) && value.isCallable(env, false, null);
  }

  /**
   * Asserts that a PHP value is callable.
   */
  public static void assertCallable(Env env, Value value) {
    if (!PhpTypes.isCallable(env, value)) {
      env.error(PhpTypes.buildErrorMessage(env, "Object is not callable.", new Object[]{}));
    }
  }

  /**
   * Asserts that a PHP value is callable.
   */
  public static void assertCallable(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isCallable(env, value)) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Value value) {
    return value.toCallable(Env.getCurrent(), false);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Env env, Value value) {
    return value.toCallable(env, false);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Value value, boolean isOptional) {
    return value.toCallable(Env.getCurrent(), isOptional);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Env env, Value value, boolean isOptional) {
    return value.toCallable(env, isOptional);
  }

  /**
   * Asserts that a value is a boolean.
   */
  public static void assertBoolean(Env env, Value value) {
    if (!value.isBoolean()) {
      env.error(PhpTypes.buildErrorMessage(env, "Invalid boolean value.", new Object[]{}));
    }
  }

  /**
   * Asserts that a value is a boolean.
   */
  public static void assertBoolean(Env env, Value value, String message, Object... args) {
    if (!value.isBoolean()) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Asserts that a value is an integer.
   */
  public static void assertInteger(Env env, Value value) {
    if (!value.isNumeric()) {
      env.error(PhpTypes.buildErrorMessage(env, "Invalid integer value.", new Object[]{}));
    }
  }

  /**
   * Assets that a value is an integer.
   */
  public static void assertInteger(Env env, Value value, String message, Object... args) {
    if (!value.isNumeric()) {
      env.error(PhpTypes.buildErrorMessage(env, message, args));
    }
  }

  /**
   * Builds a PHP error message.
   */
  public static String buildErrorMessage(Env env, String message, Object... args) {
    if (message.endsWith(".")) {
      message = message.substring(0, message.length() - 1);
    }

    Location location = env.getLocation();
    StringBuilder builder = new StringBuilder();
    builder.append(String.format(message, args));

    String fileName = location.getUserPath();
    if (fileName == null) {
      fileName = location.getFileName();
    }
    if (fileName != null) {
      builder.append(" in ").append(fileName);
    }

    builder.append(" on line ").append(location.getLineNumber()).append(".");
    return builder.toString();
  }

}

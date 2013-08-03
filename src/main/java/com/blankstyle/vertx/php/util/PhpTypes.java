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
      env.error("Object is not null.");
    }
  }

  /**
   * Asserts that a PHP value is null.
   */
  public static void assertNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isNull(value)) {
      env.error(String.format(message, args));
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
      env.error("Object is null.");
    }
  }

  /**
   * Asserts that a PHP value is not null.
   */
  public static void assertNotNull(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notNull(value)) {
      env.error(String.format(message, args));
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
      env.error("Object is not default.");
    }
  }

  /**
   * Asserts that a PHP value is set to its default value.
   */
  public static void assertDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isDefault(value)) {
      env.error(String.format(message, args));
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
      env.error("Object is default.");
    }
  }

  /**
   * Asserts that a PHP value is not set to its default value.
   */
  public static void assertNotDefault(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.notDefault(value)) {
      env.error(String.format(message, args));
    }
  }

  /**
   * Validates that a PHP value is callable.
   */
  public static boolean isCallable(Value value) {
    return !PhpTypes.isCallable(Env.getCurrent(), value);
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
    if (!PhpTypes.isCallable(value)) {
      env.error("Object is not callable.");
    }
  }

  /**
   * Asserts that a PHP value is callable.
   */
  public static void assertCallable(Env env, Value value, String message, Object... args) {
    if (!PhpTypes.isCallable(value)) {
      env.error(String.format(message, args));
    }
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Value value) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(Env.getCurrent(), false);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Env env, Value value) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(env, false);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Value value, boolean isOptional) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(Env.getCurrent(), isOptional);
  }

  /**
   * Converts a basic PHP value to a callable.
   */
  public static Callable toCallable(Env env, Value value, boolean isOptional) {
    if (!PhpTypes.isCallable(value)) {
      throw new IllegalArgumentException();
    }
    return value.toCallable(env, isOptional);
  }

}

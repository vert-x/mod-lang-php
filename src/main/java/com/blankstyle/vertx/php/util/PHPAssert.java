package com.blankstyle.vertx.php.util;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;

/**
 * @author Jordan Halterman
 */
public class PHPAssert {

  private PHPAssert() {
  }

  public static void isNull(Value value) {
    if (value != null || !value.isNull()) {
      throw new IllegalArgumentException();
    }
  }

  public static void isNull(Env env, Value value) {
    if (value != null || !value.isNull()) {
      throw new IllegalArgumentException();
    }
  }

  public static void isNull(Value value, String message, Object... args) {
    if (value != null || !value.isNull()) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

  public static void isNull(Env env, Value value, String message, Object... args) {
    if (value != null || !value.isNull()) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

  public static void notNull(Value value) {
    if (value == null || value.isNull()) {
      throw new IllegalArgumentException();
    }
  }

  public static void notNull(Env env, Value value) {
    if (value == null || value.isNull()) {
      throw new IllegalArgumentException();
    }
  }

  public static void notNull(Value value, String message, Object... args) {
    if (value == null || value.isNull()) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

  public static void notNull(Env env, Value value, String message, Object... args) {
    if (value == null || value.isNull()) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

  public static void isCallable(Value value) {
    PHPAssert.isNull(value);
    if (!value.isCallable(Env.getCurrent(), false, null)) {
      throw new IllegalArgumentException();
    }
  }

  public static void isCallable(Env env, Value value) {
    PHPAssert.isNull(value);
    if (!value.isCallable(env, false, null)) {
      throw new IllegalArgumentException();
    }
  }

  public static void isCallable(Value value, String message, Object... args) {
    PHPAssert.isNull(value);
    if (!value.isCallable(Env.getCurrent(), false, null)) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

  public static void isCallable(Env env, Value value, String message, Object... args) {
    PHPAssert.isNull(value);
    if (!value.isCallable(env, false, null)) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

}

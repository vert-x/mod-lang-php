package com.blankstyle.vertx.php.util;

/**
 * @author Jordan Halterman
 */
public final class Assert {

  private Assert() {
  }

  public static void isNull(Object object) {
    if (object != null) {
      throw new IllegalArgumentException();
    }
  }

  public static void isNull(Object object, String message, Object... args) {
    if (object != null) {
      throw new IllegalArgumentException(String.format(message, args));
    }
  }

}

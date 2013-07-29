package com.blankstyle.vertx.php;

/**
 * A static Vertx instance access class.
 */
public final class Vertx {

  private static org.vertx.java.core.Vertx instance;

  public static void init(org.vertx.java.core.Vertx instance) {
    if (Vertx.instance == null) {
      Vertx.instance = instance;
    }
  }

}

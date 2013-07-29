package com.blankstyle.vertx.php;

/**
 * A static Container instance access class.
 */
public final class Container {

  private static org.vertx.java.platform.Container instance;

  public static void init(org.vertx.java.platform.Container instance) {
    if (Container.instance == null) {
      Container.instance = instance;
    }
  }

}

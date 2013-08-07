package com.blankstyle.vertx.php.testtools;

import org.vertx.testtools.VertxAssert;

import com.blankstyle.vertx.php.PhpVerticleFactory;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.ObjectValue;

/**
 * A PHP test runner.
 *
 * @author Jordan Halterman
 */
public class PhpTestRunner {

  /**
   * Runs a PHP test.
   */
  public static void run(Env env, ObjectValue test) {
    String methodName = PhpVerticleFactory.container.config().getString("methodName");
    VertxAssert.initialize(PhpVerticleFactory.vertx);
    test.callMethod(env, env.createString(methodName));
  }

}

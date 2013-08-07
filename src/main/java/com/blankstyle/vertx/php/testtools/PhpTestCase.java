package com.blankstyle.vertx.php.testtools;

import org.vertx.testtools.VertxAssert;

import com.caucho.quercus.annotation.ClassImplementation;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.annotation.This;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A test case exposed to PHP.
 *
 * @author Jordan Halterman
 */
@ClassImplementation
public class PhpTestCase {

  public static Value __construct(Env env, @This ObjectValue obj) {
    return obj;
  }

  /**
   * Asserts that the given condition is true.
   *
   * @param obj
   * @param condition
   * @param message
   */
  public static void assertTrue(Env env, @This ObjectValue obj, BooleanValue condition, @Optional StringValue message) {
    if (!message.isDefault()) {
      VertxAssert.assertTrue(message.toString(), condition.toBoolean());
    }
    else {
      VertxAssert.assertTrue(condition.toBoolean());
    }
  }

}

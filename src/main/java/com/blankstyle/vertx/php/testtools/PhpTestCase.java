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
package com.blankstyle.vertx.php.testtools;

import org.junit.Assert;
import org.vertx.testtools.VertxAssert;

import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.annotation.ClassImplementation;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.annotation.This;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * An abstract PHP test case. Vert.x PHP makes this class available within the
 * PHP namespace as Vertx\Test\PhpTestCase. It should be extended to provide
 * PHP integration tests for Vert.x. Any public methods that are prefixed with
 * "test" will be run as tests.
 *
 * Example:
 *
 * use Vertx\Test\PhpTestCase;
 * use Vertx\Test\TestRunner;
 *
 * class MyTestCase extends PhpTestCase {
 *   public function testFoo() {
 *     // Do test foo.
 *   }
 * 
 *   public function testBar() {
 *     // Do test bar.
 *   }
 * }
 *
 * TestRunner::run(new MyTestCase());
 * 
 * @author Jordan Halterman
 */
@ClassImplementation
public abstract class PhpTestCase {

  public static Value __construct(Env env, @This ObjectValue obj) {
    return obj;
  }

  /**
   * Asserts that the given condition is true.
   * 
   * @param condition The condition to evaluate.
   * @param message An optional assertion message.
   */
  public static void assertTrue(Env env, @This ObjectValue obj, BooleanValue condition, @Optional StringValue message) {
    PhpTestCase.doAssertTrue(condition.toBoolean(), message);
  }

  private static void doAssertTrue(boolean condition, StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.assertTrue(message.toString(), condition);
    }
    else {
      VertxAssert.assertTrue(condition);
    }
  }

  /**
   * Asserts that the given condition is false.
   * 
   * @param condition The condition to evaluate.
   * @param message An optional assertion message.
   */
  public static void assertFalse(Env env, @This ObjectValue obj, BooleanValue condition, @Optional StringValue message) {
    PhpTestCase.doAssertFalse(condition.toBoolean(), message);
  }

  private static void doAssertFalse(boolean condition, StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.assertFalse(message.toString(), condition);
    }
    else {
      VertxAssert.assertFalse(condition);
    }
  }

  /**
   * Asserts that the given value is null.
   * 
   * @param value A PHP value.
   */
  public static void assertNull(Env env, @This ObjectValue obj, Value value) {
    VertxAssert.assertTrue(PhpTypes.isNull(value));
  }

  /**
   * Asserts that the given value is not null.
   * 
   * @param value A PHP value.
   */
  public static void assertNotNull(Env env, @This ObjectValue obj, Value value) {
    VertxAssert.assertTrue(PhpTypes.notNull(value));
  }

  /**
   * Asserts that the given pair of values are equal.
   * 
   * @param expected The expected value.
   * @param actual The actual value to compare.
   * @param message An optional assertion message.
   */
  public static void assertEquals(Env env, @This ObjectValue obj, Value expected, Value actual, @Optional StringValue message) {
    if (expected.isArray()) {
      throw new UnsupportedOperationException();
    }
    if (expected.isBoolean()) {
      if (!actual.isBoolean()) {
        PhpTestCase.doAssertTrue(false, message);
      }
      else if (expected.toBoolean()) {
        PhpTestCase.doAssertTrue(actual.toBoolean(), message);
      }
      else {
        PhpTestCase.doAssertFalse(actual.toBoolean(), message);
      }
    }
    else if (expected.isDouble()) {
      PhpTestCase.doAssertEquals(expected.toDouble(), actual.toDouble(), message);
    }
    else if (expected.isLong()) {
      PhpTestCase.doAssertEquals(expected.toLong(), actual.toLong(), message);
    }
    else if (expected.isNull()) {
      PhpTestCase.doAssertTrue(actual.isNull(), message);
    }
    else if (expected.isString()) {
      if (!actual.isString()) {
        PhpTestCase.doAssertTrue(false, message);
      }
      else {
        PhpTestCase.doAssertEquals(expected.toString(), actual.toString(), message);
      }
    }
    else if (expected.isObject()) {
      if (!actual.isObject()) {
        PhpTestCase.doAssertTrue(false, message);
      }
      else {
        PhpTestCase.doAssertEquals(expected.toJavaObject(), actual.toJavaObject(), message);
      }
    }
    else {
      throw new UnsupportedOperationException("Unknown data type.");
    }
  }

  private static <T> void doAssertEquals(T expected, T actual, StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.assertEquals(message.toString(), expected, actual);
    }
    else {
      VertxAssert.assertEquals(expected, actual);
    }
  }

  /**
   * Asserts that the given pair of values are not equal.
   * 
   * @param expected The expected value.
   * @param actual The actual value to compare.
   * @param message An optional assertion message.
   */
  public static void assertNotEquals(Env env, @This ObjectValue obj, Value expected, Value actual, @Optional StringValue message) {
    if (expected.isArray()) {
      throw new UnsupportedOperationException();
    }
    if (expected.isBoolean()) {
      if (actual.isBoolean()) {
        if (expected.toBoolean()) {
          PhpTestCase.doAssertFalse(actual.toBoolean(), message);
        }
        else {
          PhpTestCase.doAssertTrue(actual.toBoolean(), message);
        }
      }
    }
    else if (expected.isDouble()) {
      PhpTestCase.doAssertNotEquals(expected.toDouble(), actual.toDouble(), message);
    }
    else if (expected.isLong()) {
      PhpTestCase.doAssertNotEquals(expected.toLong(), actual.toLong(), message);
    }
    else if (expected.isNull()) {
      PhpTestCase.doAssertFalse(actual.isNull(), message);
    }
    else if (expected.isString()) {
      if (actual.isString()) {
        PhpTestCase.doAssertNotEquals(expected.toString(), actual.toString(), message);
      }
    }
    else if (expected.isObject()) {
      if (actual.isObject()) {
        PhpTestCase.doAssertNotEquals(expected.toJavaObject(), actual.toJavaObject(), message);
      }
    }
    else {
      throw new UnsupportedOperationException("Unknown data type.");
    }
  }

  private static <T> void doAssertNotEquals(T expected, T actual, StringValue message) {
    // In this case, if an AssertionError is thrown then this assertion actually
    // passed. Catch the exception. Otherwise, cause an AssertionError.
    try {
      Assert.assertEquals(expected, actual);
      PhpTestCase.doAssertTrue(false, message);
    }
    catch (AssertionError ignored) {
    }
  }

  /**
   * Asserts that the given pair of values are the same.
   * 
   * @param expected The expected value.
   * @param actual The value to compare.
   * @param message An optional assertion message.
   */
  public static void assertSame(Env env, @This ObjectValue obj, Value expected, Value actual, @Optional StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.assertSame(message.toString(), expected.toValue(), actual.toValue());
    }
    else {
      VertxAssert.assertSame(expected.toValue(), actual.toValue());
    }
  }

  /**
   * Asserts that the given pair of values are not the same.
   * 
   * @param expected The expected value.
   * @param actual The value to compare.
   * @param message An optional assertion message.
   */
  public static void assertNotSame(Env env, @This ObjectValue obj, Value expected, Value actual, @Optional StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.assertNotSame(message.toString(), expected.toValue(), actual.toValue());
    }
    else {
      VertxAssert.assertNotSame(expected.toValue(), actual.toValue());
    }
  }

  /**
   * Fails the test.
   * 
   * @param message An optional failure message.
   */
  public static void fail(Env env, @This ObjectValue obj, @Optional StringValue message) {
    if (PhpTypes.notNull(message)) {
      VertxAssert.fail(message.toString());
    }
    else {
      VertxAssert.fail();
    }
  }

}

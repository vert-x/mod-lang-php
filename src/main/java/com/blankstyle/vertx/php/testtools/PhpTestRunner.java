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

import org.vertx.testtools.VertxAssert;

import com.blankstyle.vertx.php.PhpVerticleFactory;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.ObjectValue;

/**
 * A PHP test runner.
 *
 * To use the test runner within a PHP script, simply call the static run()
 * method on the Vertx\Test\TestRunner class in the PHP namespace.
 *
 * Vertx\Test\TestRunner::run(new MyTestCase());
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
    test.callMethod(env, env.createString("setUp"));
    test.callMethod(env, env.createString(methodName));
    test.callMethod(env, env.createString("tearDown"));
  }

}

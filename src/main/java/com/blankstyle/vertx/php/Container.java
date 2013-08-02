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
package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.ArrayValueImpl;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * A static Container instance access class.
 */
public final class Container {

  private static org.vertx.java.platform.Container instance;

  /**
   * Initializes the container.
   */
  public static void init(org.vertx.java.platform.Container instance) {
    if (Container.instance == null) {
      Container.instance = instance;
    }
  }

  /**
   * Deploys a module.
   */
  @SuppressWarnings("unchecked")
  public static void deployModule(Env env, StringValue moduleName, @Optional ArrayValue config, @Optional("1") NumberValue instances, @Optional Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Handler argument to Container::deployModule() must be callable.");
    }
    boolean hasConfig = config != null && !config.isDefault();
    boolean hasHandler = handler != null && !handler.isDefault();
    if (hasConfig && hasHandler) {
      Container.instance.deployModule(moduleName.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt(), new Handler<AsyncResult<String>>(env, (Callback) handler));
    }
    else if (hasConfig) {
      Container.instance.deployModule(moduleName.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt());
    }
    else if (hasHandler) {
      Container.instance.deployModule(moduleName.toString(), instances.toInt(), new Handler<AsyncResult<String>>(env, (Callback) handler));
    }
    else {
      Container.instance.deployModule(moduleName.toString(), instances.toInt());
    }
  }

  /**
   * Undeploys a module.
   */
  public static void undeployModule(Env env, StringValue deploymentID, @Optional Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Handler argument to Container::undeployModule() must be callable.");
    }
    if (handler != null && !handler.isDefault()) {
      Container.instance.undeployModule(deploymentID.toString(), new Handler<AsyncResult<Void>>(env, (Callback) handler));
    }
    else {
      Container.instance.undeployModule(deploymentID.toString());
    }
  }

  /**
   * Deploys a verticle.
   */
  @SuppressWarnings("unchecked")
  public static void deployVerticle(Env env, StringValue main, @Optional ArrayValue config, @Optional("1") NumberValue instances, @Optional Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Handler argument to Container::deployVerticle() must be callable.");
    }
    boolean hasConfig = config != null && !config.isDefault();
    boolean hasHandler = handler != null && !handler.isDefault();
    if (hasConfig && hasHandler) {
      Container.instance.deployVerticle(main.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt(), new Handler<AsyncResult<String>>(env, (Callback) handler));
    }
    else if (hasConfig) {
      Container.instance.deployVerticle(main.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt());
    }
    else if (hasHandler) {
      Container.instance.deployVerticle(main.toString(), instances.toInt(), new Handler<AsyncResult<String>>(env, (Callback) handler));
    }
    else {
      Container.instance.deployVerticle(main.toString(), instances.toInt());
    }
  }

  /**
   * Undeploys a verticle.
   */
  public static void undeployVerticle(Env env, StringValue deploymentID, @Optional Value handler) {
    if (handler != null && !handler.isNull() && !handler.isCallable(env, false, null)) {
      env.error("Handler argument to Container::undeployVerticle() must be callable.");
    }
    if (handler != null && !handler.isDefault()) {
      Container.instance.undeployVerticle(deploymentID.toString(), new Handler<AsyncResult<Void>>(env, (Callback) handler));
    }
    else {
      Container.instance.undeployVerticle(deploymentID.toString());
    }
  }

  /**
   * Deploys a verticle.
   */
  @SuppressWarnings("unchecked")
  public static void deployWorkerVerticle(Env env, StringValue main, @Optional ArrayValue config, @Optional("1") NumberValue instances) {
    boolean hasConfig = config != null && !config.isDefault();
    if (hasConfig) {
      Container.instance.deployWorkerVerticle(main.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt());
    }
    else {
      Container.instance.deployWorkerVerticle(main.toString(), instances.toInt());
    }
  }

  /**
   * Returns the current Vertx container environment.
   */
  public static ArrayValue env(Env env) {
    Map<String, String> map = Container.instance.env();
    ArrayValue array = new ArrayValueImpl();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      array.append(env.createString(entry.getKey()), env.createString(entry.getValue()));
    }
    return array;
  }

  /**
   * Exits the container.
   */
  public static void exit() {
    Container.instance.exit();
  }

  /**
   * Returns the Vertx logger.
   */
  public static Logger logger() {
    return Container.instance.logger();
  }

  /**
   * Returns the Vertx configuration.
   */
  public static ArrayValue config(Env env) {
    JsonObject config = Container.instance.config();
    ArrayValue array = new ArrayValueImpl();
    for (Map.Entry<String, Object> entry : config.toMap().entrySet()) {
      array.append(env.createString(entry.getKey()), env.wrapJava(entry.getValue()));
    }
    return array;
  }

}

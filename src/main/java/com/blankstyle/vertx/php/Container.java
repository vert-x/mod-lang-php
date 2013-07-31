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
  public static void deployModule(final Env env, final StringValue moduleName, @Optional final ArrayValue config, @Optional("1") final NumberValue instances, @Optional final Callback handler) {
    boolean hasConfig = config != null && !config.isDefault();
    boolean hasHandler = handler != null && !handler.isDefault();
    if (hasConfig && hasHandler) {
      Container.instance.deployModule(moduleName.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt(), new Handler<AsyncResult<String>>(env, handler));
    }
    else if (hasConfig) {
      Container.instance.deployModule(moduleName.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt());
    }
    else if (hasHandler) {
      Container.instance.deployModule(moduleName.toString(), instances.toInt(), new Handler<AsyncResult<String>>(env, handler));
    }
    else {
      Container.instance.deployModule(moduleName.toString(), instances.toInt());
    }
  }

  /**
   * Undeploys a module.
   */
  public static void undeployModule(final Env env, StringValue deploymentID, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      Container.instance.undeployModule(deploymentID.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      Container.instance.undeployModule(deploymentID.toString());
    }
  }

  /**
   * Deploys a verticle.
   */
  @SuppressWarnings("unchecked")
  public static void deployVerticle(final Env env, final StringValue main, @Optional final ArrayValue config, @Optional("1") final NumberValue instances, @Optional final Callback handler) {
    boolean hasConfig = config != null && !config.isDefault();
    boolean hasHandler = handler != null && !handler.isDefault();
    if (hasConfig && hasHandler) {
      Container.instance.deployVerticle(main.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt(), new Handler<AsyncResult<String>>(env, handler));
    }
    else if (hasConfig) {
      Container.instance.deployVerticle(main.toString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toInt());
    }
    else if (hasHandler) {
      Container.instance.deployVerticle(main.toString(), instances.toInt(), new Handler<AsyncResult<String>>(env, handler));
    }
    else {
      Container.instance.deployVerticle(main.toString(), instances.toInt());
    }
  }

  /**
   * Undeploys a verticle.
   */
  public static void undeployVerticle(final Env env, StringValue deploymentID, @Optional final Callback handler) {
    if (handler != null && !handler.isDefault()) {
      Container.instance.undeployVerticle(deploymentID.toString(), new Handler<AsyncResult<Void>>(env, handler));
    }
    else {
      Container.instance.undeployVerticle(deploymentID.toString());
    }
  }

  /**
   * Deploys a verticle.
   */
  @SuppressWarnings("unchecked")
  public static void deployWorkerVerticle(final Env env, final StringValue main, @Optional final ArrayValue config, @Optional("1") final NumberValue instances) {
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

package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.ArrayValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
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
  public static void deployModule(final Env env, final StringValue moduleName, @Optional final ArrayValue config, @Optional final Value instances, @Optional final Callback handler) {
    if (instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString());
    }
    else if (!instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), instances.toJavaInteger());
    }
    else if (!instances.isDefault() && config.isDefault() && !handler.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), instances.toJavaInteger(), new Handler<AsyncResult<String>>(env, handler));
    }
    else if (!instances.isDefault() && !config.isDefault() && !handler.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toJavaInteger());
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
  public static void deployVerticle(final Env env, final StringValue moduleName, @Optional final ArrayValue config, @Optional final Value instances, @Optional final Callback handler) {
    if (instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployVerticle(moduleName.toJavaString());
    }
    else if (!instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployVerticle(moduleName.toJavaString(), instances.toJavaInteger());
    }
    else if (!instances.isDefault() && config.isDefault() && !handler.isDefault()) {
      Container.instance.deployVerticle(moduleName.toJavaString(), instances.toJavaInteger(), new Handler<AsyncResult<String>>(env, handler));
    }
    else if (!instances.isDefault() && !config.isDefault() && !handler.isDefault()) {
      Container.instance.deployVerticle(moduleName.toJavaString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toJavaInteger());
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
   * Deploys a worker verticle.
   */
  @SuppressWarnings("unchecked")
  public static void deployWorkerVerticle(final Env env, final StringValue moduleName, @Optional final ArrayValue config, @Optional final Value instances, @Optional final Callback handler) {
    if (instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployWorkerVerticle(moduleName.toJavaString());
    }
    else if (!instances.isDefault() && config.isDefault() && handler.isDefault()) {
      Container.instance.deployWorkerVerticle(moduleName.toJavaString(), instances.toJavaInteger());
    }
    else if (!instances.isDefault() && !config.isDefault() && !handler.isDefault()) {
      Container.instance.deployWorkerVerticle(moduleName.toJavaString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toJavaInteger());
    }
  }

  /**
   * Returns the current Vertx container environment.
   */
  public static Map<String, String> env(Env env) {
    return Container.instance.env();
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
  public static JsonObject config(Env env) {
    return Container.instance.config();
  }

}

package com.blankstyle.vertx.php;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
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

  public static void deployModule(final Env env, final StringValue moduleName, @Optional final ArrayValue config, @Optional final Value instances, @Optional final Callback callback) {
    if (instances.isDefault() && config.isDefault() && callback.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString());
    }
    else if (!instances.isDefault() && config.isDefault() && callback.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), instances.toJavaInteger());
    }
    else if (!instances.isDefault() && config.isDefault() && !callback.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), instances.toJavaInteger(), new AsyncResultHandler<String>() {
        @Override
        public void handle(AsyncResult<String> result) {
          callback.toCallable(env, false).call(env, env.wrapJava(result));
        }
      });
    }
    else if (!instances.isDefault() && !config.isDefault() && !callback.isDefault()) {
      Container.instance.deployModule(moduleName.toJavaString(), new JsonObject(config.toJavaMap(env, new HashMap<String, Object>().getClass())), instances.toJavaInteger());
    }
  }

  public static Map<String, String> env(Env env) {
    return Container.instance.env();
  }

  public static void exit() {
    Container.instance.exit();
  }

  public static Logger logger() {
    return Container.instance.logger();
  }

}

package com.blankstyle.vertx.php.shareddata;

import java.util.Set;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * Wraps Vert.x shared data objects in PHP friendly interfaces.
 *
 * @author Jordan Halterman
 */
public class SharedData {

  private org.vertx.java.core.shareddata.SharedData data;

  public SharedData(org.vertx.java.core.shareddata.SharedData data) {
    this.data = data;
  }

  public SharedMap getMap(Env env, StringValue name) {
    return new SharedMap(data.getMap(name.toString()));
  }

  public boolean removeMap(Env env, Value name) {
    return data.removeMap(name.toJavaObject());
  }

  public Set<?> getSet(Env env, StringValue name) {
    return data.getSet(name.toString());
  }

  public boolean removeSet(Env env, Value name) {
    return data.removeSet(name.toJavaObject());
  }

}

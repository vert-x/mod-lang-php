package com.blankstyle.vertx.php.shareddata;

import org.vertx.java.core.shareddata.ConcurrentSharedMap;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.spl.ArrayAccess;

/**
 * Wraps a Vert.x shared map in a PHP friendly interface.
 *
 * @author Jordan Halterman
 */
public class SharedMap implements ArrayAccess {

  private ConcurrentSharedMap<Object, Object> map;

  SharedMap(ConcurrentSharedMap<Object, Object> map) {
    this.map = map;
  }

  @Override
  public boolean offsetExists(Value key) {
    return map.containsKey(key.toJavaObject());
  }

  @Override
  public Value offsetGet(Value key) {
    return Env.getCurrent().wrapJava(map.get(key.toJavaObject()));
  }

  @Override
  public Value offsetSet(Value key, Value value) {
    map.put(key.toJavaObject(), value.toJavaObject());
    return null;
  }

  @Override
  public Value offsetUnset(Value key) {
    map.remove(key.toJavaObject());
    return null;
  }

}

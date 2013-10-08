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
package io.vertx.lang.php.shareddata;

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

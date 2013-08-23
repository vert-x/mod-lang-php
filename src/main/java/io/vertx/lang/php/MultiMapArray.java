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
package io.vertx.lang.php;

import org.vertx.java.core.MultiMap;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.spl.ArrayAccess;
import com.caucho.quercus.lib.spl.Countable;

/**
 * A PHP associative array implementation of the Vert.x MultiMap.
 *
 * @author Jordan Halterman
 */
public class MultiMapArray implements ArrayAccess, Countable {

  private MultiMap map;

  public MultiMapArray(MultiMap map) {
    this.map = map;
  }

  @Override
  public boolean offsetExists(Value name) {
    return map.contains(name.toString());
  }

  @Override
  public Value offsetGet(Value name) {
    return Env.getCurrent().wrapJava(map.get(name.toString()));
  }

  @Override
  public Value offsetSet(Value name, Value value) {
    map.set(name.toString(), value.toString());
    return null;
  }

  @Override
  public Value offsetUnset(Value name) {
    map.remove(name.toString());
    return null;
  }

  @Override
  public int count() {
    return map.size();
  }

}

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

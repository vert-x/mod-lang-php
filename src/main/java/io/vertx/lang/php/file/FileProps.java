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
package io.vertx.lang.php.file;

import io.vertx.lang.php.Gettable;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x FileProps class.
 *
 * @author Jordan Halterman
 */
public class FileProps implements Gettable {

  private org.vertx.java.core.file.FileProps props;

  public FileProps(org.vertx.java.core.file.FileProps props) {
    this.props = props;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  public long size(Env env) {
    return props.size();
  }

  public BooleanValue isRegularFile(Env env) {
    return BooleanValue.create(props.isRegularFile());
  }

  public BooleanValue isDirectory(Env env) {
    return BooleanValue.create(props.isRegularFile());
  }

  public BooleanValue isSymbolicLink(Env env) {
    return BooleanValue.create(props.isRegularFile());
  }

  public BooleanValue isOther(Env env) {
    return BooleanValue.create(props.isOther());
  }

  public LongValue creationTime(Env env) {
    return LongValue.create(props.creationTime().getTime());
  }

  public LongValue lastModifiedTime(Env env) {
    return LongValue.create(props.lastModifiedTime().getTime());
  }

  public LongValue lastAccessTime(Env env) {
    return LongValue.create(props.lastAccessTime().getTime());
  }

}

package com.blankstyle.vertx.php.file;

import com.blankstyle.vertx.php.Gettable;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x FileSystemProps class.
 *
 * @author Jordan Halterman
 */
public class FileSystemProps implements Gettable {

  private org.vertx.java.core.file.FileSystemProps props;

  public FileSystemProps(org.vertx.java.core.file.FileSystemProps props) {
    this.props = props;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  public long usableSpace(Env env) {
    return props.usableSpace();
  }

  public long unallocatedSpace(Env env) {
    return props.unallocatedSpace();
  }

  public long totalSpace(Env env) {
    return props.totalSpace();
  }

}

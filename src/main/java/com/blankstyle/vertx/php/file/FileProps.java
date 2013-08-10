package com.blankstyle.vertx.php.file;

import com.blankstyle.vertx.php.Gettable;
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

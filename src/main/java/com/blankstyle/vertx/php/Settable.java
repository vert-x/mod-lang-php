package com.blankstyle.vertx.php;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * An interface for PHP classes that implement the magic __set() method.
 *
 * @author Jordan Halterman
 */
public interface Settable {

  /**
   * Sets a field value.
   */
  public Value __setField(Env env, StringValue name, Value value);

}

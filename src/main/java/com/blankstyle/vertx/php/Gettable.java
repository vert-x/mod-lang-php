package com.blankstyle.vertx.php;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

/**
 * An interface for PHP classes that implement the magic __get() method.
 *
 * @author Jordan Halterman
 */
public interface Gettable {

  /**
   * Gets a field value.
   */
  public Value __getField(Env env, StringValue name);

}

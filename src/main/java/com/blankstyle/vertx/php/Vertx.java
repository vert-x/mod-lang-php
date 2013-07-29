package com.blankstyle.vertx.php;

import com.blankstyle.vertx.php.http.HttpClient;
import com.blankstyle.vertx.php.http.HttpServer;
import com.blankstyle.vertx.php.net.NetClient;
import com.blankstyle.vertx.php.net.NetServer;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;

/**
 * A static Vertx instance access class.
 */
public final class Vertx {

  private static org.vertx.java.core.Vertx instance;

  /**
   * Initializes the static internal Vertx instance.
   *
   * @param instance The Vert.x Vertx instance.
   */
  public static void init(org.vertx.java.core.Vertx instance) {
    if (Vertx.instance == null) {
      Vertx.instance = instance;
    }
  }

  /**
   * Creates a TCP/SSL server.
   * @return
   */
  public static NetServer createNetServer() {
    return new NetServer(Vertx.instance.createNetServer());
  }

  /**
   * Creates a TCP/SSL client.
   * @return
   */
  public static NetClient createNetClient() {
    return new NetClient(Vertx.instance.createNetClient());
  }

  /**
   * Creates an HTTP/HTTPS server.
   * @return
   */
  public static HttpServer createHttpServer() {
    return new HttpServer(Vertx.instance.createHttpServer());
  }

  /**
   * Creates an HTTP/HTTPS client.
   * @return
   */
  public static HttpClient createHttpClient() {
    return new HttpClient(Vertx.instance.createHttpClient());
  }

  /**
   * Returns a boolean value indicating whether the current
   * thread is an event loop thread.
   */
  public static BooleanValue isEventLoop() {
    return BooleanValue.create(Vertx.instance.isEventLoop());
  }

  /**
   * Returns a boolean value indicating whether the current
   * thread is a worker thread.
   */
  public static BooleanValue isWorker() {
    return BooleanValue.create(Vertx.instance.isWorker());
  }

  public static void runOnContext() {
    
  }

  /**
   * Returns the current Vertx context.
   */
  // public static Context currentContext(Env env) {
  //  
  // }

  /**
   * Cancels the timer with the specified id.
   *
   * @param id The timer id.
   * @return A value indicating whether the timer was successfully cancelled.
   */
  public static BooleanValue cancelTimer(final long id) {
    boolean result = Vertx.instance.cancelTimer(id);
    return BooleanValue.create(result);
  }

  /**
   * Stops the eventbus and any resources managed by the eventbus.
   */
  public static void stop() {
    Vertx.instance.stop();
  }

}

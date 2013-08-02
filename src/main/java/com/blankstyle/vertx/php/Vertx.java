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
package com.blankstyle.vertx.php;

import org.vertx.java.core.logging.Logger;
import org.vertx.java.core.shareddata.SharedData;

import com.blankstyle.vertx.php.eventbus.EventBus;
import com.blankstyle.vertx.php.file.FileSystem;
import com.blankstyle.vertx.php.http.HttpClient;
import com.blankstyle.vertx.php.http.HttpServer;
import com.blankstyle.vertx.php.net.NetClient;
import com.blankstyle.vertx.php.net.NetServer;
import com.blankstyle.vertx.php.sockjs.SockJSServer;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;

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
   * Allows the Vert.x logger to be accessed via the static Vertx PHP class.
   */
  public static Logger logger(Env env) {
    return Container.logger();
  }

  /**
   * Creates a TCP/SSL server.
   */
  public static NetServer createNetServer(Env env) {
    return new NetServer(Vertx.instance.createNetServer());
  }

  /**
   * Creates a TCP/SSL client.
   */
  public static NetClient createNetClient(Env env) {
    return new NetClient(Vertx.instance.createNetClient());
  }

  /**
   * Creates an HTTP/HTTPS server.
   */
  public static HttpServer createHttpServer(Env env) {
    return new HttpServer(Vertx.instance.createHttpServer());
  }

  /**
   * Creates an HTTP/HTTPS client.
   */
  public static HttpClient createHttpClient(Env env) {
    return new HttpClient(Vertx.instance.createHttpClient());
  }

  /**
   * Creates a SockJS server.
   */
  public static SockJSServer createSockJSServer(Env env, HttpServer server) {
    return new SockJSServer(Vertx.instance.createSockJSServer(server.getVertxServer()));
  }

  /**
   * Returns a boolean value indicating whether the current
   * thread is an event loop thread.
   */
  public static BooleanValue isEventLoop(Env env) {
    return BooleanValue.create(Vertx.instance.isEventLoop());
  }

  /**
   * Returns a boolean value indicating whether the current
   * thread is a worker thread.
   */
  public static BooleanValue isWorker(Env env) {
    return BooleanValue.create(Vertx.instance.isWorker());
  }

  /**
   * Returns the Vert.x event bus.
   */
  public static EventBus eventBus(Env env) {
    return new EventBus(Vertx.instance.eventBus());
  }

  /**
   * Returns the Vertx FileSystem instance.
   */
  public static FileSystem fileSystem(Env env) {
    return new FileSystem(Vertx.instance.fileSystem());
  }

  /**
   * Returns the Vertx SharedData instance.
   */
  public static SharedData sharedData(Env env) {
    return Vertx.instance.sharedData();
  }

  /**
   * Returns the current Vertx context.
   */
  public static Context currentContext(Env env) {
   return new Context(Vertx.instance.currentContext());
  }

  /**
   * Put the handler on the event queue for the current loop
   * (or worker context) so it will be run asynchronously ASAP
   * after this event has been processed
   * @param callback A callable PHP function, method, or closure.
   */
  public static void runOnContext(Env env, Callback callback) {
    currentContext(env).runOnContext(env, callback);
  }

  /**
   * Sets a timed handler.
   */
  public static LongValue setTimer(Env env, LongValue delay, Callback handler) {
    Vertx.instance.setTimer(delay.toLong(), new Handler<Long>(env, handler));
    return delay;
  }

  /**
   * Sets a periodic handler.
   */
  public static LongValue setPeriodic(Env env, LongValue delay, Callback handler) {
    Vertx.instance.setPeriodic(delay.toLong(), new Handler<Long>(env, handler));
    return delay;
  }

  /**
   * Cancels the timer with the specified id.
   *
   * @param id The timer id.
   * @return A value indicating whether the timer was successfully cancelled.
   */
  public static BooleanValue cancelTimer(Env env, LongValue id) {
    boolean result = Vertx.instance.cancelTimer(id.toLong());
    return BooleanValue.create(result);
  }

  /**
   * Stops the eventbus and any resources managed by the eventbus.
   */
  public static void stop(Env env) {
    Vertx.instance.stop();
  }

}

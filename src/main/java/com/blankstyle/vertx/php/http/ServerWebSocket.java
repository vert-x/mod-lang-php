package com.blankstyle.vertx.php.http;

import org.vertx.java.core.http.WebSocketBase;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.StringValue;

public class ServerWebSocket extends WebSocket {

  private org.vertx.java.core.http.ServerWebSocket socket;

  public ServerWebSocket(org.vertx.java.core.http.ServerWebSocket socket) {
    super(socket);
    this.socket = socket;
  }

  public ServerWebSocket(Env env, org.vertx.java.core.http.ServerWebSocket socket) {
    super(env, socket);
    this.socket = socket;
  }

  /**
   * The path the websocket is attempting to connect at
   */
  public StringValue path(Env env) {
    return env.createString(socket.path());
  }

  /**
   * The query string passed on the websocket uri
   */
  public StringValue query(Env env) {
    return env.createString(socket.query());
  }

  /**
   * A map of all headers in the request to upgrade to websocket
   */
  public Value headers(Env env) {
    return env.wrapJava(socket.headers());
  }

  /**
   * Rejects the websocket.
   */
  public Value reject(Env env) {
    socket.reject();
    return env.wrapJava(this);
  }

}

package com.blankstyle.vertx.php.net;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;

import com.blankstyle.vertx.php.TCPClient;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;

public class NetClient extends TCPClient<org.vertx.java.core.net.NetClient> {

  public NetClient(org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  public NetClient(Env env, org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  /**
   * Connects to a server.
   */
  public NetClient connect(final Env env, final NumberValue port, @Optional final StringValue host, final Callback handler) {
    if (host != null && !host.isDefault()) {
      client.connect(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          handler.call(env, env.wrapJava(new NetSocket(socket.result())));
        }
      });
    }
    else if (handler != null && !handler.isDefault()) {
      client.connect(port.toInt(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          handler.call(env, env.wrapJava(new NetSocket(socket.result())));
        }
      });
    }
    return this;
  }

  /**
   * Returns the connection timeout.
   */
  public int getConnectTimeout(Env env) {
    return client.getConnectTimeout();
  }

  /**
   * Sets the connect timeout.
   */
  public NetClient setConnectTimeout(Env env, NumberValue timeout) {
    client.setConnectTimeout(timeout.toInt());
    return this;
  }

  /**
   * Returns the reconnect attempts.
   */
  public int getReconnectAttempts(Env env) {
    return client.getReconnectAttempts();
  }

  /**
   * Sets the reconnect attempts.
   */
  public NetClient setReconnectAttempts(Env env, NumberValue attempts) {
    client.setReconnectAttempts(attempts.toInt());
    return this;
  }

  /**
   * Returns the reconnect interval.
   */
  public long getReconnectInterval(Env env) {
    return client.getReconnectInterval();
  }

  /**
   * Sets the reconnect interval.
   */
  public NetClient setReconnectInterval(Env env, LongValue interval) {
    client.setReconnectInterval(interval.toLong());
    return this;
  }

  /**
   * Closes the client.
   */
  public void close() {
    client.close();
  }

}

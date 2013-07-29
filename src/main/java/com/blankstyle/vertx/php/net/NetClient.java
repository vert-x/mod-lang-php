package com.blankstyle.vertx.php.net;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;

import com.blankstyle.vertx.php.TCPClient;
import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;

public class NetClient extends TCPClient<org.vertx.java.core.net.NetClient> {

  public NetClient(org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  public NetClient(Env env, org.vertx.java.core.net.NetClient client) {
    super(client);
  }

  public NetClient connect(final Env env, final NumberValue port, @Optional final StringValue host, final Callback callback) {
    if (host != null && !host.isDefault()) {
      client.connect(port.toInt(), host.toString(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          callback.call(env, env.wrapJava(new NetSocket(socket.result())));
        }
      });
    }
    else if (callback != null && !callback.isDefault()) {
      client.connect(port.toInt(), new AsyncResultHandler<org.vertx.java.core.net.NetSocket>() {
        public void handle(AsyncResult<org.vertx.java.core.net.NetSocket> socket) {
          host.call(env, env.wrapJava(new NetSocket(socket.result())));
        }
      });
    }
    return this;
  }

}

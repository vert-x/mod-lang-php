package com.blankstyle.vertx.php;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.vertx.java.core.ClientSSLSupport;
import org.vertx.java.core.TCPSupport;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * An abstract TCP server.
 */
public abstract class TCPClient<T extends TCPSupport<T> & ClientSSLSupport<T>> implements Gettable, Settable {

  protected T client;

  public TCPClient(T client) {
    this.client = client;
  }

  /**
   * Gets a field value.
   */
  public Value __getField(Env env, StringValue name) {
    try {
      Method method = this.getClass().getMethod(name.toString());
      return env.wrapJava(method.invoke(this));
    }
    catch (SecurityException e) {
      env.error(e);
    }
    catch (NoSuchMethodException e) {
      env.error(e);
    }
    catch (IllegalArgumentException e) {
      env.error(e);
    }
    catch (IllegalAccessException e) {
      env.error(e);
    }
    catch (InvocationTargetException e) {
      env.error(e);
    }
    return env.wrapJava(null);
  }

  /**
   * Sets a field value.
   */
  public Value __setField(Env env, StringValue name, Value value) {
    try {
      Method method = this.getClass().getMethod(name.toString());
      return env.wrapJava(method.invoke(this, value));
    }
    catch (SecurityException e) {
      env.error(e);
    }
    catch (NoSuchMethodException e) {
      env.error(e);
    }
    catch (IllegalArgumentException e) {
      env.error(e);
    }
    catch (IllegalAccessException e) {
      env.error(e);
    }
    catch (InvocationTargetException e) {
      env.error(e);
    }
    return env.wrapJava(null);
  }

  /**
   * Indicates whether a certificate should trust all connections.
   */
  public BooleanValue trustAll(Env env) {
    return BooleanValue.create(client.isTrustAll());
  }

  /**
   * Sets the certificate trust all setting.
   */
  public TCPClient<T> trustAll(Env env, BooleanValue trustAll) {
    client.setTrustAll(trustAll.toBoolean());
    return this;
  }

  /**
   * Indicates whether this is an SSL connection.
   */
  public BooleanValue ssl(Env env) {
    return BooleanValue.create(client.isSSL());
  }

  /**
   * Sets the SSL status of the connection.
   */
  public TCPClient<T> ssl(Env env, BooleanValue ssl) {
    client.setSSL(ssl.toBoolean());
    return this;
  }

  /**
   * Returns the key store password.
   */
  public StringValue keyStorePassword(Env env) {
    return env.createString(client.getKeyStorePassword());
  }

  /**
   * Sets the key store password.
   */
  public TCPClient<T> keyStorePassword(Env env, StringValue pwd) {
    client.setKeyStorePassword(pwd.toString());
    return this;
  }

  /**
   * Returns the key store path.
   */
  public StringValue keyStorePath(Env env) {
    return env.createString(client.getKeyStorePath());
  }

  /**
   * Sets the key store path.
   */
  public TCPClient<T> keyStorePath(Env env, StringValue path) {
    client.setKeyStorePath(path.toString());
    return this;
  }

  /**
   * Returns the trust store password.
   */
  public StringValue trustStorePassword(Env env) {
    return env.createString(client.getTrustStorePassword());
  }

  /**
   * Sets the trust store password.
   */
  public TCPClient<T> trustStorePassword(Env env, StringValue pwd) {
    client.setTrustStorePassword(pwd.toString());
    return this;
  }

  /**
   * Returns the send buffer size.
   */
  public Value sendBufferSize(Env env) {
    return env.wrapJava(client.getSendBufferSize());
  }

  /**
   * Set the TCP send buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> sendBufferSize(Env env, NumberValue size) {
    client.setSendBufferSize(size.toInt());
    return this;
  }

  /**
   * Returns the receive buffer size.
   */
  public Value receiveBufferSize(Env env) {
    return env.wrapJava(client.getReceiveBufferSize());
  }

  /**
   * Set the TCP receive buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> receiveBufferSize(Env env, NumberValue size) {
    client.setReceiveBufferSize(size.toInt());
    return this;
  }

  /**
   * The value of TCP reuse address.
   */
  public BooleanValue reuseAddress(Env env) {
    return BooleanValue.create(client.isReuseAddress());
  }

  /**
   * Set the TCP reuseAddress setting for connections created by this instance to reuse.
   */
  public TCPClient<T> reuseAddress(Env env, BooleanValue reuse) {
    client.setReuseAddress(reuse.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP so linger.
   */
  public Value soLinger(Env env) {
    return env.wrapJava(client.getSoLinger());
  }

  /**
   * Set the TCP soLinger setting for connections created by this instance to linger.
   */
  public TCPClient<T> soLinger(Env env, NumberValue linger) {
    client.setReceiveBufferSize(linger.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether TCP keep alive is enabled.
   */
  public BooleanValue keepAlive(Env env) {
    return BooleanValue.create(client.isTCPKeepAlive());
  }

  /**
   * Set the TCP keepAlive setting for connections created by this instance to keepAlive.
   */
  public TCPClient<T> keepAlive(Env env, BooleanValue keepAlive) {
    client.setTCPKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Returns a boolean indicating whether Nagle's algorithm is enabled.
   */
  public BooleanValue noDelay(Env env) {
    return BooleanValue.create(client.isTCPNoDelay());
  }

  /**
   * If tcpNoDelay is set to true then Nagle's algorithm will turned off for the TCP connections created by this instance.
   */
  public TCPClient<T> noDelay(Env env, BooleanValue tcpNoDelay) {
    client.setTCPNoDelay(tcpNoDelay.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP traffic class.
   */
  public Value trafficClass(Env env) {
    return env.wrapJava(client.getTrafficClass());
  }

  /**
   * Set the TCP trafficClass setting for connections created by this instance to trafficClass.
   */
  public TCPClient<T> trafficClass(Env env, NumberValue trafficClass) {
    client.setReceiveBufferSize(trafficClass.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether pooled buffers are used.
   */
  public BooleanValue usePooledBuffers(Env env) {
    return BooleanValue.create(client.isUsePooledBuffers());
  }

  /**
   * Set if vertx should use pooled buffers for performance reasons.
   */
  public TCPClient<T> usePooledBuffers(Env env, BooleanValue pooledBuffers) {
    client.setUsePooledBuffers(pooledBuffers.toBoolean());
    return this;
  }

}

